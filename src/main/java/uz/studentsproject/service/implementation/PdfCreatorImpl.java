package uz.studentsproject.service.implementation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.aggregation.model.FileStorage;
import uz.studentsproject.repository.FileStorageRepository;
import uz.studentsproject.service.PdfCreator;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PdfCreatorImpl implements PdfCreator {

    private final EntityManager entityManager;
    private final FileStorageRepository fileStorageRepository;
    @Value("${upload.folder}")
    private String uploadFolder;

    @Override
    public byte[] createPdf(Long id) {
        StudentResponseDto studentInfo;
        studentInfo = getStudentInfo(id);
        assert studentInfo != null;
        FileStorage fileStorage = fileStorageRepository.findById(studentInfo.getFileStorageId()).orElseThrow();
        String path = "C:\\Users\\asadb\\Desktop\\Projects\\Projects\\StudentsProject\\" + this.uploadFolder + "\\"
                + fileStorage.getUploadPath();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document myDocument = new Document();
            PdfWriter.getInstance(myDocument, outputStream);
            myDocument.open();

            PdfPTable dateTimeTable = new PdfPTable(1);
            PdfPCell dateTimeCell = new PdfPCell();
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define your date-time format here
            String currentDateTime = dateTimeFormat.format(new Date());
            dateTimeCell.addElement(new Paragraph(currentDateTime, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.DARK_GRAY)));
            dateTimeCell.setBorder(Rectangle.NO_BORDER);
            dateTimeTable.addCell(dateTimeCell);
            dateTimeTable.setWidthPercentage(100);
            dateTimeTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            myDocument.add(dateTimeTable);


            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(path);
            img.setAbsolutePosition(473f, 750f);
            img.scaleAbsolute(80f, 85);
            PdfPTable table = new PdfPTable(2);
            myDocument.add(img);
            myDocument.add(new Paragraph(" ",
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph(" ",
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Name: " + studentInfo.getFirstName() + " " +
                    studentInfo.getLastName() + " " + studentInfo.getMiddleName(),
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("",
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("",
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            myDocument.add(new Paragraph("CONTACT DETAILS", FontFactory.getFont(FontFactory.TIMES_BOLD, 9, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Email: " + " " + " ", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Contact", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Address", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            myDocument.add(new Paragraph("SKILLS", FontFactory.getFont(FontFactory.TIMES_BOLD, 9, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY)));
            //Skills
            myDocument.add(new Paragraph("Skill 1", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Skill 2", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Skill 3", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Skill 4", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(table);
            myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            myDocument.add(new Paragraph("QUALIFICATIONS", FontFactory.getFont(FontFactory.TIMES_BOLD, 9, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Collage: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("University: " + " " + studentInfo.getUniversityName(), FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("e.t.c", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            myDocument.add(new Paragraph("WORK EXPERIENCE", FontFactory.getFont(FontFactory.TIMES_BOLD, 10, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("DAVR BANK", FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            myDocument.add(new Paragraph("REFERENCES", FontFactory.getFont(FontFactory.TIMES_BOLD, 9, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY)));
            myDocument.add(new Paragraph("Available upon request", FontFactory.getFont(FontFactory.TIMES_BOLD, 6, BaseColor.DARK_GRAY)));
            myDocument.close();
            return outputStream.toByteArray();
        } catch (DocumentException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public StudentResponseDto getStudentInfo(Long studentId) {
        String sql = """
                SELECT s.id AS id,
                       s.first_name AS first_name,
                       s.last_name AS last_name,
                       s.middle_name AS middle_name,
                       s.description AS description,
                       s.study_state_date AS study_state_date,
                       s.study_end_date AS study_end_date,
                       s.gender AS gender,
                       fos.name AS field_of_study_name,
                       u.name AS university_name,
                       fs.id AS file_storage_id
                FROM student s
                    JOIN field_of_study fos ON s.field_of_study_id = fos.id
                    JOIN university u ON fos.university_id = u.id
                    JOIN file_storage fs ON s.file_storage_id = fs.id
                WHERE s.id = :id
                """;

        Query query = entityManager.createNativeQuery(sql, StudentResponseDto.class);
        query.setParameter("id", studentId);

        try {
            return (StudentResponseDto) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
