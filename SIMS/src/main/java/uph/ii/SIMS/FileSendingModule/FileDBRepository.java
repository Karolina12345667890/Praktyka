package uph.ii.SIMS.FileSendingModule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDBRepository extends JpaRepository<FileData, Long> {

    FileData save(FileData fileData);
    void deleteById(Long Id);
    boolean existsById(Long Id);
    Optional<FileData> findById(Long Id);

}
