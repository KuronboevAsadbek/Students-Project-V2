package uz.studentsproject.controller;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.service.ExportService;
import uz.studentsproject.service.StudentService;

import java.io.IOException;

import static uz.studentsproject.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EXPORT)
public class ExportController {
    private final ExportService exportService;
    private final StudentService studentService;

    @GetMapping(EXCEL)
    public ResponseEntity<byte[]> exportToExcel(Pageable pageable) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition",
                "attachment; filename=students.xlsx"); //inline only for preview, attachment for download file
        return new ResponseEntity<>(exportService.exportToExcel(pageable), httpHeaders, HttpStatus.OK);

    }

    @GetMapping(PDF)
    public ResponseEntity<byte[]> exportToPdf(Pageable pageable) throws DocumentException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition",
                "attachment; filename=students.pdf"); //inline only for preview, attachment for download file
        return new ResponseEntity<>(exportService.exportToPdf(pageable), httpHeaders, HttpStatus.OK);

    }

//    @GetMapping(PDF_RESUME)
//    public ResponseEntity<byte[]> pdfResume(@PathVariable Long id) throws DocumentException {
//        StudentResponseDto studentResponseDto = studentService.getOneStudent(id).getData();
//        String studentName = studentResponseDto.getFirstName() + "_" + studentResponseDto.getLastName();
//
//        // Generate the PDF content
//        byte[] pdfContent = exportService.pdfResume(id);
//
//        // Set the filename based on the student's name
//        String filename = studentName + "_Resume.pdf";
//
//        // Set response headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", filename);
//
//        // Return the PDF content with appropriate headers
//        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

    }

//    @GetMapping(PDF_RESUME2)
//    public ResponseEntity<byte[]> pdfResume2(@PathVariable Long id, HttpServletResponse httpServletResponse) throws
//            DocumentException, IOException {
//        StudentResponseDto studentResponseDto = studentService.getOneStudent(id).getData();
//        String studentName = studentResponseDto.getFirstName() + "_" + studentResponseDto.getLastName();
//
//        // Generate the PDF content
//        byte[] pdfContent = exportService.pdfResume2(id, httpServletResponse);
//
//        // Set the filename based on the student's name
//        String filename = studentName + "_Resume.pdf";
//
//        // Set response headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", filename);
//
//        // Return the PDF content with appropriate headers
//        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
//
//    }
