package uph.ii.SIMS.UserModule;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    public void save(User user);
    
    public Optional<User> getUserById(Long id);
    
}
