package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class DziennikPraktykConfiguration  {

    /**
     *
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie
     *
     * @param dziennikPraktykRepository Spring wstrzyknie wygenerowaną klasę implementującą {@link DziennikPraktykRepository}
     * @return Fasada podmodułu działająca na bazie danych
     */
    @Bean
    DziennikPraktykFacade dziennikPraktykFacade(DziennikPraktykRepository dziennikPraktykRepository) {
        return new DziennikPraktykFacade(dziennikPraktykRepository);
    }

    /**
     *
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci ({@link DziennikPraktykInMemoryRepository}) wykorzystywana w testach
     *
     * @return Fasada podmodułu na potrzeby testów, działająca w pamięci
     */
    public DziennikPraktykFacade dziennikPraktykFacadeInMemoryIO() {
        DziennikPraktykRepository dziennikPraktykInMemoryRepository = new DziennikPraktykInMemoryRepository();
        return new DziennikPraktykFacade(dziennikPraktykInMemoryRepository);
    }
}
