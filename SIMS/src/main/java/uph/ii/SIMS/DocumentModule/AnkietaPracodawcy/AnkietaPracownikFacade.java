package uph.ii.SIMS.DocumentModule.AnkietaPracodawcy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaPracownikDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;

import java.util.HashMap;
import java.util.List;

/**
 * Klasa odpowiedzialna za działania na ankietach pracownika
 */

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AnkietaPracownikFacade {

    private AnkietaPracownikRepository ankietaPracownikRepository;
    private AnkietaPracownikService ankietaPracownikService;

    /**
     * Metoda odpowiedzialna za przekazanie danych do zapisu ankiety do bazy danych
     * @param ankietaPracownikDto
     * @param studentId
     * @param groupId
     * @param groupName
     */
    public AnkietaPracownik createNewPracownikAnkieta(AnkietaPracownikDto ankietaPracownikDto, Long studentId, Long groupId, String groupName) {
       return ankietaPracownikService.createAnkietaPracownik(ankietaPracownikDto,studentId,groupId,groupName);
    }

    /**
     * Metoda odpowiedzialna za podsumowania ankiet pracowników
     * @param id
     * @return
     */
    public HashMap<Integer, List<Object>> surverySummary(Long id)
    {
        return ankietaPracownikService.makeSummary( id);
    }
}
