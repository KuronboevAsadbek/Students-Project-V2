package uz.studentsproject.service.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.studentsproject.aggregation.dto.request.StudentRequestDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.aggregation.model.Student;
import uz.studentsproject.exception.StudentException;
import uz.studentsproject.mapping.StudentMapping;
import uz.studentsproject.repository.StudentRepository;
import uz.studentsproject.service.StudentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapping studentMapping;
    private final EntityManager entityManager;


    //Create new student and update student
    @Override
    public ResponseDTO<StudentResponseDto> createOrUpdate(StudentRequestDto studentRequestDto, Long id) {
        ResponseDTO<StudentResponseDto> responseDTO = new ResponseDTO<>();
        Student student = studentMapping.toEntity(studentRequestDto);
        if (id != null) {
            student = studentRepository.findById(id).orElseThrow(() ->
                    new StudentException("Student not found"));
            studentMapping.updateFromDto(studentRequestDto, student);
            student = studentRepository.save(student);
            responseDTO.setMessage("Student is updated");
            responseDTO.setSuccess(true);
            responseDTO.setData(studentMapping.toDto(student));
            log.info("Student is updated");
            return responseDTO;
        }
        studentRepository.save(student);
        responseDTO.setMessage("Student is created");
        responseDTO.setSuccess(true);
        responseDTO.setData(studentMapping.toDto(student));
        log.info("Student is created");
        return responseDTO;
    }

    //Get all students
    @Override
    public ResponseDTO<Page<StudentResponseDto>> getAllStudents(Pageable pageable) {
        ResponseDTO<Page<StudentResponseDto>> responseDTO = new ResponseDTO<>();
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
                           fs.id             AS file_storage_id
                           
                    FROM student s
                        JOIN field_of_study fos ON s.field_of_study_id = fos.id
                        JOIN university u ON fos.university_id = u.id
                        JOIN file_storage fs ON s.file_storage_id = fs.id\s
                    ORDER BY s.id
                    OFFSET :offset
                    LIMIT :limit
                                """);
            Query query = entityManager.createNativeQuery(sql, StudentResponseDto.class);
            query.setParameter("offset", pageable.getOffset());
            query.setParameter("limit", pageable.getPageSize());
            @SuppressWarnings("unchecked")
            List<StudentResponseDto> studentResponseDtos = query.getResultList();
            String countSql = "SELECT COUNT(*) FROM student";
            Query countQuery = entityManager.createNativeQuery(countSql);
            long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            Page<StudentResponseDto> page = new PageImpl<>(studentResponseDtos, pageable, totalCount);
            log.info("Student list found");
            responseDTO.setData(page);
            responseDTO.setMessage("Student list found");
            responseDTO.setSuccess(true);
            return responseDTO;
        } catch (Exception e) {
            log.error("Error in getting all students", e);
            throw new StudentException("Error in getting all students");
        }
    }

    //Get One student by ID
    @Override
    public ResponseDTO<StudentResponseDto> getOneStudent(Long id){
        ResponseDTO<StudentResponseDto> responseDTO = new ResponseDTO<>();
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
                           fs.id             AS file_storage_id
                           
                    FROM student s
                        JOIN field_of_study fos ON s.field_of_study_id = fos.id
                        JOIN university u ON fos.university_id = u.id
                        JOIN file_storage fs ON s.file_storage_id = fs.id WHERE s.id = :id
                                """);
            Query query = entityManager.createNativeQuery(sql, StudentResponseDto.class);
            query.setParameter("id", id);
            StudentResponseDto studentResponseDto = (StudentResponseDto) query.getSingleResult();
            log.info("Student found");
            responseDTO.setData(studentResponseDto);
            responseDTO.setMessage("Student found");
            responseDTO.setSuccess(true);
            return responseDTO;
        } catch (Exception e) {
            log.error("Student not found", e);
            throw new StudentException("Student not found");

        }
    }

    //Delete student by ID
    @Override
    public ResponseDTO<StudentResponseDto> delete(Long id) {
        studentRepository.findById(id).ifPresent(studentRepository::delete);
        ResponseDTO<StudentResponseDto> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(true);
        log.info("Student deleted");
        responseDTO.setMessage("Student deleted");
        return responseDTO;
    }

}
