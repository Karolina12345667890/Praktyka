package uph.ii.SIMS.DocumentModule.AnkietaStudenta;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Interfejs odpowiedzialny za operacje na encjach
 */
public interface AnkietaStudentaRepository extends Repository<AnkietaStudenta, Long> {
    /**
     * Metoda odpowiedzialna za zapisanie ankiety studenta do bazy danych
     * @param ankietaStudenta
     * @return
     */
    AnkietaStudenta save(AnkietaStudenta ankietaStudenta);

    /**
     * Metoda odpowiedzialna za odszukanie ankiety po id
     * @param id
     * @return
     */
    AnkietaStudenta findById(Long id);

    /**
     * Metoda odpowiedzialna za sprawdzenie czy aniketa istnieje dla danych id
     * @param groupId - id grupy
     * @param OwnerId - id właściciela grupy
     * @return
     */
    Boolean existsByGroupIdAndOwnerId(Long groupId, Long OwnerId);

    /**
     * Metoda odpowiedzialna za zwrócenie w formie listy wszystkich ankiet dla danej grupu
     * @param id
     * @return
     */
    List<AnkietaStudenta> findAllByGroupId(Long id);
}
