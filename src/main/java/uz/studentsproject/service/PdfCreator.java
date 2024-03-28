package uz.studentsproject.service;

import uz.studentsproject.aggregation.dto.response.StudentResponseDto;

public interface PdfCreator {

    byte[] createPdf(Long id);

    StudentResponseDto getStudentInfo(Long id);
}
