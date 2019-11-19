package uph.ii.SIMS.UserModule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupApplicationRepository extends JpaRepository<GroupApplication, Long> {
    List<GroupApplication> getAllByGroupId(Long groupId);
    Optional<GroupApplication> getByStudentIdAndGroupId(Long studentId, Long groupId);
}
