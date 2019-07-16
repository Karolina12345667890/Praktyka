package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class DocumentConfiguration {
    
    @Bean
    DocumentFacade documentsFacade() {
        PdfBuilderConfiguration pdfBuilderConfig = new PdfBuilderConfiguration();
        PdfBuilder pdfBuilder = new PdfBuilder(
            pdfBuilderConfig.getStaticResourcesLocation(),
            pdfBuilderConfig.config(),
            pdfBuilderConfig.templateEngine()
        );
        
        return new DocumentFacade(pdfBuilder);
    }
    
}
