package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
/**
 *
 * kontroler dla modułu dokumentów, posiada mapowania metod {@link DocumentFacade fasady} na adresy url
 */
class DocumentController {
    
    private DocumentFacade documentFacade;
    
    /**
     *
     * Tworzy przykładowe dane i zapisuje je do BD
     */
    // TODO delete this, used to fill the DB
    @GetMapping("/test12")
    void fillValues() throws Exception {
        documentFacade.storeOswiadczenie(OswiadczenieDto.builder()
            .opiekunI("Adam")
            .opiekunN("Nowak")
            .opiekunMail("adam.nowak@gmail.com")
            .opiekunTel("123456789")
            .comment("komentarz1")
            .build()
        );
        documentFacade.storePorozumienie(PorozumienieDto.builder()
            .comment("komentarz2")
            .build()
        );
        
    }
    
    @GetMapping("/test123")
    List<DocumentDto> listMyDocuments(){
        return documentFacade.listMyDocuments();
    }
    
    
}
