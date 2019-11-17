package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Kontroler odpowiedzialny za dokument oświadczenia
 */
@RestController
@RequestMapping(value = Document.URL + Oswiadczenie.DOCUMENT_TYPE)
@AllArgsConstructor
class OswiadczenieController {
    private DocumentFacade documentFacade;
    
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OswiadczenieDto fetchOswiadczenieDto(@PathVariable Long id) {
        return documentFacade.fetchOswiadczenie(id);
    }
    
    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createOswiadczeniePdf(@PathVariable Long id) throws Exception {
        return documentFacade.printOswiadczenieToPdf(id);
    }
    
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Object> postOswiadczenie(@PathVariable Long id, @RequestBody OswiadczenieDto dto) throws Exception {
        System.out.println(dto);
        documentFacade.storeOswiadczenie(dto);
        return documentFacade.oswiadczenieFacade.findMyDocuments().stream().collect(Collectors.toList());
    }
    
    
}
