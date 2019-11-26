package uph.ii.SIMS.DocumentModule.Porozumienie;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.UserModule.UserFacade;

/**
 * Klasa odpowiedzialna za utworzenie fasady podmodułu porozumień
 */
@Configuration
@AllArgsConstructor
public class PorozumienieConfiguration {
    
    /**
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param porozumienieRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link PorozumienieRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    PorozumienieFacade PorozumienieFacade(PorozumienieRepository porozumienieRepository) {
        return new PorozumienieFacade(porozumienieRepository);
    }
    
    /**
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci ({@link PorozumienieInMemoryRepository}) wykorzystywana w testach
     *
     * @return Fasada podmodułu na potrzeby testów, działająca w pamięci
     */
    public PorozumienieFacade porozumienieFacadeInMemoryIO() {
        PorozumienieRepository PorozumienieInMemoryRepository = new PorozumienieInMemoryRepository();
        return new PorozumienieFacade(PorozumienieInMemoryRepository);
    }
    
}
