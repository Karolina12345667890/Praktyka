package uph.ii.SIMS.DocumentModule.Porozumienie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 *
 * <p>
 * Interfejs zawierający metody związane z persystencją dla encji {@link Porozumienie porozumienia}
 * </p>
 * <p>
 * Spring automatycznie generuje klasę implementującą ten interfejs
 * </p>
 */
interface PorozumienieRepository extends Repository<Porozumienie, Long> {
    /**
     *
     * Zapisuje Porozumienie do repozytorium
     *
     * @param porozumienie do zapisania
     * @return Zapisane oswiadczenie
     */
    Porozumienie save(Porozumienie porozumienie);
    
    /**
     *
     * Wyszukuje Porozumienie o podanym Id
     *
     * @param id Id Porozumienie
     * @return Szukane Porozumienie
     */
    Porozumienie findById(Long id);
    
    /**
     *
     * Wyszukuje wszystkie Porozumienia i dzieli je odpowiednio na strony
     *
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<Porozumienie> findAll(Pageable pageable);
    
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
    Page<Porozumienie> findAllByOwnerId(Long ownerId, Pageable pageable);
    
}
