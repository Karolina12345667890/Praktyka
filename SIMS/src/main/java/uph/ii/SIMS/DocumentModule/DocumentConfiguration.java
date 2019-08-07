package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieConfiguration;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieFacade;
import uph.ii.SIMS.PdfCreationService.PdfBuilder;
import uph.ii.SIMS.UserModule.UserFacade;

@Configuration
class DocumentConfiguration {
    
    @Bean
    DocumentFacade documentFacade(PdfBuilder pdfBuilder, UserFacade userFacade) {
        OswiadczenieFacade oswiadczenieFacade = new OswiadczenieConfiguration(userFacade)
            .oswiadczenieFacadeInMemoryIO();
        
        return new DocumentFacade(pdfBuilder, oswiadczenieFacade);
    }
    
    DocumentFacade documentFacadeInMemoryIO(PdfBuilder pdfBuilder, UserFacade userFacade) {
        OswiadczenieFacade oswiadczenieFacadeInMemoryIO =
            new OswiadczenieConfiguration(userFacade).oswiadczenieFacadeInMemoryIO();
        
        return new DocumentFacade(pdfBuilder, oswiadczenieFacadeInMemoryIO);
    }
}
    

