package uz.studentsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.studentsproject.aggregation.model.FieldOfStudy;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE field_of_study SET university_id=NULL WHERE id=:id", nativeQuery = true)
    void updatingUniversityId(Long id);

}
