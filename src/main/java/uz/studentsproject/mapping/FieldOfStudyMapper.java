package uz.studentsproject.mapping;

import org.mapstruct.Mapper;
import uz.studentsproject.aggregation.dto.request.FieldOfStudyRequestDto;
import uz.studentsproject.aggregation.dto.response.FieldOfStudyResponseDto;
import uz.studentsproject.aggregation.model.FieldOfStudy;

@Mapper(componentModel = "spring")
public interface FieldOfStudyMapper extends EntityMapping<FieldOfStudy, FieldOfStudyRequestDto, FieldOfStudyResponseDto> {

}
