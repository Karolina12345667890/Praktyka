package uph.ii.SIMS.DocumentModule.Zaswiadczenie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 *
 * <p>
 * Interfejs zawierający metody związane z persystencją dla encji {@link Zaswiadczenie porozumienia}
 * </p>
 * <p>
 * Spring automatycznie generuje klasę implementującą ten interfejs
 * </p>
 */
interface ZaswiadczenieRepository extends Repository<Zaswiadczenie, Long> {
    /**
     *
     * Zapisuje Zaswiadczenie do repozytorium
     *
     * @param Zaswiadczenie do zapisania
     * @return Zapisane oswiadczenie
     */
    Zaswiadczenie save(Zaswiadczenie Zaswiadczenie);

    /**
     *
     * Wyszukuje Zaswiadczenie o podanym Id
     *
     * @param id Id Zaswiadczenie
     * @return Szukane Zaswiadczenie
     */
    Zaswiadczenie findById(Long id);

    /**
     *
     * Wyszukuje wszystkie Porozumienia i dzieli je odpowiednio na strony
     *
     * @param pageable Obiekt klasy implementującej interfejs {@link org.springframework.data.domain.Pageable},
     *                 Zawiera informacje, którą stronę należy zwrócić i ile elementów przypada na jedną strone.
     *                 Obiekt taki można uzyskać korzystając np. ze statycznej metody {@link org.springframework.data.domain.PageRequest#of(int, int)}
     * @return
     */
    Page<Zaswiadczenie> findAll(Pageable pageable);

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
    Page<Zaswiadczenie> findAllByOwnerId(Long ownerId, Pageable pageable);

}

