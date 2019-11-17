package uph.ii.SIMS.DocumentModule.Porozumienie;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;

/**
 *
 * Kontroler odpowiedzialny za dokument porozumienia
 */
@RestController
@RequestMapping(value = Porozumienie.URL)
@AllArgsConstructor
class PorozumienieController {
    private DocumentFacade documentFacade;
    

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    PorozumienieDto fetchPorozumienieDto(@PathVariable Long id) {
        return documentFacade.fetchPorozumienie(id);
    }
    
    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createPorozumieniePdf(@PathVariable Long id) throws Exception {
        return documentFacade.printPorozumienieToPdf(id);
    }
    
}
