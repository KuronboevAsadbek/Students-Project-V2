package uz.studentsproject.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.studentsproject.aggregation.dto.request.StudentRequestDto;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.aggregation.model.Student;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-25T11:24:38+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class StudentMappingImpl implements StudentMapping {

    @Override
    public Student toEntity(StudentRequestDto req) {
        if ( req == null ) {
            return null;
        }

        Student student = new Student();

        student.setFirstName( req.getFirstName() );
        student.setLastName( req.getLastName() );
        student.setMiddleName( req.getMiddleName() );
        student.setDescription( req.getDescription() );
        student.setStudyStateDate( req.getStudyStateDate() );
        student.setStudyEndDate( req.getStudyEndDate() );
        student.setGender( req.getGender() );
        student.setFieldOfStudyId( req.getFieldOfStudyId() );
        student.setFileStorageId( req.getFileStorageId() );

        return student;
    }

    @Override
    public StudentResponseDto toDto(Student entity) {
        if ( entity == null ) {
            return null;
        }

        StudentResponseDto studentResponseDto = new StudentResponseDto();

        studentResponseDto.setId( entity.getId() );
        studentResponseDto.setFirstName( entity.getFirstName() );
        studentResponseDto.setLastName( entity.getLastName() );
        studentResponseDto.setMiddleName( entity.getMiddleName() );
        studentResponseDto.setDescription( entity.getDescription() );
        studentResponseDto.setStudyStateDate( entity.getStudyStateDate() );
        studentResponseDto.setStudyEndDate( entity.getStudyEndDate() );
        studentResponseDto.setGender( entity.getGender() );
        studentResponseDto.setFileStorageId( entity.getFileStorageId() );

        return studentResponseDto;
    }

    @Override
    public void updateFromDto(StudentRequestDto req, Student entity) {
        if ( req == null ) {
            return;
        }

        if ( req.getFirstName() != null ) {
            entity.setFirstName( req.getFirstName() );
        }
        if ( req.getLastName() != null ) {
            entity.setLastName( req.getLastName() );
        }
        if ( req.getMiddleName() != null ) {
            entity.setMiddleName( req.getMiddleName() );
        }
        if ( req.getDescription() != null ) {
            entity.setDescription( req.getDescription() );
        }
        if ( req.getStudyStateDate() != null ) {
            entity.setStudyStateDate( req.getStudyStateDate() );
        }
        if ( req.getStudyEndDate() != null ) {
            entity.setStudyEndDate( req.getStudyEndDate() );
        }
        if ( req.getGender() != null ) {
            entity.setGender( req.getGender() );
        }
        if ( req.getFieldOfStudyId() != null ) {
            entity.setFieldOfStudyId( req.getFieldOfStudyId() );
        }
        if ( req.getFileStorageId() != null ) {
            entity.setFileStorageId( req.getFileStorageId() );
        }
    }

    @Override
    public List<Student> toEntity(List<StudentRequestDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( dtoList.size() );
        for ( StudentRequestDto studentRequestDto : dtoList ) {
            list.add( toEntity( studentRequestDto ) );
        }

        return list;
    }

    @Override
    public List<StudentResponseDto> toDto(List<Student> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StudentResponseDto> list = new ArrayList<StudentResponseDto>( entityList.size() );
        for ( Student student : entityList ) {
            list.add( toDto( student ) );
        }

        return list;
    }
}
