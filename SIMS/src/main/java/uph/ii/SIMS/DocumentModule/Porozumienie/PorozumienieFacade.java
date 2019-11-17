package uph.ii.SIMS.DocumentModule.Porozumienie;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.UserModule.UserFacade;

/**
 *
 * <p>
 *     Klasa udostępniająca wszystkie operacje na dokumencie oświadczenia
 * </p>
 * <p>
 *     Wykorzystywana przez {@link uph.ii.SIMS.DocumentModule.DocumentFacade}.
 * </p>
 * @see PorozumienieConfiguration klasa odpowiedzialna za tworzenie instancji fasady
 *
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PorozumienieFacade {
    
    private PorozumienieRepository porozumienieRepository;
    private UserFacade userFacade;
    
    /**
     *
     * Persystuje oświadczenie utworzone na podstawie przekazanego DTO. Właścicielem oświadczenia staje się aktualny użytkownik.
     *
     * @param porozumienieDto Dane potrzebne do zapisania porozumienia
     * @throws Exception
     */
    //TODO Zająć się obsługą wyjątku (dodać controller advice, doprecyzować klasę/klasy wyjątków1)
    public void save(PorozumienieDto porozumienieDto) throws Exception {
        Long ownerId = userFacade.getCurrentUser().getId();
    
        Porozumienie porozumienie = new Porozumienie(
            ownerId
        );
        porozumienie.setComment(porozumienieDto.getComment());
        porozumienieRepository.save(porozumienie);
    }
    
    /**
     *
     * Zwraca dane porozumienia o podanym id
     *
     * @param id id szukanego porozumienia
     * @return DTO z danymi porozumienia o podanym id
     */
    public PorozumienieDto find(Long id) {
        return porozumienieRepository.findById(id).dto();
    }
}
