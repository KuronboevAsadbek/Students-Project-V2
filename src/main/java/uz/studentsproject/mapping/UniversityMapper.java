package uz.studentsproject.mapping;

import org.mapstruct.Mapper;
import uz.studentsproject.aggregation.dto.request.UniversityRequestDto;
import uz.studentsproject.aggregation.dto.response.UniversityResponseDto;
import uz.studentsproject.aggregation.model.University;

@Mapper(componentModel = "spring")
public interface UniversityMapper extends EntityMapping<University, UniversityRequestDto, UniversityResponseDto> {

}
