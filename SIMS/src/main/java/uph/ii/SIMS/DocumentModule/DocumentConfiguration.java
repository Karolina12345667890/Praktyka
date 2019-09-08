package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieConfiguration;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieFacade;
import uph.ii.SIMS.DocumentModule.Porozumienie.PorozumienieConfiguration;
import uph.ii.SIMS.DocumentModule.Porozumienie.PorozumienieFacade;
import uph.ii.SIMS.PdfCreationService.PdfBuilder;
import uph.ii.SIMS.UserModule.UserFacade;

/**
 * Klasa odpowiedzialna za utworzenie fasady modułu dokumentów
 *
 */
@Configuration
@AllArgsConstructor
class DocumentConfiguration {
    
    /**
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie. Kontener IoC springa zajmie się wstrzyknięciem wszystkich zależności
     * @return Fasada modułu działająca na bazie danych
     */
    @Bean
    DocumentFacade documentFacade(OswiadczenieFacade oswiadczenieFacade,
                                  PorozumienieFacade porozumienieFacade,
                                  PdfBuilder pdfBuilder,
                                  UserFacade userFacade) {
        return new DocumentFacade(pdfBuilder, oswiadczenieFacade, porozumienieFacade, userFacade);
    }
    
    /**
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci wykorzystywana w testach
     * @return Fasada modułu na potrzeby testów, działająca w pamięci
     */
    DocumentFacade documentFacadeInMemoryIO(PdfBuilder pdfBuilder, UserFacade userFacade) {
        OswiadczenieFacade oswiadczenieFacadeInMemoryIO =
            new OswiadczenieConfiguration(userFacade).oswiadczenieFacadeInMemoryIO();
        
        PorozumienieFacade porozumienieFacadeInMemoryIO = new PorozumienieConfiguration(userFacade)
            .porozumienieFacadeInMemoryIO();
        
        
        return new DocumentFacade(pdfBuilder, oswiadczenieFacadeInMemoryIO, porozumienieFacadeInMemoryIO, userFacade);
    }
}
    

