package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Klasa odpowiedzialna za utworzenie fasady modułu użytkowników
 */
@Configuration
@AllArgsConstructor
class UserConfiguration {
    
    private UserRepository userRepository;
    
    /**
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     * @return Fasada modułu działająca na bazie danych
     */
    @Bean
    UserFacade userFacade() {
        return new UserFacade(userRepository);
    }
    
    /**
     * Metoda tworząca fasadę modułu działającą na repozytorium w pamięci ({@link UserInMemoryRepository}) wykorzystywana w testach
     *
     * @return Fasada modułu na potrzeby testów, działająca w pamięci
     */
    UserFacade userFacadeInMemoryIO() {
        return new UserFacade(new UserInMemoryRepository());
    }
}
