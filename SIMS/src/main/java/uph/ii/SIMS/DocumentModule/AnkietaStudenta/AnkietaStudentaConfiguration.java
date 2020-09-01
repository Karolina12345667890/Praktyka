package uph.ii.SIMS.DocumentModule.AnkietaStudenta;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.UserModule.UserFacade;
import uph.ii.SIMS.UserModule.UserRepository;

/**
 *
 * Klasa odpowiedzialna za utworzenie fasady podmodułu ankiet
 */

@Configuration
@AllArgsConstructor
public class AnkietaStudentaConfiguration {
    /**
     *
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param ankietaStudentaRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link AnkietaStudentaRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    AnkietaStudentaFacade ankietaStudentaFacade(AnkietaStudentaRepository ankietaStudentaRepository, AnkietaStudentaService ankietaStudentaService) {
        return new AnkietaStudentaFacade(ankietaStudentaRepository, ankietaStudentaService);
    }

    /**
     * Metoda wykorzystywana przy testach
     *
     * Metoda nie uzupełniona
     * @return
     */
    public AnkietaStudentaFacade ankietaStudentaFacadeInMemoryIO() {
        AnkietaStudentaRepository ankietaStudentaInMemoryRepository = new AnkietaStudentaMemoryRepository();
        AnkietaStudentaService ankietaStudentaService = new AnkietaStudentaService();

        return new AnkietaStudentaFacade(ankietaStudentaInMemoryRepository,ankietaStudentaService);
    }
}
