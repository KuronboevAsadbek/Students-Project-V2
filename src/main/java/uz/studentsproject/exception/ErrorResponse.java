package uz.studentsproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.studentsproject.utils.ResponseCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private  int code;
    private  String message;

    public static ErrorResponse of(ResponseCode responseCode, String message) {
        return new ErrorResponse(responseCode.getCode(), message);
    }

}
