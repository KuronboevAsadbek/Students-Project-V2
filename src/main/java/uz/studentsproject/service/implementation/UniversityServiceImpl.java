package uz.studentsproject.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.studentsproject.aggregation.dto.request.UniversityRequestDto;
import uz.studentsproject.aggregation.dto.response.ResponseDTO;
import uz.studentsproject.aggregation.dto.response.UniversityResponseDto;
import uz.studentsproject.aggregation.model.University;
import uz.studentsproject.exception.UniversityException;
import uz.studentsproject.mapping.UniversityMapper;
import uz.studentsproject.repository.FieldOfStudyRepository;
import uz.studentsproject.repository.UniversityRepository;
import uz.studentsproject.service.FieldOfStudyService;
import uz.studentsproject.service.UniversityService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;
    private final FieldOfStudyRepository fieldOfStudyRepository;


    @Override
    //creating or updating university
    public ResponseDTO<UniversityResponseDto> createOrUpdate(UniversityRequestDto universityRequestDto, Long id) {
        ResponseDTO<UniversityResponseDto> responseDTO = new ResponseDTO<>();
        University university = universityMapper.toEntity(universityRequestDto);

        //checking id to null. if id is not null, then we are updating university
        if (id != null) {
            university = universityRepository.findById(id).orElseThrow(() -> new UniversityException("University not found"));
            universityMapper.updateFromDto(universityRequestDto, university);
            university = universityRepository.save(university);
            responseDTO.setMessage("University updated");
            log.info("University updated");
            responseDTO.setSuccess(true);
            responseDTO.setData(universityMapper.toDto(university));
            return responseDTO;
        } else {
            university = universityRepository.save(university);
            log.info("University created");
            responseDTO.setMessage("University created");
            responseDTO.setData(universityMapper.toDto(university));
            responseDTO.setSuccess(true);
            return responseDTO;
        }
    }

    @Override
    //getting all universities
    public List<UniversityResponseDto> getAll(Pageable pageable) {
        List<University> universityList = universityRepository.findAll(pageable).getContent();
        log.info("UniversityServiceImpl.getAll");
        return universityMapper.toDto(universityList);
    }

    @Override
    public UniversityResponseDto getById(Long id) {
        University university = universityRepository.findById(id).orElseThrow(() ->
                new UniversityException("University not found"));
        log.info("UniversityServiceImpl.getById");
        return universityMapper.toDto(university);
    }

    @Override
    public ResponseDTO<UniversityResponseDto> delete(Long id) {
        ResponseDTO<UniversityResponseDto> responseDTO = new ResponseDTO<>();
        universityRepository.findById(id).ifPresent(universityRepository::delete);
        fieldOfStudyRepository.updatingUniversityId(id);
        responseDTO.setMessage("University deleted");
        responseDTO.setSuccess(true);
        log.info("University deleted");
        return responseDTO;
    }
}


