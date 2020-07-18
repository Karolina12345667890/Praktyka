package uph.ii.SIMS.DocumentModule.Zaswiadczenie;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Klasa odpowiedzialna za utworzenie fasady podmodułu porozumień
 */
@Configuration
@AllArgsConstructor
public class ZaswiadczenieConfiguration {

    /**
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param zaswiadczenieRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link ZaswiadczenieRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    ZaswiadczenieFacade ZaswiadczenieFacade(ZaswiadczenieRepository zaswiadczenieRepository) {
        return new ZaswiadczenieFacade(zaswiadczenieRepository);
    }

    /**
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci ({@link ZaswiadczenieInMemoryRepository}) wykorzystywana w testach
     *
     * @return Fasada podmodułu na potrzeby testów, działająca w pamięci
     */
    public ZaswiadczenieFacade zaswiadczenieFacadeInMemoryIO() {
        ZaswiadczenieRepository ZaswiadczenieInMemoryRepository = new ZaswiadczenieInMemoryRepository();
        return new ZaswiadczenieFacade(ZaswiadczenieInMemoryRepository);
    }

}
