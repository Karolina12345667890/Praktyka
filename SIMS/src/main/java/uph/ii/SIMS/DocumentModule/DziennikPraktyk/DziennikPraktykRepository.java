package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * <p>
 * Interfejs zawierający metody związane z persystencją dla encji {@link DziennikPraktyk DziennikPraktyk}
 * </p>
 * <p>
 * Spring automatycznie generuje klasę implementującą ten interfejs
 * </p>
 */
interface DziennikPraktykRepository extends Repository<DziennikPraktyk, Long> {

    /**
     *
     * Zapisuje DziennikPraktyk do repozytorium
     *
     * @param DziennikPraktyk DziennikPraktyk do zapisania
     * @return Zapisane DziennikPraktyk
     */
    DziennikPraktyk save(DziennikPraktyk DziennikPraktyk);

    /**
     *
     * Wyszukuje DziennikPraktyk o podanym Id
     *
     * @param id Id DziennikPraktyk
     * @return Szukane DziennikPraktyk
     */
    DziennikPraktyk findById(Long id);

    /**
     * Wyszukuje wszystkie DziennikPraktyk i dzieli je odpowiednio na strony
     *
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<DziennikPraktyk> findAll(Pageable pageable);

    /**
     * Wyszukuje wszystkie DziennikPraktyk danego użytkownika i dzieli je odpowiednio na strony
     *
     * @param ownerId  Id użytkownika, którego oświadczenia chcemy znaleźć
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<DziennikPraktyk> findAllByOwnerId(Long ownerId, Pageable pageable);
}
