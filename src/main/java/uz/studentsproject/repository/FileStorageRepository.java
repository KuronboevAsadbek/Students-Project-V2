package uz.studentsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.studentsproject.aggregation.model.FileStorage;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {

    FileStorage findByHashId(String hashId);

    @Query(value = "DELETE FROM file_storage WHERE id = ?1", nativeQuery = true)
    @Modifying
    void deleteByUserId(Long id);
}
