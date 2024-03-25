package uz.studentsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.studentsproject.aggregation.dto.request.FieldOfStudyRequestDto;
import uz.studentsproject.aggregation.dto.response.FieldOfStudyResponseDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.service.FieldOfStudyService;

import java.util.List;

import static uz.studentsproject.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(FIELD_OF_STUDY)
public class FieldOfStudyController {

    private final FieldOfStudyService fieldOfStudyService;

    @PostMapping(CREATE_OR_UPDATE)
    public ResponseEntity<ResponseDTO<FieldOfStudyResponseDto>> createField(@Valid @RequestBody FieldOfStudyRequestDto
                                                                                    fieldOfStudyRequestDto,
                                                                            @RequestParam(required = false) Long fieldId) {
        return ResponseEntity.ok(fieldOfStudyService.createOrUpdate(fieldOfStudyRequestDto, fieldId));
    }

    @GetMapping(GETALL)
    public ResponseEntity<ResponseDTO<List<FieldOfStudyResponseDto>>> getAllField(Pageable pageable) {
        return ResponseEntity.ok(fieldOfStudyService.getAll(pageable));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseDTO<FieldOfStudyResponseDto>> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(fieldOfStudyService.delete(id), HttpStatus.OK);
    }


}
