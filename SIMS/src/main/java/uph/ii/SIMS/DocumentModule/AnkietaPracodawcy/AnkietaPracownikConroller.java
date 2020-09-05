package uph.ii.SIMS.DocumentModule.AnkietaPracodawcy;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaPracownikDto;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaPracownikFillDto;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.Group;
import uph.ii.SIMS.UserModule.GroupRepository;
import uph.ii.SIMS.UserModule.UserFacade;

import java.util.HashMap;
import java.util.List;

/**
 * Klasa odpowiedzialna za dokument ankiety pracownika
 */

@RestController
@RequestMapping(value = Document.URL + AnkietaPracownik.DOCUMENT_TYPE)
@AllArgsConstructor
public class AnkietaPracownikConroller {

    private GroupRepository groupRepository;
    private UserFacade userFacade;
    private AnkietaPracownikFacade ankietaPracownikFacade;
    private AnkietaPracownikService ankietaPracownikService;

    /**
     * Metoda odpowiedzialna za odebranie danych z formularza ankiety pracownika i przekazanie ich do zapisu
     * @param id - id grupy
     * @param fillDto
     */
    @PostMapping(value = "/{id}")
    public void storeAnkieta(@PathVariable Long id, @RequestBody AnkietaPracownikFillDto fillDto) {

        UserDto user = userFacade.getCurrentUser();
        Group group = groupRepository.getOne(id);

        AnkietaPracownikDto ankietaPracownikDto = new AnkietaPracownikDto(
                null,
                id,
                user.getId(),
                fillDto.getCompanyInfo(),
                fillDto.getNumberOfStudent(),
                fillDto.getAnswer().get(0),
                fillDto.getAnswer().get(1),
                fillDto.getAnswer().get(2),
                fillDto.getAnswer().get(3),
                fillDto.getAnswer().get(4),
                fillDto.getAnswer().get(5),
                fillDto.getAnswer().get(6),
                fillDto.getAnswer().get(7),
                fillDto.getAnswer().get(8),
                fillDto.getAnswer().get(9),
                fillDto.getAnswer().get(10),
                fillDto.getAnswer().get(11),
                fillDto.getAnswer().get(12),
                fillDto.getAnswer().get(13),
                fillDto.getAnswer().get(14),
                fillDto.getAnswer().get(15),
                fillDto.getAnswerTo15text(),
                fillDto.getAnswerTo16text(),
                null
        );
        ankietaPracownikService.createNewAnkietaPracownik(ankietaPracownikDto, user.getId(), group.getId());
    }

    /**
     * Metoda odpowiedzialna za przekazanie podsumowania ankiet pracowników w formie HashMap
     * @param id - jest to id grupy
     * @return
     */
    @GetMapping(value = "/{id}/summaryPracownikSurvay")
    public HashMap<Integer, List<Object>> survaySummary(@PathVariable Long id)
    {
        return ankietaPracownikFacade.surverySummary(id);
    }

    /**
     * Metoda odpowiedzialna za pobranie id ankiety i zwrócenie jej obiektu
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public AnkietaPracownik getAnkieta(@PathVariable Long id)
    {
        return ankietaPracownikFacade.getAnkieta(id);
    }
}
