package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.CantModifyAcceptedDocumentException;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;
import uph.ii.SIMS.UserModule.Dto.UserDto;

/**
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
    
    public void storeChanges(OswiadczenieDto oswiadczenieDto, UserDto userDto, Boolean userIsAdmin) {
        Oswiadczenie oswiadczenie = oswiadczenieRepository.findById(oswiadczenieDto.getId());
        boolean userOwnsDocument = userDto.getId().equals(oswiadczenie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        if(oswiadczenie.getStatusEnum().equals(StatusEnum.ACCEPTED)){
            throw new CantModifyAcceptedDocumentException("You can't modify accepted document");
        }
        oswiadczenie.setOpiekunI(oswiadczenieDto.getOpiekunI());
        oswiadczenie.setOpiekunN(oswiadczenieDto.getOpiekunN());
        oswiadczenie.setOpiekunMail(oswiadczenieDto.getOpiekunMail());
        oswiadczenie.setOpiekunTel(oswiadczenieDto.getOpiekunTel());
        
        oswiadczenie.setStatus(StatusEnum.NEW);
    }
    
    public void createNew(OswiadczenieDto oswiadczenieDto, Long studentId, Long groupId) {
        Oswiadczenie porozumienie = new Oswiadczenie(studentId);
        
        porozumienie.setComment(oswiadczenieDto.getComment());
        porozumienie.setGroupId(groupId);
        oswiadczenieRepository.save(porozumienie);
    }
    
    
    public void setComment(Long id, String newComment, Boolean userIsAdmin) {
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can set comments on documents");
        }
    
        oswiadczenieRepository.findById(id).setComment(newComment);
    }
    
    
    /**
     * Zwraca dane oświadczenia o podanym id
     *
     * @param id id szukanego oświadczenia
     * @return DTO z danymi oświadczenia o podanym id
     */
    public OswiadczenieDto find(Long id) {
        return oswiadczenieRepository.findById(id).oswiadczenieDto();
    }
    
    public void setStatus(Long id, StatusEnum status, Boolean userIsAdmin){
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can set comments on documents");
        }
    
        oswiadczenieRepository.findById(id).setStatus(status);
    }
}
