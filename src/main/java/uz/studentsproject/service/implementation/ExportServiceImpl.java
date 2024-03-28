package uz.studentsproject.service.implementation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.aggregation.model.FileStorage;
import uz.studentsproject.repository.FileStorageRepository;
import uz.studentsproject.service.ExportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {


    private final EntityManager entityManager;
    private final FileStorageRepository fileStorageRepository;
    @Value("${upload.folder}")
    private String uploadFolder;

    @Override
    public byte[] exportToExcel(Pageable pageable) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student's List");
        rowGenerator(new String[]{"ID", "First Name", "Middle Name",
                "Last Name", "Description", "Study State Date", "Study End Date",
                "Gender", "Field of Study", "University"}, sheet, workbook);

        int rowNum = 1;
        for (StudentResponseDto studentResponseDto : getAllStudents(pageable)) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(studentResponseDto.getId());
            row.createCell(1).setCellValue(studentResponseDto.getFirstName());
            row.createCell(2).setCellValue(studentResponseDto.getMiddleName());
            row.createCell(3).setCellValue(studentResponseDto.getLastName());
            row.createCell(4).setCellValue(studentResponseDto.getDescription());
            row.createCell(5).setCellValue(studentResponseDto.getStudyStateDate().toString());
            row.createCell(6).setCellValue(studentResponseDto.getStudyEndDate().toString());
            row.createCell(7).setCellValue(studentResponseDto.getGender());
            row.createCell(8).setCellValue(studentResponseDto.getFieldOfStudyName());
            row.createCell(9).setCellValue(studentResponseDto.getUniversityName());
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (Exception e) {
            log.info("Error in writing excel file");
            throw new RuntimeException("Error in writing excel file");
        }
        log.info("Excel Created");
        return bos.toByteArray();
    }

    @Override
    public byte[] exportToPdf(Pageable pageable) throws DocumentException {
        try {
            Document document = new Document();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, bos);
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Paragraph title = new Paragraph("Student's List", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font tableBodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            PdfPTable table = new PdfPTable(10);
            table.addCell(new Phrase("ID", tableHeaderFont));
            table.addCell(new Phrase("First Name", tableHeaderFont));
            table.addCell(new Phrase("Middle Name", tableHeaderFont));
            table.addCell(new Phrase("Last Name", tableHeaderFont));
            table.addCell(new Phrase("Description", tableHeaderFont));
            table.addCell(new Phrase("Study State Date", tableHeaderFont));
            table.addCell(new Phrase("Study End Date", tableHeaderFont));
            table.addCell(new Phrase("Gender"));
            table.addCell(new Phrase("Field of Study", tableHeaderFont));
            table.addCell(new Phrase("University", tableHeaderFont));
            getAllStudents(pageable).forEach(studentResponseDto -> {
                table.addCell(new Phrase(studentResponseDto.getId().toString(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getFirstName(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getMiddleName(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getLastName(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getDescription(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getStudyStateDate().toString(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getStudyEndDate().toString(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getGender(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getFieldOfStudyName(), tableBodyFont));
                table.addCell(new Phrase(studentResponseDto.getUniversityName(), tableBodyFont));
            });

            document.add(table);
            document.close();
            log.info("PDF created");
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error in writing PDF file");
        }
    }


    @Override
    public List<StudentResponseDto> getAllStudents(Pageable pageable) {
        try {
            String sql = ("""
                    SELECT s.id              AS id,
                           s.first_name      AS first_name,
                           s.last_name       AS last_name,
                           s.middle_name     AS middle_name,
                           s.description     AS description,
                           s.study_state_date AS study_state_date,
                           s.study_end_date  AS study_end_date,
                           s.gender          AS gender,
                           fos.name          AS field_of_study_name,
                           u.name            AS university_name,
                           s.file_storage_id             AS file_storage_id
                           
                    FROM student s
                        JOIN field_of_study fos ON s.field_of_study_id = fos.id
                        JOIN university u ON fos.university_id = u.id
                                        
                                """);
            Query query = entityManager.createNativeQuery(sql, StudentResponseDto.class);
            List<StudentResponseDto> studentResponseDtos = query.getResultList();
            return studentResponseDtos;
        } catch (Exception e) {
            throw new RuntimeException("Error in getting students, {}", e);
        }
    }

    @Override
    public byte[] pdfResume(Long id) {
        StudentResponseDto studentInfo = getStudentInfo(id);
        assert studentInfo != null;
        FileStorage fileStorage = fileStorageRepository.findById(studentInfo.getFileStorageId()).orElseThrow();
        String path = "C:\\Users\\asadb\\Desktop\\Projects\\Projects\\StudentsProject\\" + this.uploadFolder + "\\"
                + fileStorage.getUploadPath();
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            // Write student information
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Name: " + studentInfo.getFirstName() + " " + studentInfo.getLastName());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Description: " + studentInfo.getDescription());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("University: " + studentInfo.getUniversityName());
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Field of Study: " + studentInfo.getFieldOfStudyName());
            // Add more student information as needed
            contentStream.endText();

            // Load student's photo
            PDImageXObject photo = PDImageXObject.createFromFile(this.uploadFolder + "\\" +
                    fileStorage.getUploadPath(), document);
            // Draw student's photo on the PDF
            contentStream.drawImage(photo, 420, 700, 100, 120); // Adjust the position and size as needed

            contentStream.close();

            // Save the PDF content to byte array
            document.save(outputStream);

            // Return the PDF content as byte array
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] pdfResume2(Long id, HttpServletResponse httpServletResponse) throws IOException, DocumentException {

        StudentResponseDto studentInfo = getStudentInfo(id);
        assert studentInfo != null;
        FileStorage fileStorage = fileStorageRepository.findById(studentInfo.getFileStorageId()).orElseThrow();
        String path = "C:\\Users\\asadb\\Desktop\\Projects\\Projects\\StudentsProject\\" + this.uploadFolder + "\\"
                + fileStorage.getUploadPath();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(10);
            font.setColor(BaseColor.RED);

            Paragraph paragraph = new Paragraph(studentInfo.getFirstName() + " " + studentInfo.getLastName() + " Resume", font);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);

            Image image = Image.getInstance(path);
            image.scaleToFit(100f, 100f);
            image.scaleAbsolute(200f, 200f);
            PdfPTable pdfPTable = new PdfPTable(3);
            image.setAlignment(Image.ALIGN_RIGHT);
            pdfPTable.getDefaultCell().setBorder(0);
            pdfPTable.addCell("");
            pdfPTable.addCell("");
            pdfPTable.addCell(image);

            document.add(pdfPTable);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(9);
            table.setSpacingAfter(25);
            table.setSpacingBefore(25);

            PdfPCell[] headers = new PdfPCell[]{
                    new PdfPCell(new Phrase("First Name", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("Last Name", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("Middle Name", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("University", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("Field of Study", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("Description", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("Study Start Date", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("Study End Date", FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase("Gender", FontFactory.getFont(FontFactory.HELVETICA, 8))),

            };

            PdfPCell[] data = new PdfPCell[]{
                    new PdfPCell(new Phrase(studentInfo.getFirstName(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getLastName(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getMiddleName(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getUniversityName(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getFieldOfStudyName(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getStudyStateDate().toString(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getStudyEndDate().toString(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
                    new PdfPCell(new Phrase(studentInfo.getGender(), FontFactory.getFont(FontFactory.HELVETICA, 8))),
            };

            for (PdfPCell cell : headers) {
                cell.setBackgroundColor(BaseColor.GRAY);
                table.addCell(cell);
            }

            for (PdfPCell cell : data) {
                table.addCell(cell);
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here, you may log it or return an error response
            return null;
        }
    }



    public void rowGenerator(String[] headers, Sheet sheet, Workbook workbook) {

        Row headRow = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
        for (int i = 0; i < headers.length; i++) {
            headRow.createCell(i).setCellStyle(cellStyle);
            headRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private StudentResponseDto getStudentInfo(Long studentId) {
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
