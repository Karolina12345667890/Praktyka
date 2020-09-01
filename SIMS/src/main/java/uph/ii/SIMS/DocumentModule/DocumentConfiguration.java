package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.DocumentModule.AnkietaPracodawcy.AnkietaPracownikConfiguration;
import uph.ii.SIMS.DocumentModule.AnkietaPracodawcy.AnkietaPracownikFacade;
import uph.ii.SIMS.DocumentModule.AnkietaStudenta.AnkietaStudentaConfiguration;
import uph.ii.SIMS.DocumentModule.AnkietaStudenta.AnkietaStudentaFacade;
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
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienie;
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienieConfiguration;
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienieFacade;
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
    DocumentFacade documentFacade(AnkietaPracownikFacade ankietaPracownikFacade,
                                  AnkietaStudentaFacade ankietaStudentaFacade,
                                  OswiadczenieFacade oswiadczenieFacade,
                                  PorozumienieFacade porozumienieFacade,
                                  ZaswiadczenieFacade zaswiadczenieFacade,
                                  DziennikPraktykFacade dziennikPraktykFacade,
                                  PlanPraktykiFacade planPraktykiFacade,
                                  ZaswiadczenieZatrudnienieFacade zaswiadczenieZatrudnienieFacade,
                                  PdfBuilder pdfBuilder,
                                  UserFacade userFacade) {
        return new DocumentFacade(oswiadczenieFacade, pdfBuilder, porozumienieFacade, documentRepository, userFacade,zaswiadczenieFacade,dziennikPraktykFacade, planPraktykiFacade,zaswiadczenieZatrudnienieFacade,ankietaStudentaFacade, ankietaPracownikFacade );
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
                .planPraktykiFacadeInMemoryIO();

        ZaswiadczenieZatrudnienieFacade zaswiadczenieZatrudnienieFacadeInMemoryIO = new ZaswiadczenieZatrudnienieConfiguration()
                .zaswiadczenieZatrudnienieFacadeInMemoryIO();

        AnkietaStudentaFacade ankietaStudentaFacadeInMemoryIO =
                new AnkietaStudentaConfiguration().ankietaStudentaFacadeInMemoryIO();

        AnkietaPracownikFacade ankietaPracownikFacadeInMemoryIO =
                new AnkietaPracownikConfiguration().ankietaPracownikFacadeInMemoryIO();

        return new DocumentFacade(oswiadczenieFacadeInMemoryIO, pdfBuilder, porozumienieFacadeInMemoryIO, documentRepository, userFacade, zaswiadczenieFacadeInMemoryIO,dziennikPraktykFacadeInMemoryIO,planPraktykiFacadeInMemoryIO,zaswiadczenieZatrudnienieFacadeInMemoryIO, ankietaStudentaFacadeInMemoryIO, ankietaPracownikFacadeInMemoryIO);
    }
}
    

