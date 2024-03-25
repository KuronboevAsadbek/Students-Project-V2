package uz.studentsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.studentsproject.aggregation.dto.request.StudentRequestDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.service.FileStorageService;
import uz.studentsproject.service.StudentService;

import static uz.studentsproject.service.implementation.FileStorageServiceImplement.getFileUrlResourceResponseEntity;

import java.net.MalformedURLException;
import java.util.List;

import static uz.studentsproject.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(STUDENT)
public class StudentController {

    private final StudentService studentService;
    private final FileStorageService fileStorageService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @PostMapping(CREATE_OR_UPDATE)
    public ResponseEntity<ResponseDTO<StudentResponseDto>> create(@Valid @RequestBody StudentRequestDto
                                                                          studentRequestDto,
                                                                  @RequestParam(required = false) Long studentId) {
        return new ResponseEntity<>(studentService.createOrUpdate(studentRequestDto,studentId), HttpStatus.OK);
    }


    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseDTO<StudentResponseDto>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.delete(id), HttpStatus.OK);
    }

    @GetMapping(GETALL)
    public ResponseEntity<ResponseDTO<Page<StudentResponseDto>>> getAll(Pageable pageable){
        return new ResponseEntity<>(studentService.getAllStudents(pageable), HttpStatus.OK);
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<ResponseDTO<StudentResponseDto>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getOneStudent(id), HttpStatus.OK);
    }

    @GetMapping(value = PREVIEW, consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public ResponseEntity<FileUrlResource> previewFile(
            @RequestParam(value = "hashId", required = false) String hashId,
            @RequestParam(value = "fileId", required = false) Long fileId
    ) throws MalformedURLException {
        return getFileUrlResourceResponseEntity(hashId, fileId, fileStorageService, uploadFolder);
    }




}
