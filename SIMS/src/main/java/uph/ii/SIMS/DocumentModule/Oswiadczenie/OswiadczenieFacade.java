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
        oswiadczenie.setStudentDuties(oswiadczenieDto.getStudentDuties());
        
        oswiadczenie.setStatus(StatusEnum.NEW);
        oswiadczenieRepository.save(oswiadczenie);
    }
    
    public void createNew(OswiadczenieDto oswiadczenieDto, Long studentId, Long groupId,String groupName) {
        Oswiadczenie oswiadczenie = new Oswiadczenie(studentId);

        oswiadczenie.setComment(oswiadczenieDto.getComment());
        oswiadczenie.setGroupId(groupId);
        oswiadczenie.setGroupName(groupName);
        oswiadczenieRepository.save(oswiadczenie);
    }
    
    
    public void setComment(Long id, String newComment, Boolean userIsAdmin) {
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can set comments on documents");
        }
    
        Oswiadczenie oswiadczenie = oswiadczenieRepository.findById(id);
        oswiadczenie.setComment(newComment);
        oswiadczenieRepository.save(oswiadczenie);
    }
    
    
    /**
     * Zwraca dane oświadczenia o podanym id
     *
     * @param id id szukanego oświadczenia
     * @return DTO z danymi oświadczenia o podanym id
     */
    public OswiadczenieDto find(Long id, UserDto userDto, Boolean userIsAdmin) {
        Oswiadczenie oswiadczenie = oswiadczenieRepository.findById(id);
        boolean userOwnsDocument = userDto.getId().equals(oswiadczenie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return oswiadczenie.oswiadczenieDto();
    }
    
    public void setStatus(Long id, StatusEnum status, Boolean userIsAdmin){
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can change status of documents");
        }
    
        Oswiadczenie oswiadczenie = oswiadczenieRepository.findById(id);
        oswiadczenie.setStatus(status);
        oswiadczenieRepository.save(oswiadczenie);
    }
}
