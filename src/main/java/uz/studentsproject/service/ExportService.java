package uz.studentsproject.service;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExportService {

    byte[] exportToExcel(Pageable pageable);

    byte[] exportToPdf(Pageable pageable) throws DocumentException;

    List<StudentResponseDto> getAllStudents(Pageable pageable);

    byte[] pdfResume(Long id) throws DocumentException;

    byte[] pdfResume2(Long id, HttpServletResponse httpServletResponse) throws IOException, DocumentException;


}
