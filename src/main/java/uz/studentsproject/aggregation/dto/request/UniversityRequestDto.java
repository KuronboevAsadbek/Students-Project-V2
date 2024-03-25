package uz.studentsproject.aggregation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
public class UniversityRequestDto {


    @JsonProperty("name")
    @NotEmpty(message = "University Name Must Be Filled")
    private String name;

}