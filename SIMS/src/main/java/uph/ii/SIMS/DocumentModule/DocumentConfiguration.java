package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.DocumentModule.DziennikPraktyk.DziennikPraktykConfiguration;
import uph.ii.SIMS.DocumentModule.DziennikPraktyk.DziennikPraktykFacade;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieConfiguration;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieFacade;
import uph.ii.SIMS.DocumentModule.PlanPraktyki.PlanPraktykiConfiguration;
import uph.ii.SIMS.DocumentModule.PlanPraktyki.PlanPraktykiFacade;
import uph.ii.SIMS.DocumentModule.Porozumienie.PorozumienieConfiguration;
import uph.ii.SIMS.DocumentModule.Porozumienie.PorozumienieFacade;
import uph.ii.SIMS.DocumentModule.Zaswiadczenie.Zaswiadczenie;
import uph.ii.SIMS.DocumentModule.Zaswiadczenie.ZaswiadczenieConfiguration;
import uph.ii.SIMS.DocumentModule.Zaswiadczenie.ZaswiadczenieFacade;
import uph.ii.SIMS.PdfCreationService.PdfBuilder;
import uph.ii.SIMS.UserModule.UserFacade;

/**
 *
 * Klasa odpowiedzialna za utworzenie fasady modułu dokumentów
 */
@Configuration
@AllArgsConstructor
class DocumentConfiguration {
    
    private DocumentRepository documentRepository;
    
    /**
     * Metoda oznaczona adnotacją {@link Bean}, wykorzystywana przez Spring, nie używać ręcznie. Kontener IoC springa zajmie się wstrzyknięciem wszystkich zależności
     *
     * @return Fasada modułu działająca na bazie danych
     */
    @Bean
    DocumentFacade documentFacade(OswiadczenieFacade oswiadczenieFacade,
                                  PorozumienieFacade porozumienieFacade,
                                  ZaswiadczenieFacade zaswiadczenieFacade,
                                  DziennikPraktykFacade dziennikPraktykFacade,
                                  PlanPraktykiFacade planPraktykiFacade,
                                  PdfBuilder pdfBuilder,
                                  UserFacade userFacade) {
        return new DocumentFacade(oswiadczenieFacade, pdfBuilder, porozumienieFacade, documentRepository, userFacade,zaswiadczenieFacade,dziennikPraktykFacade, planPraktykiFacade );
    }
    
    /**
     * Metoda tworząca fasadę podmodułu działającą na repozytorium w pamięci wykorzystywana w testach
     *
     * @return Fasada modułu na potrzeby testów, działająca w pamięci
     */
    DocumentFacade documentFacadeInMemoryIO(PdfBuilder pdfBuilder, UserFacade userFacade) {
        OswiadczenieFacade oswiadczenieFacadeInMemoryIO =
            new OswiadczenieConfiguration().oswiadczenieFacadeInMemoryIO();
        
        PorozumienieFacade porozumienieFacadeInMemoryIO = new PorozumienieConfiguration()
            .porozumienieFacadeInMemoryIO();

        ZaswiadczenieFacade zaswiadczenieFacadeInMemoryIO = new ZaswiadczenieConfiguration()
                .zaswiadczenieFacadeInMemoryIO();

        DziennikPraktykFacade dziennikPraktykFacadeInMemoryIO = new DziennikPraktykConfiguration()
                .dziennikPraktykFacadeInMemoryIO();

        PlanPraktykiFacade planPraktykiFacadeInMemoryIO = new PlanPraktykiConfiguration()
                .PlanPraktykiFacadeInMemoryIO();
        
        return new DocumentFacade(oswiadczenieFacadeInMemoryIO, pdfBuilder, porozumienieFacadeInMemoryIO, documentRepository, userFacade, zaswiadczenieFacadeInMemoryIO,dziennikPraktykFacadeInMemoryIO,planPraktykiFacadeInMemoryIO );
    }
}
    

