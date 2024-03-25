package uz.studentsproject.service;

import org.springframework.web.multipart.MultipartFile;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.model.FileStorage;

public interface FileStorageService {
    ResponseDTO<FileStorage> save(MultipartFile multipartFile, Long userId);

    ResponseDTO<FileStorage> findByHashId(String hashId);

    ResponseDTO<FileStorage> findById(Long fileId);

    ResponseDTO<String> delete(String hashId);

    String cutFileOriginalName(String name);
}
