package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.DziennikPraktykDto;
import uph.ii.SIMS.DocumentModule.Dto.DziennikPraktykFillDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;


@RestController
@RequestMapping(value = Document.URL + DziennikPraktyk.DOCUMENT_TYPE)
@AllArgsConstructor
class DziennikPraktykController {
    private DocumentFacade documentFacade;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    DziennikPraktykDto fetchDziennikPraktykDto(@PathVariable Long id) {
        return documentFacade.fetchDziennikPraktyk(id);
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createDziennikPraktykPdf(@PathVariable Long id) throws Exception {
        return documentFacade.printDziennikPraktykToPdf(id);
    }

    @PostMapping(value = "/{id}")
    void storeDziennikPraktyk(@PathVariable Long id, @RequestBody DziennikPraktykFillDto fillDto) {
        DziennikPraktykDto dziennikPraktykDto = new DziennikPraktykDto(
                id,
                null,
                null,
                fillDto.getPeriodFrom(),
                fillDto.getPeriodTo(),
                fillDto.getStudentAlbumNumber(),
                fillDto.getCompanyName(),
                fillDto.getDiary(),
                null,
                null
        );
        documentFacade.storeDziennikPraktyk(dziennikPraktykDto);

    }

    @PostMapping(value = "/{id}/comment")
    void setDziennikPraktykComment(@PathVariable Long id, @RequestBody(required = false) String newComment) {
        documentFacade.setDziennikPraktykComment(id, newComment);
    }

    @PostMapping(value = "/{id}/accept")
    void acceptDziennikPraktyk(@PathVariable Long id) {
        documentFacade.setDziennikPraktykStatus(id, StatusEnum.ACCEPTED);
    }

    @PostMapping(value = "/{id}/decline")
    void declineDziennikPraktyk(@PathVariable Long id) {
        documentFacade.setDziennikPraktykStatus(id, StatusEnum.DECLINED);
    }
}
