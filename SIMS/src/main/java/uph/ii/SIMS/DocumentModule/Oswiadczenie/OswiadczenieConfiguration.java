package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.UserModule.UserFacade;

/**
 *
 * Klasa odpowiedzialna za utworzenie fasady podmodułu oświadczeń
 */
@Configuration
@AllArgsConstructor
public class OswiadczenieConfiguration {
    
    UserFacade userFacade;
    
    /**
     *
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param oswiadczenieRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link OswiadczenieRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    OswiadczenieFacade oswiadczenieFacade(OswiadczenieRepository oswiadczenieRepository) {
        return new OswiadczenieFacade(oswiadczenieRepository, userFacade);
    }
    
    /**
     *
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci ({@link OswiadczenieInMemoryRepository}) wykorzystywana w testach
     *
     * @return Fasada podmodułu na potrzeby testów, działająca w pamięci
     */
    public OswiadczenieFacade oswiadczenieFacadeInMemoryIO() {
        OswiadczenieRepository oswiadczenieInMemoryRepository = new OswiadczenieInMemoryRepository();
        return new OswiadczenieFacade(oswiadczenieInMemoryRepository, userFacade);
    }
}
