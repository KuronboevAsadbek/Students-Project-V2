package uz.studentsproject.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.studentsproject.aggregation.dto.request.FieldOfStudyRequestDto;
import uz.studentsproject.aggregation.dto.response.FieldOfStudyResponseDto;
import uz.studentsproject.aggregation.model.FieldOfStudy;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-25T11:24:38+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class FieldOfStudyMapperImpl implements FieldOfStudyMapper {

    @Override
    public FieldOfStudy toEntity(FieldOfStudyRequestDto req) {
        if ( req == null ) {
            return null;
        }

        FieldOfStudy fieldOfStudy = new FieldOfStudy();

        fieldOfStudy.setName( req.getName() );
        fieldOfStudy.setUniversityId( req.getUniversityId() );

        return fieldOfStudy;
    }

    @Override
    public FieldOfStudyResponseDto toDto(FieldOfStudy entity) {
        if ( entity == null ) {
            return null;
        }

        FieldOfStudyResponseDto fieldOfStudyResponseDto = new FieldOfStudyResponseDto();

        fieldOfStudyResponseDto.setId( entity.getId() );
        fieldOfStudyResponseDto.setName( entity.getName() );

        return fieldOfStudyResponseDto;
    }

    @Override
    public void updateFromDto(FieldOfStudyRequestDto req, FieldOfStudy entity) {
        if ( req == null ) {
            return;
        }

        if ( req.getName() != null ) {
            entity.setName( req.getName() );
        }
        if ( req.getUniversityId() != null ) {
            entity.setUniversityId( req.getUniversityId() );
        }
    }

    @Override
    public List<FieldOfStudy> toEntity(List<FieldOfStudyRequestDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FieldOfStudy> list = new ArrayList<FieldOfStudy>( dtoList.size() );
        for ( FieldOfStudyRequestDto fieldOfStudyRequestDto : dtoList ) {
            list.add( toEntity( fieldOfStudyRequestDto ) );
        }

        return list;
    }

    @Override
    public List<FieldOfStudyResponseDto> toDto(List<FieldOfStudy> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FieldOfStudyResponseDto> list = new ArrayList<FieldOfStudyResponseDto>( entityList.size() );
        for ( FieldOfStudy fieldOfStudy : entityList ) {
            list.add( toDto( fieldOfStudy ) );
        }

        return list;
    }
}
