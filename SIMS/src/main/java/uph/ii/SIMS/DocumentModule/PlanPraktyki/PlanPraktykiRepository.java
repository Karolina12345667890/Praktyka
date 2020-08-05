package uph.ii.SIMS.DocumentModule.PlanPraktyki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * <p>
 * Interfejs zawierający metody związane z persystencją dla encji {@link PlanPraktyki oswiadczenia}
 * </p>
 * <p>
 * Spring automatycznie generuje klasę implementującą ten interfejs
 * </p>
 */
interface PlanPraktykiRepository extends Repository<PlanPraktyki, Long> {

    /**
     *
     * Zapisuje PlanPraktyki do repozytorium
     *
     * @param PlanPraktyki PlanPraktyki do zapisania
     * @return Zapisane PlanPraktyki
     */
    PlanPraktyki save(PlanPraktyki PlanPraktyki);

    /**
     *
     * Wyszukuje PlanPraktyki o podanym Id
     *
     * @param id Id Oswiadczenia
     * @return Szukane PlanPraktyki
     */
    PlanPraktyki findById(Long id);

    /**
     * Wyszukuje wszystkie Oswiadczenia i dzieli je odpowiednio na strony
     *
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<PlanPraktyki> findAll(Pageable pageable);

    /**
     * Wyszukuje wszystkie Oswiadczenia danego użytkownika i dzieli je odpowiednio na strony
     *
     * @param ownerId  Id użytkownika, którego oświadczenia chcemy znaleźć
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<PlanPraktyki> findAllByOwnerId(Long ownerId, Pageable pageable);
}
