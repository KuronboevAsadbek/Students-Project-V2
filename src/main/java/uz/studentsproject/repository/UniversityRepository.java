package uz.studentsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.studentsproject.aggregation.model.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

//    boolean existsByName(String name);

//    University findByName(String name);

}
