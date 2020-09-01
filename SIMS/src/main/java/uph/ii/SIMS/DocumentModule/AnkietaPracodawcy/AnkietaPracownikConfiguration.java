package uph.ii.SIMS.DocumentModule.AnkietaPracodawcy;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Klasa odpowiedzialna za utworzenie fasady podmodułu ankiety pracownika
 */

@Configuration
@AllArgsConstructor
public class AnkietaPracownikConfiguration {
    /**
     *
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param ankietaPracownikRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link AnkietaPracownikRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    AnkietaPracownikFacade PracownikFacade(AnkietaPracownikRepository ankietaPracownikRepository, AnkietaPracownikService ankietaPracownikService) {
        return new AnkietaPracownikFacade(ankietaPracownikRepository, ankietaPracownikService);
    }

    /*
    Metoda wykorzystywana przy testach
     */
    public AnkietaPracownikFacade ankietaPracownikFacadeInMemoryIO() {
        AnkietaPracownikRepository ankietaPracownikInMemoryRepository = new AnkietaPracownikMemoryRepository();
        AnkietaPracownikService ankietaPracownikService = new AnkietaPracownikService();

        return new AnkietaPracownikFacade(ankietaPracownikInMemoryRepository, ankietaPracownikService);
    }
}
