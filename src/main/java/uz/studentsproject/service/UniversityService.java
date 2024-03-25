package uz.studentsproject.service;

import org.springframework.data.domain.Pageable;
import uz.studentsproject.aggregation.dto.request.UniversityRequestDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.dto.response.UniversityResponseDto;

import java.util.List;

public interface UniversityService {

    ResponseDTO<UniversityResponseDto> createOrUpdate(UniversityRequestDto universityResponseDto, Long id);

    List<UniversityResponseDto> getAll(Pageable pageable);

    UniversityResponseDto getById(Long id);

ResponseDTO<UniversityResponseDto> delete(Long id);


}
