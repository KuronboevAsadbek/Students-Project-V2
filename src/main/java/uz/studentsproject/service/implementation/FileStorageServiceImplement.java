package uz.studentsproject.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.model.FileStorage;
import uz.studentsproject.aggregation.model.Student;
import uz.studentsproject.exception.FileStorageException;
import uz.studentsproject.exception.StudentException;
import uz.studentsproject.exception.UniversityException;
import uz.studentsproject.repository.FileStorageRepository;
import uz.studentsproject.repository.StudentRepository;
import uz.studentsproject.service.FileStorageService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImplement implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final Hashids hashids;
    private final StudentRepository studentRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    public static ResponseEntity<FileUrlResource> getFileUrlResourceResponseEntity(@RequestParam(
            value = "hashId", required = false) String hashId, @RequestParam(value = "fileId", required = false)
    Long fileId, FileStorageService fileStorageService, String uploadFolder) throws MalformedURLException {
        if (hashId == null && fileId == null) {
            return ResponseEntity.badRequest().build();
        }
        FileStorage fileStorage = null;
        if (hashId == null) {
            fileStorage = fileStorageService.findById(fileId).getData();
        } else if (fileId == null) {
            fileStorage = fileStorageService.findByHashId(hashId).getData();
        }
        assert fileStorage != null;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(fileStorage.getName(), StandardCharsets.UTF_8) + "\"")
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }

    @Override
    public ResponseDTO<FileStorage> save(MultipartFile multipartFile, Long studentId) {

        if (multipartFile.isEmpty() || studentId == null) {
            throw new UniversityException("Fayl yoki foydalanuvchi yoki fan topilmadi");
        }

        ResponseDTO<FileStorage> responseDTO = new ResponseDTO<>();
        FileStorage fileStorage = new FileStorage();

        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setOriginalName(cutFileOriginalName(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorageRepository.save(fileStorage);

        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DATE)));
        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            log.info("Upload folder created");
        }
        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DATE),
                fileStorage.getHashId(),
                fileStorage.getExtension()));
        fileStorage = fileStorageRepository.save(fileStorage);
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new StudentException("Student is not found"));
        student.setFileStorageId(fileStorage.getId());
        studentRepository.save(student);

        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s", fileStorage.getHashId(),
                fileStorage.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException ignored) {
            log.error("File upload error");
        }
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Fayl mufoqiyatli yuklandi");
        responseDTO.setData(fileStorage);
        return responseDTO;
    }

    @Override
    public ResponseDTO<FileStorage> findByHashId(String hashId) {
        ResponseDTO<FileStorage> responseDTO = new ResponseDTO<>();
        responseDTO.setData(fileStorageRepository.findByHashId(hashId));
        responseDTO.setMessage("File found by hashId");
        responseDTO.setSuccess(true);
        log.info("File found by hashId");
        return responseDTO;
    }

    @Override
    public ResponseDTO<FileStorage> findById(Long fileId) {
        ResponseDTO<FileStorage> responseDTO = new ResponseDTO<>();
        responseDTO.setData(fileStorageRepository.findById(fileId).orElseThrow(() -> new StudentException("File not found")));
        responseDTO.setMessage("File found by id");
        responseDTO.setSuccess(true);
        log.info("File found by id");
        return responseDTO;
    }

    @Override
    public ResponseDTO<String> delete(String hashId) {
        FileStorage fileStorage = fileStorageRepository.findByHashId(hashId);
        try {
            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            studentRepository.updateFileStorageId(fileStorage.getId());
            fileStorageRepository.delete(fileStorage);
            responseDTO.setSuccess(true);
            responseDTO.setMessage("File deleted");
            log.info("File deleted");
            return responseDTO;
        } catch (Exception e) {
            log.error("Error in deleting file", e);
            throw new FileStorageException("Error in deleting file");
        }
    }

    @Override
    public String cutFileOriginalName(String name) {
        int dot = name.lastIndexOf('.');
        return name.substring(0, dot);
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }
}

