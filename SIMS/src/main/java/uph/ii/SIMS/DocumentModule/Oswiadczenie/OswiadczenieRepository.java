package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * <p>
 * Interfejs zawierający metody związane z persystencją dla encji {@link Oswiadczenie oswiadczenia}
 * </p>
 * <p>
 * Spring automatycznie generuje klasę implementującą ten interfejs
 * </p>
 */
interface OswiadczenieRepository extends Repository<Oswiadczenie, Long> {
    
    /**
     * Zapisuje Oswiadczenie do repozytorium
     *
     * @param oswiadczenie Oswiadczenie do zapisania
     * @return Zapisane oswiadczenie
     */
    Oswiadczenie save(Oswiadczenie oswiadczenie);
    
    /**
     * Wyszukuje Oswiadczenie o podanym Id
     *
     * @param id Id Oswiadczenia
     * @return Szukane Oswiadczenie
     */
    Oswiadczenie findById(Long id);
    
    /**
     * Wyszukuje wszystkie Oswiadczenia i dzieli je odpowiednio na strony
     *
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<Oswiadczenie> findAll(Pageable pageable);
    
    /**
     * Wyszukuje wszystkie Oswiadczenia danego użytkownika i dzieli je odpowiednio na strony
     *
     * @param ownerId  Id użytkownika, którego oświadczenia chcemy znaleźć
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<Oswiadczenie> findAllByOwnerId(Long ownerId, Pageable pageable);
}
