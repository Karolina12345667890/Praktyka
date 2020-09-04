package uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 *
 * <p>
 * Interfejs zawierający metody związane z persystencją dla encji {@link ZaswiadczenieZatrudnienie porozumienia}
 * </p>
 * <p>
 * Spring automatycznie generuje klasę implementującą ten interfejs
 * </p>
 */
interface ZaswiadczenieZatrudnienieRepository extends Repository<ZaswiadczenieZatrudnienie, Long> {
    /**
     *
     * Zapisuje ZaswiadczenieZatrudnienie do repozytorium
     *
     * @param ZaswiadczenieZatrudnienie do zapisania
     * @return Zapisane oswiadczenie
     */
    ZaswiadczenieZatrudnienie save(ZaswiadczenieZatrudnienie ZaswiadczenieZatrudnienie);

    /**
     *
     * Wyszukuje ZaswiadczenieZatrudnienie o podanym Id
     *
     * @param id Id ZaswiadczenieZatrudnienie
     * @return Szukane ZaswiadczenieZatrudnienie
     */
    ZaswiadczenieZatrudnienie findById(Long id);

    /**
     *
     * Wyszukuje wszystkie Porozumienia i dzieli je odpowiednio na strony
     *
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<ZaswiadczenieZatrudnienie> findAll(Pageable pageable);

    /**
     *
     * Wyszukuje wszystkie Porozumienia danego użytkownika i dzieli je odpowiednio na strony
     *
     * @param ownerId  Id użytkownika, którego oświadczenia chcemy znaleźć
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<ZaswiadczenieZatrudnienie> findAllByOwnerId(Long ownerId, Pageable pageable);

    ZaswiadczenieZatrudnienie findByGroupIdAndOwnerId(Long groupId, Long ownerId);

}
