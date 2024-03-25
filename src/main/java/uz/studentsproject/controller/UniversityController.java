package uz.studentsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.studentsproject.aggregation.dto.request.UniversityRequestDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.dto.response.UniversityResponseDto;
import uz.studentsproject.service.UniversityService;

import java.util.List;

import static uz.studentsproject.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(UNIVERSITY)
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping(CREATE_OR_UPDATE)
    public ResponseEntity<ResponseDTO<UniversityResponseDto>> createOrUpdateUniversity(
            @Valid @RequestBody UniversityRequestDto universityRequestDto,
            @RequestParam(required = false) Long universityId) {
        return new ResponseEntity<>(universityService.createOrUpdate(universityRequestDto, universityId), HttpStatus.CREATED);
    }

    @GetMapping(GETALL)
    public ResponseEntity<List<UniversityResponseDto>> getAllUniversity(Pageable pageable) {
        return new ResponseEntity<>(universityService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<UniversityResponseDto> getByIdUniversity(@PathVariable Long id) {
        return new ResponseEntity<>(universityService.getById(id), HttpStatus.OK);
    }
}
