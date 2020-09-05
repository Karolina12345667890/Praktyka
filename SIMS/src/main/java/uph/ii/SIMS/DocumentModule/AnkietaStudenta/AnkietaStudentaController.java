package uph.ii.SIMS.DocumentModule.AnkietaStudenta;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaStudentaDto;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaStudentaFillDto;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.UserModule.*;
import uph.ii.SIMS.UserModule.Dto.UserDto;

import java.util.HashMap;
import java.util.List;

/**
 * Klasa odpowiedzialna za dokument ankieta studenta
 */
@RestController
@RequestMapping(value = Document.URL + AnkietaStudenta.DOCUMENT_TYPE)
@AllArgsConstructor
public class AnkietaStudentaController {

    private GroupRepository groupRepository;
    private UserFacade userFacade;
    private AnkietaStudentaService ankietaStudentaService;
    private AnkietaStudentaFacade ankietaStudentaFacade;

    /**
     * Metoda odpowiedzialna za pobranie danych z formularza i przekazanie ich do zapisu
     * @param id
     * @param fillDto
     */
    @PostMapping(value = "/{id}")
   public void storeAnkieta(@PathVariable Long id, @RequestBody AnkietaStudentaFillDto fillDto) {

        UserDto user = userFacade.getCurrentUser();
        Group group = groupRepository.getOne(id);

        AnkietaStudentaDto ankietaStudentaDto = new AnkietaStudentaDto(
                null,
                id,
                user.getId(),
                fillDto.getStudentName(),
                fillDto.getStudentSurname(),
                fillDto.getStudentSpecialization(),
                fillDto.getInstytutionType(),
                fillDto.getCompanyNameAndLocation(),
                fillDto.getStudentInternshipStart(),
                fillDto.getStudentInternshipEnd(),
                fillDto.getAnswerTo1(),
                fillDto.getAnswerTo2(),
                fillDto.getAnswerTo3(),
                fillDto.getAnswerTo4(),
                fillDto.getAnswerTo5(),
                fillDto.getAnswerTo5atext(),
                fillDto.getAnswerTo6(),
                fillDto.getAnswerTo7(),
                fillDto.getAnswerTo7atext(),
                fillDto.getAnswerTo8(),
                fillDto.getAnswerTo91(),
                fillDto.getAnswerTo92(),
                fillDto.getAnswerTo93(),
                fillDto.getAnswerTo10(),
                fillDto.getAnswerTo11(),
                fillDto.getAnswerTo11text(),
                fillDto.getAnswerTo12(),
                fillDto.getAnswerTo12atext(),
                fillDto.getAnswerTo12btext(),
                fillDto.getAnswerTo13(),
                fillDto.getAnswerTo13text(),
                fillDto.getAnswerTo14text(),
                null
        );
        ankietaStudentaService.createNewAnkietaStudenta(ankietaStudentaDto, user.getId(), group.getId());

    }

    /**
     * Metoda odpowiedzialna za przekazanie podsumowania ankiet studentów w formie HashMapy
     * @param id - id grupy
     * @return
     */
    @GetMapping(value = "/{id}/summaryStudentSurvay")
    public HashMap<Integer, List<Object>> survaySummary(@PathVariable Long id)
    {
        return ankietaStudentaFacade.surverySummary(id);
    }

    /**
     * Metoda odpowiedzialna za odebranie id ankiety i jej zwrócenie
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public AnkietaStudenta getAnkieta(@PathVariable Long id)
    {
        return ankietaStudentaFacade.getAnkieta(id);
    }
}
