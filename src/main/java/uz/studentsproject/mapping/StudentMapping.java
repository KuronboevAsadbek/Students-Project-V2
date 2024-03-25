package uz.studentsproject.mapping;

import org.mapstruct.Mapper;
import uz.studentsproject.aggregation.dto.request.StudentRequestDto;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.aggregation.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapping extends EntityMapping<Student, StudentRequestDto, StudentResponseDto> {
}
