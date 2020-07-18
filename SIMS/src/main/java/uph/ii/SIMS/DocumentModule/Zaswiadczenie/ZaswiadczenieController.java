package uph.ii.SIMS.DocumentModule.Zaswiadczenie;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;
import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieFillDto;

/**
 * Kontroler odpowiedzialny za dokument zaswiadczenia
 */
@RestController
@RequestMapping(value = Document.URL + Zaswiadczenie.DOCUMENT_TYPE)
@AllArgsConstructor
class ZaswiadczenieController {

    private DocumentFacade documentFacade;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ZaswiadczenieDto fetchZaswiadczenieDto(@PathVariable Long id) {
       return documentFacade.fetchZaswiadczenie(id);
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createZaswiadczeniePdf(@PathVariable Long id) throws Exception {
        return documentFacade.printZaswiadczenieToPdf(id);
    }

    @PostMapping(value = "/{id}")
    void storeZaswiadczenie(@PathVariable Long id, @RequestBody ZaswiadczenieFillDto fillDto) {
        ZaswiadczenieDto ZaswiadczenieDto = new ZaswiadczenieDto(
                id,
                null,
                null,
                fillDto.getStudentInternshipStart(),
                fillDto.getStudentInternshipEnd(),
                fillDto.getStudentRating1(),
                fillDto.getStudentRating2(),
                fillDto.getStudentRating3(),
                fillDto.getStudentRating(),
                fillDto.getStudentWorks(),
                fillDto.getStudentInterests(),
                null,
                null
        );
        documentFacade.storeZaswiadczenie(ZaswiadczenieDto);

    }

    @PostMapping(value = "/{id}/comment")
    void setZaswiadczenieComment(@PathVariable Long id, @RequestBody(required = false)  String newComment) {
        documentFacade.setZaswiadczenieComment(id, newComment);
    }

    @PostMapping(value = "/{id}/accept")
    void acceptZaswiadczenie(@PathVariable Long id) {
        documentFacade.setZaswiadczenieStatus(id, StatusEnum.ACCEPTED);
    }

    @PostMapping(value = "/{id}/decline")
    void declineZaswiadczenie(@PathVariable Long id) {
        documentFacade.setZaswiadczenieStatus(id, StatusEnum.DECLINED);
    }

}
