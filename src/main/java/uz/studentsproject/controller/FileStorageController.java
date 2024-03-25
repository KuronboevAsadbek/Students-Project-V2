package uz.studentsproject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.model.FileStorage;
import uz.studentsproject.service.FileStorageService;

import static uz.studentsproject.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(FILE_STORAGE)
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @PostMapping(UPLOAD)
    public ResponseEntity<ResponseDTO<FileStorage>> upload(@RequestParam("file") MultipartFile multipartFile,
                                                           @RequestParam Long userId) {
        return new ResponseEntity<>(fileStorageService.save(multipartFile, userId), HttpStatus.CREATED);
    }

    @DeleteMapping(DELETE_BY_HASH_ID)
    public ResponseEntity<ResponseDTO<String>> delete(@RequestParam String hashId) {
        return new ResponseEntity<>(fileStorageService.delete(hashId), HttpStatus.OK);
    }

    @GetMapping(GET_BY_HASH_ID)
    public ResponseEntity<ResponseDTO<FileStorage>> getByHashId(@RequestParam String hashId) {
        return new ResponseEntity<>(fileStorageService.findByHashId(hashId), HttpStatus.OK);
    }

    @GetMapping(GET_BY_FILE_ID)
    public ResponseEntity<ResponseDTO<FileStorage>> getById(@RequestParam Long fileId) {
        return new ResponseEntity<>(fileStorageService.findById(fileId), HttpStatus.OK);
    }

}
