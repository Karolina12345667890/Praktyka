package uph.ii.SIMS.DocumentModule.AnkietaPracodawcy;

import org.springframework.data.repository.Repository;
import uph.ii.SIMS.DocumentModule.AnkietaStudenta.AnkietaStudenta;

import java.util.List;

/**
 * Interfejs odpowiedzialny za operację na encjach
 */
public interface AnkietaPracownikRepository extends Repository<AnkietaPracownik, Long> {
    /**
     * Metoda odpowiedzialna za zapisanie ankiety pracownika do bazy danych
     * @param ankietaPracownik
     * @return
     */
    AnkietaPracownik save(AnkietaPracownik ankietaPracownik);

    /**
     * Metoda odpowiedzialna za odnalezienie encji w bazie danych
     * @param id - id ankiety pracownika
     * @return
     */
    AnkietaPracownik findById(Long id);

    /**
     * Metoda odpowiedzialna za sprawdzenie czy w bazie danych istnieje ankieta pracownika
     * @param groupId - id grupy
     * @param OwnerId - id właściciela ankiety
     * @return
     */
    Boolean existsByGroupIdAndOwnerId(Long groupId, Long OwnerId);

    /**
     * Metoda odpowiedzialna za pobranie z bazy danych wszystkich ankiet pracowników o zadanym id grupy
     * @param id - id grupy
     * @return
     */
    List<AnkietaPracownik> findAllByGroupId(Long id);
}
