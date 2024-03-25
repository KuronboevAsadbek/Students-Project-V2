package uz.studentsproject.service.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.studentsproject.aggregation.dto.request.FieldOfStudyRequestDto;
import uz.studentsproject.aggregation.dto.response.FieldOfStudyResponseDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.model.FieldOfStudy;
import uz.studentsproject.exception.FieldOfStudyException;
import uz.studentsproject.mapping.FieldOfStudyMapper;
import uz.studentsproject.repository.FieldOfStudyRepository;
import uz.studentsproject.repository.StudentRepository;
import uz.studentsproject.repository.UniversityRepository;
import uz.studentsproject.service.FieldOfStudyService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FieldOfStudyServiceImpl implements FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final EntityManager entityManager;
    private final FieldOfStudyMapper fieldOfStudyMapper;
    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;


    @Override
    @Transactional
    public ResponseDTO<FieldOfStudyResponseDto> createOrUpdate(FieldOfStudyRequestDto fieldOfStudyRequestDto, Long id) {
        ResponseDTO<FieldOfStudyResponseDto> responseDTO = new ResponseDTO<>();
        FieldOfStudyResponseDto fieldOfStudyResponseDto = new FieldOfStudyResponseDto();
        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toEntity(fieldOfStudyRequestDto);
        if (id != null) {
            fieldOfStudy = fieldOfStudyRepository.findById(id).orElseThrow(() ->
                    new FieldOfStudyException("Field of study not found"));
            fieldOfStudyMapper.updateFromDto(fieldOfStudyRequestDto, fieldOfStudy);
            fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
            responseDTO.setMessage("Field of study is updated");
            responseDTO.setSuccess(true);
            fieldOfStudyResponseDto = fieldOfStudyMapper.toDto(fieldOfStudy);
            fieldOfStudyResponseDto.setUniversityName(universityRepository
                    .findById(fieldOfStudyRequestDto.getUniversityId()).get().getName());
            responseDTO.setData(fieldOfStudyResponseDto);
            log.info("Field of study is updated");
            return responseDTO;
        }
        fieldOfStudyResponseDto = fieldOfStudyMapper.toDto(fieldOfStudy);
        fieldOfStudyResponseDto.setUniversityName(universityRepository
                .findById(fieldOfStudyRequestDto.getUniversityId()).get().getName());
        responseDTO.setData(fieldOfStudyResponseDto);
        fieldOfStudyRepository.save(fieldOfStudy);
        responseDTO.setMessage("Field of study is created");
        responseDTO.setSuccess(true);
        log.info("Field of study is created");
        return responseDTO;
    }

    @Override
    public ResponseDTO<List<FieldOfStudyResponseDto>> getAll(Pageable pageable) {
        ResponseDTO<List<FieldOfStudyResponseDto>> responseDTO = new ResponseDTO<>();
        try {
            String sql = ("""
                    SELECT fos.id            AS id,
                           fos.name          AS name,
                           u.name            AS university_name
                    FROM field_of_study fos
                        JOIN university u ON fos.university_id = u.id
                        """);
            Query query = entityManager.createNativeQuery(sql, FieldOfStudyResponseDto.class);
            List<FieldOfStudyResponseDto> fieldOfStudyDtos = query.getResultList();
            log.info("Field of study list found");
            responseDTO.setData(fieldOfStudyDtos);
            responseDTO.setSuccess(true);
            responseDTO.setMessage("Field of study list found");
            return responseDTO;
        } catch (Exception e) {
            log.error("Field of study not found");
            throw new FieldOfStudyException("Field of study not found");
        }
    }

    @Override
    public ResponseDTO<FieldOfStudyResponseDto> delete(Long id) {
        fieldOfStudyRepository.findById(id).ifPresent(fieldOfStudyRepository::delete);
        studentRepository.deleteById(id);
        studentRepository.updateFieldOfStudyId(id);
        ResponseDTO<FieldOfStudyResponseDto> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(true);
        log.info("Field of study deleted");
        responseDTO.setMessage("Field of study deleted");
        return responseDTO;
    }

}
