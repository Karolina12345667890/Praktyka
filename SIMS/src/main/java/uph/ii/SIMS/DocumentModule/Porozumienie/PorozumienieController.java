package uph.ii.SIMS.DocumentModule.Porozumienie;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieFillDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;

/**
 * Kontroler odpowiedzialny za dokument porozumienia
 */
@RestController
@RequestMapping(value = Document.URL + Porozumienie.DOCUMENT_TYPE)
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
    
    @PostMapping(value = "/{id}")
    void storePorozumienie(@PathVariable Long id, @RequestBody PorozumienieFillDto fillDto) {
        PorozumienieDto porozumienieDto = new PorozumienieDto(
            id,
            null,
            null,
            fillDto.getCompanyName(),
            fillDto.getCompanyLocationCity(),
            fillDto.getCompanyLocationStreet(),
            fillDto.getCompanyRepresentantName(),
            fillDto.getCompanyRepresentantSurname(),
            fillDto.getStudentInternshipStart(),
            fillDto.getStudentInternshipEnd(),
            fillDto.getStudentStudyForm(),
            fillDto.getStudentSpecialization(),
            fillDto.getDepartment(),
            null,
            null
        );
        documentFacade.storePorozumienie(porozumienieDto);
        
    }
    
    @PostMapping(value = "/{id}/comment")
    void setPorozumienieComment(@PathVariable Long id, @RequestBody(required = false)  String newComment) {
        documentFacade.setPorozumienieComment(id, newComment);
    }
    
    @PostMapping(value = "/{id}/accept")
    void acceptPorozumienie(@PathVariable Long id) {
        documentFacade.setPorozumienieStatus(id, StatusEnum.ACCEPTED);
    }
    
    @PostMapping(value = "/{id}/decline")
    void declinePorozumienie(@PathVariable Long id) {
        documentFacade.setPorozumienieStatus(id, StatusEnum.DECLINED);
    }
    
}
