package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.UserModule.UserFacade;

/**
 *
 * <p>
 * Klasa udostępniająca wszystkie operacje na dokumencie oświadczenia
 * </p>
 * <p>
 * Wykorzystywana przez {@link uph.ii.SIMS.DocumentModule.DocumentFacade}.
 * </p>
 *
 * @see OswiadczenieConfiguration klasa odpowiedzialna za tworzenie instancji fasady
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class OswiadczenieFacade {
    
    private OswiadczenieRepository oswiadczenieRepository;
    private UserFacade userFacade;
    
    /**
     *
     * Persystuje oświadczenie utworzone na podstawie przekazanego DTO. Właścicielem oświadczenia staje się aktualny użytkownik.
     *
     * @param dto Dane potrzebne do zapisania oświaczenia
     * @throws Exception
     */
    //TODO Zająć się obsługą wyjątku (dodać controller advice, doprecyzować klasę/klasy wyjątków1)
    public void save(OswiadczenieDto dto) throws Exception {
        Long ownerId = userFacade.getCurrentUser().getId();
        save(dto, ownerId, 1L);
    }
    
    public void save(OswiadczenieDto dto, Long studentId, Long groupId) {
        Oswiadczenie oswiadczenie = new Oswiadczenie(
            studentId,
            dto.getOpiekunI(),
            dto.getOpiekunN(),
            dto.getOpiekunMail(),
            dto.getOpiekunTel());
        oswiadczenie.setComment(dto.getComment());
        oswiadczenie.setGroupId(groupId);
        
        oswiadczenieRepository.save(oswiadczenie);
    }
    
    /**
     *
     * Zwraca dane oświadczenia o podanym id
     *
     * @param id id szukanego oświadczenia
     * @return DTO z danymi oświadczenia o podanym id
     */
    public OswiadczenieDto find(Long id) {
        return oswiadczenieRepository.findById(id).oswiadczenieDto();
    }
    
    /**
     *
     * Zwraca listę (z paginacją) dokumentów aktualnie zalogowanego użytkownika
     *
     * @return dokumentów aktualnie zalogowanego użytkownika
     * @throws Exception
     */
    //TODO Zająć się obsługą wyjątku (dodać controller advice, doprecyzować klasę/klasy wyjątków1)
    public Page<OswiadczenieDto> findMyDocuments() throws Exception {
        Long ownerId = userFacade.getCurrentUser().getId();
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        return oswiadczenieRepository.findAllByOwnerId(ownerId, pageRequest)
            .map(Oswiadczenie::oswiadczenieDto);
    }
    
    public Page<OswiadczenieDto> findUsersDocuments(Long ownerId) throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        return oswiadczenieRepository.findAllByOwnerId(ownerId, pageRequest)
            .map(Oswiadczenie::oswiadczenieDto);
    }
}
