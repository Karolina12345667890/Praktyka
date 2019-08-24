package uph.ii.SIMS.UserModule;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, Long> {
    void save(User user);
    
    Optional<User> getUserById(Long id);
    
}
