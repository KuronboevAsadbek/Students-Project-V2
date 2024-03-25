package uz.studentsproject.service;

import org.springframework.data.domain.Pageable;
import uz.studentsproject.aggregation.dto.request.FieldOfStudyRequestDto;
import uz.studentsproject.aggregation.dto.response.FieldOfStudyResponseDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;

import java.util.List;

public interface FieldOfStudyService {

    ResponseDTO<FieldOfStudyResponseDto> createOrUpdate(FieldOfStudyRequestDto fieldOfStudyRequestDto, Long id);

    ResponseDTO<List<FieldOfStudyResponseDto>> getAll(Pageable pageable);

    ResponseDTO<FieldOfStudyResponseDto> delete(Long id);


}
