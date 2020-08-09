package uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;
import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieZatrudnienieDto;
import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieZatrudnienieFillDto;

/**
 * Kontroler odpowiedzialny za dokument zaswiadczenia
 */
@RestController
@RequestMapping(value = Document.URL + ZaswiadczenieZatrudnienie.DOCUMENT_TYPE)
@AllArgsConstructor
class ZaswiadczenieZatrudnienieController {

    private DocumentFacade documentFacade;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ZaswiadczenieZatrudnienieDto fetchZaswiadczenieZatrudnienieDto(@PathVariable Long id) {
        return documentFacade.fetchZaswiadczenieZatrudnienie(id);
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createZaswiadczenieZatrudnieniePdf(@PathVariable Long id) throws Exception {
        return documentFacade.printZaswiadczenieZatrudnienieToPdf(id);
    }

    @PostMapping(value = "/{id}")
    void storeZaswiadczenieZatrudnienie(@PathVariable Long id, @RequestBody ZaswiadczenieZatrudnienieFillDto fillDto) {
        ZaswiadczenieZatrudnienieDto ZaswiadczenieZatrudnienieDto = new ZaswiadczenieZatrudnienieDto(
                id,
                null,
                null,
                fillDto.getStudentRoad(),
                fillDto.getStudentCity(),
                fillDto.getStudentZip(),
                fillDto.getStudentPesel(),
                fillDto.getStudentInternshipStart(),
                fillDto.getStudentInternshipEnd(),
                fillDto.getCompanyName(),
                fillDto.getStudentPosition(),
                fillDto.getHoursPerWeek(),
                fillDto.getStudentTasks(),
                null,
                null
        );
        documentFacade.storeZaswiadczenieZatrudnienie(ZaswiadczenieZatrudnienieDto);

    }

    @PostMapping(value = "/{id}/comment")
    void setZaswiadczenieZatrudnienieComment(@PathVariable Long id, @RequestBody(required = false)  String newComment) {
        documentFacade.setZaswiadczenieZatrudnienieComment(id, newComment);
    }

    @PostMapping(value = "/{id}/accept")
    void acceptZaswiadczenieZatrudnienie(@PathVariable Long id) {
        documentFacade.setZaswiadczenieZatrudnienieStatus(id, StatusEnum.ACCEPTED);
    }

    @PostMapping(value = "/{id}/decline")
    void declineZaswiadczenieZatrudnienie(@PathVariable Long id) {
        documentFacade.setZaswiadczenieZatrudnienieStatus(id, StatusEnum.DECLINED);
    }

}

