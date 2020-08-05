package uph.ii.SIMS.DocumentModule.PlanPraktyki;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.PlanPraktykiDto;
import uph.ii.SIMS.DocumentModule.Dto.PlanPraktykiFillDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;

/**
 * Kontroler odpowiedzialny za dokument oświadczenia
 */
@RestController
@RequestMapping(value = Document.URL + PlanPraktyki.DOCUMENT_TYPE)
@AllArgsConstructor
class PlanPraktykiController {
    private DocumentFacade documentFacade;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    PlanPraktykiDto fetchPlanPraktykiDto(@PathVariable Long id) {
        return documentFacade.fetchPlanPraktyki(id);
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createPlanPraktykiPdf(@PathVariable Long id) throws Exception {
        return documentFacade.printPlanPraktykiToPdf(id);
    }

    @PostMapping(value = "/{id}")
    void storePlanPraktyki(@PathVariable Long id, @RequestBody PlanPraktykiFillDto fillDto) {
        PlanPraktykiDto planPraktykiDto = new PlanPraktykiDto(
                id,
                null,
                null,
                fillDto.getStudentInternshipStart(),
                fillDto.getStudentInternshipEnd(),
                fillDto.getStudentTasks(),
                fillDto.getStudentPesel(),
                null,
                null
        );
        System.out.println(planPraktykiDto.getStudentPesel());
        documentFacade.storePlanPraktyki(planPraktykiDto);

    }

    @PostMapping(value = "/{id}/comment")
    void setPlanPraktykiComment(@PathVariable Long id, @RequestBody(required = false) String newComment) {
        documentFacade.setPlanPraktykiComment(id, newComment);
    }

    @PostMapping(value = "/{id}/accept")
    void acceptPlanPraktyki(@PathVariable Long id) {
        documentFacade.setPlanPraktykiStatus(id, StatusEnum.ACCEPTED);
    }

    @PostMapping(value = "/{id}/decline")
    void declinePlanPraktyki(@PathVariable Long id) {
        documentFacade.setPlanPraktykiStatus(id, StatusEnum.DECLINED);
    }
}

