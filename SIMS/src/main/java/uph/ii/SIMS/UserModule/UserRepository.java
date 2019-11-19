package uph.ii.SIMS.UserModule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String username);
    List<User> findAllByIdIn(List<Long> ids);
}
