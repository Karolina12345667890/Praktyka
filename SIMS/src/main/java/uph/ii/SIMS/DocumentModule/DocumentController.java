package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;

@Configuration // TODO delete this, used to fill the DB
@RestController
@AllArgsConstructor
class DocumentController {
    
    private DocumentFacade documentFacade;
    
    @Bean
        // TODO delete this, used to fill the DB
    void fillValues() throws Exception {
        documentFacade.storeOswiadczenie(OswiadczenieDto.builder()
            .opiekunI("Adam")
            .opiekunN("Nowak")
            .opiekunMail("adam.nowak@gmail.com")
            .opiekunTel("123456789")
            .build()
        );
        documentFacade.storePorozumienie(PorozumienieDto.builder()
            .build()
        );
    }
    
    
    @RestController
    @RequestMapping(headers = "Document-Type=Oswiadczenie")
    class OswiadczenieController {
        @GetMapping(value = "/api/document/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        OswiadczenieDto fetchOswiadczenieDto(@PathVariable Long id) {
            return documentFacade.fetchOswiadczenie(id);
        }
        
        @GetMapping(value = "/api/document/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
        @ResponseBody
        byte[] createOswiadczeniePdf(@PathVariable Long id) throws Exception {
            return documentFacade.printOswiadczenieToPdf(id);
        }
    }
    
    @RestController
    @RequestMapping(headers = "Document-Type=Porozumienie")
    class PorozumienieController {
        @GetMapping(value = "/api/document/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        PorozumienieDto fetchPorozumienieDto(@PathVariable Long id) {
            return documentFacade.fetchPorozumienie(id);
        }
        
        @GetMapping(value = "/api/document/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
        @ResponseBody
        byte[] createPorozumieniePdf(@PathVariable Long id) throws Exception {
            return documentFacade.printPorozumienieToPdf(id);
        }
        
    }
    
    
}
