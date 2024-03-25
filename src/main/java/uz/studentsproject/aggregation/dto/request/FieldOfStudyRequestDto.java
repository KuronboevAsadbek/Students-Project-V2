package uz.studentsproject.aggregation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class FieldOfStudyRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4347920093597002867L;
    @JsonProperty("name")
    @NotEmpty(message = "Field Of Study Name Must Be Filled")
    private String name;


    @JsonProperty("university_id")
    @Min(value = 1, message = "University Id Must Be Greater Than 0 ")
    @NotNull(message = "University Id Must Be Filled")
    private Long universityId;
}