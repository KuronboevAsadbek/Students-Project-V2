package uz.studentsproject.aggregation.dto.response;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudyResponseDto {

    @Id
    private  Long id;
    private  String name;
    private  String universityName;
}