package uz.studentsproject.aggregation.dto.response;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto{

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String description;
    private Timestamp studyStateDate;
    private Timestamp studyEndDate;
    private String gender;
    private String fieldOfStudyName;
    private String universityName;
    private Long fileStorageId;

}