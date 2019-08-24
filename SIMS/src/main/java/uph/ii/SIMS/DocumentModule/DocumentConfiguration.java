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

@Configuration
@AllArgsConstructor
class DocumentConfiguration {
    
    @Bean
    DocumentFacade documentFacade(OswiadczenieFacade oswiadczenieFacade,
                                  PorozumienieFacade porozumienieFacade,
                                  PdfBuilder pdfBuilder,
                                  UserFacade userFacade) {
        return new DocumentFacade(pdfBuilder, oswiadczenieFacade, porozumienieFacade, userFacade);
    }
    
    DocumentFacade documentFacadeInMemoryIO(PdfBuilder pdfBuilder, UserFacade userFacade) {
        OswiadczenieFacade oswiadczenieFacadeInMemoryIO =
            new OswiadczenieConfiguration(userFacade).oswiadczenieFacadeInMemoryIO();
        
        PorozumienieFacade porozumienieFacadeInMemoryIO = new PorozumienieConfiguration(userFacade)
            .porozumienieFacadeInMemoryIO();
        
        
        return new DocumentFacade(pdfBuilder, oswiadczenieFacadeInMemoryIO, porozumienieFacadeInMemoryIO, userFacade);
    }
}
    

