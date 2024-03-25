package uz.studentsproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.studentsproject.aggregation.dto.response.StudentResponseDto;
import uz.studentsproject.aggregation.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Modifying
    @Transactional
    @Query(value ="UPDATE student  SET field_of_study_id=NULL WHERE field_of_study_id=:id", nativeQuery = true)
    void updateFieldOfStudyId(Long id);

    @Modifying
    @Transactional
    @Query(value ="UPDATE student SET file_storage_id=NULL WHERE file_storage_id=:id", nativeQuery = true)
    void updateFileStorageId(Long id);

}

