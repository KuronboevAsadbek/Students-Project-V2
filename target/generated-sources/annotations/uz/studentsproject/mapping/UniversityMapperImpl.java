package uz.studentsproject.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.studentsproject.aggregation.dto.request.UniversityRequestDto;
import uz.studentsproject.aggregation.dto.response.UniversityResponseDto;
import uz.studentsproject.aggregation.model.University;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-25T11:24:38+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class UniversityMapperImpl implements UniversityMapper {

    @Override
    public University toEntity(UniversityRequestDto req) {
        if ( req == null ) {
            return null;
        }

        University university = new University();

        university.setName( req.getName() );

        return university;
    }

    @Override
    public UniversityResponseDto toDto(University entity) {
        if ( entity == null ) {
            return null;
        }

        UniversityResponseDto universityResponseDto = new UniversityResponseDto();

        universityResponseDto.setId( entity.getId() );
        universityResponseDto.setName( entity.getName() );

        return universityResponseDto;
    }

    @Override
    public void updateFromDto(UniversityRequestDto req, University entity) {
        if ( req == null ) {
            return;
        }

        if ( req.getName() != null ) {
            entity.setName( req.getName() );
        }
    }

    @Override
    public List<University> toEntity(List<UniversityRequestDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<University> list = new ArrayList<University>( dtoList.size() );
        for ( UniversityRequestDto universityRequestDto : dtoList ) {
            list.add( toEntity( universityRequestDto ) );
        }

        return list;
    }

    @Override
    public List<UniversityResponseDto> toDto(List<University> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UniversityResponseDto> list = new ArrayList<UniversityResponseDto>( entityList.size() );
        for ( University university : entityList ) {
            list.add( toDto( university ) );
        }

        return list;
    }
}
