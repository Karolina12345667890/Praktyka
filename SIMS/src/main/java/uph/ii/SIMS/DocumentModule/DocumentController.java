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
    
    @GetMapping(Document.URL + "list")
    List<DocumentDto> listMyDocuments(){
        return documentFacade.listMyDocuments();
    }
    
}
