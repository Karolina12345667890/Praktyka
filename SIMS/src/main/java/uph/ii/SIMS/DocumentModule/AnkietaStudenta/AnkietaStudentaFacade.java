package uph.ii.SIMS.DocumentModule.AnkietaStudenta;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaStudentaDto;


import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Klasa udostępniająca wszystkie operacje na dokumencie ankiety studeta
 * </p>
 *
 * @see AnkietaStudentaFacade klasa odpowiedzialna za tworzenie instancji fasady
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AnkietaStudentaFacade {
    private AnkietaStudentaRepository ankietaStudentaRepository;
    private AnkietaStudentaService ankietaStudentaService;

    /**
     * Metoda odpowiedzialna za przekazanie danych do zapisu ankiety do bazy danych
     * @param ankietaStudentaDto
     * @param studentId
     * @param groupId
     * @param groupName
     * @return
     */
    public AnkietaStudenta createNew(AnkietaStudentaDto ankietaStudentaDto, Long studentId, Long groupId, String groupName) {
        return ankietaStudentaService.createAnkietaStudenta(ankietaStudentaDto,studentId,groupId,groupName);
    }

    /**
     * Metoda odpowiedzialna za podsumowania ankiet pracowników
     * @param id
     * @return
     */
    public HashMap<Integer, List<Object>> surverySummary(Long id)
    {
        return ankietaStudentaService.makeSummary( id);
    }
}
