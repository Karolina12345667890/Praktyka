package uph.ii.SIMS.DocumentModule.PlanPraktyki;


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
public class PlanPraktykiConfiguration {

    /**
     *
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param planPraktykiRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link PlanPraktykiRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    PlanPraktykiFacade planPraktykiFacade(PlanPraktykiRepository planPraktykiRepository) {
        return new PlanPraktykiFacade(planPraktykiRepository);
    }

    /**
     *
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci ({@link PlanPraktykiInMemoryRepository}) wykorzystywana w testach
     *
     * @return Fasada podmodułu na potrzeby testów, działająca w pamięci
     */
    public PlanPraktykiFacade planPraktykiFacadeInMemoryIO() {
        PlanPraktykiRepository planPraktykiInMemoryRepository = new PlanPraktykiInMemoryRepository();
        return new PlanPraktykiFacade(planPraktykiInMemoryRepository);
    }
}
