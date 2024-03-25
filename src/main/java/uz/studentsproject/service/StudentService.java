package uz.studentsproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.studentsproject.aggregation.dto.request.StudentRequestDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;

import java.util.List;

public interface StudentService {

    ResponseDTO<StudentResponseDto> createOrUpdate(StudentRequestDto studentRequestDto, Long id);

    ResponseDTO<Page<StudentResponseDto>> getAllStudents(Pageable pageable);

    ResponseDTO<StudentResponseDto> getOneStudent(Long id);

    ResponseDTO<StudentResponseDto> delete(Long id);


}
