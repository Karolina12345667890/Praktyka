package uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienieFacade;
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienieInMemoryRepository;
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienieRepository;

/**
 * Klasa odpowiedzialna za utworzenie fasady podmodułu porozumień
 */
@Configuration
@AllArgsConstructor
public class ZaswiadczenieZatrudnienieConfiguration {

    /**
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param zaswiadczenieZatrudnienieRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link ZaswiadczenieZatrudnienieRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    ZaswiadczenieZatrudnienieFacade ZaswiadczenieZatrudnienieFacade(ZaswiadczenieZatrudnienieRepository zaswiadczenieZatrudnienieRepository) {
        return new ZaswiadczenieZatrudnienieFacade(zaswiadczenieZatrudnienieRepository);
    }

    /**
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci ({@link ZaswiadczenieZatrudnienieInMemoryRepository}) wykorzystywana w testach
     *
     * @return Fasada podmodułu na potrzeby testów, działająca w pamięci
     */
    public ZaswiadczenieZatrudnienieFacade zaswiadczenieZatrudnienieFacadeInMemoryIO() {
        ZaswiadczenieZatrudnienieRepository ZaswiadczenieZatrudnienieInMemoryRepository = new ZaswiadczenieZatrudnienieInMemoryRepository();
        return new ZaswiadczenieZatrudnienieFacade(ZaswiadczenieZatrudnienieInMemoryRepository);
    }
}
