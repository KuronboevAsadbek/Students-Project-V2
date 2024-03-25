package uz.studentsproject.aggregation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Validated
public class StudentRequestDto {


    @JsonProperty("first_name")
    @NotEmpty(message = "First Name Must Be Filled")
    private final String firstName;

    @JsonProperty("last_name")
    @NotEmpty(message = "Last Name Must Be Filled")
    private final String lastName;

    @JsonProperty("middle_name")
    @NotEmpty(message = "Middle Name Must Be Filled")
    private final String middleName;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("study_state_date")
    private final Timestamp studyStateDate;

    @JsonProperty("study_end_date")
    private final Timestamp studyEndDate;

    @JsonProperty("gender")
    @NotEmpty(message = "Gender Must Be Filled")
    private final String gender;

    @JsonProperty("field_of_study_id")
    @Min(value = 1, message = "Field Of Study Id Must Be Greater Than 0 ")
    @NotNull(message = "Field Id Must Be Filled")
    private final Long fieldOfStudyId;

    @JsonProperty("file_storage_id")
    private final Long fileStorageId;
}