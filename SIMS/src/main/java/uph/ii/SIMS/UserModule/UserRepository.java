package uph.ii.SIMS.UserModule;

import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * <p>
 * Interfejs zawierający metody związane z persystencją dla encji {@link User użytkownika}
 * </p>
 * <p>
 * Spring automatycznie generuje klasę implementującą ten interfejs
 * </p>
 */
interface UserRepository extends Repository<User, Long> {
    /**
     * Zapisuje Użytkownika do repozytorium
     * @param user użytkownik do zapisania
     */
    void save(User user);
    
    /**
     * Pobiera użytkownika o podanym id
     * @param id id użytkownika do pobrania
     * @return Optional z użytkownikiem, pusty w przypadku braku takiego użytkownika
     */
    Optional<User> getUserById(Long id);
    
}
