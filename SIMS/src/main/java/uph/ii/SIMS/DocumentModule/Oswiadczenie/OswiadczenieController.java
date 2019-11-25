package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieFillDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;

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
    
    @PostMapping(value = "/{id}")
    void storeOswiadczenie(@PathVariable Long id, @RequestBody OswiadczenieFillDto fillDto) {
        OswiadczenieDto oswiadczenieDto = new OswiadczenieDto(
            id,
            null,
            null,
            fillDto.getOpiekunI(),
            fillDto.getOpiekunN(),
            fillDto.getOpiekunMail(),
            fillDto.getOpiekunTel(),
            fillDto.getStudentDuties(),
            null,
            null
        );
        documentFacade.storeOswiadczenie(oswiadczenieDto);
        
    }
    
    @PostMapping(value = "/{id}/comment")
    void setOswiadczenieComment(@PathVariable Long id, @RequestBody(required = false) String newComment) {
        documentFacade.setOswiadczenieComment(id, newComment);
    }
    
    @PostMapping(value = "/{id}/accept")
    void acceptOswiadczenie(@PathVariable Long id) {
        documentFacade.setOswiadczenieStatus(id, StatusEnum.ACCEPTED);
    }
    
    @PostMapping(value = "/{id}/decline")
    void declineOswiadczenie(@PathVariable Long id) {
        documentFacade.setOswiadczenieStatus(id, StatusEnum.DECLINED);
    }
}
