package uz.studentsproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.utils.ResponseCode;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UniversityException.class)
    public ResponseEntity<Object> handleUniversityException(UniversityException universityException) {
        String errorMessage = universityException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleFiledOfStudyException(Exception exception) {
//        String errorMessage = exception.getMessage();
//        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
//        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> on(MethodArgumentNotValidException ex) {
        log.error("Client sent the wrong request: {}", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ResponseCode.REQUIRED_DATA_MISSING, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<Object> handleUniversityException(StudentException studentException) {
        String errorMessage = studentException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FieldOfStudyException.class)
    public ResponseEntity<Object> handleUniversityException(FieldOfStudyException fieldOfStudyException) {
        String errorMessage = fieldOfStudyException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<Object> handleFileStorageException(FileStorageException fileStorageException) {
        String errorMessage = fileStorageException.getMessage();
        ResponseDTO<Object> responseDTO = ResponseDTO.error(errorMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

}
