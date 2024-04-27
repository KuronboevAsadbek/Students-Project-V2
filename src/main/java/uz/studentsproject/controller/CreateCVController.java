package uz.studentsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.service.PdfCreator;
import uz.studentsproject.service.StudentService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/pdf/create")
public class CreateCVController {

    private final StudentService studentService;
    private final PdfCreator pdfCreator;


    @GetMapping("/cv/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        StudentResponseDto studentResponseDto = studentService.getOneStudent(id).getData();
        String studentName = studentResponseDto.getFirstName() + "_" + studentResponseDto.getLastName();

        // Generate the PDF content
        byte[] pdfContent = pdfCreator.createPdf(id);

        // Set the filename based on the student's name
        String filename = studentName + "_Resume.pdf";

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", filename);

        // Return the PDF content with appropriate headers
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}

