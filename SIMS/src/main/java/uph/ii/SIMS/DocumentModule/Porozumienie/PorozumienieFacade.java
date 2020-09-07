package uph.ii.SIMS.DocumentModule.Porozumienie;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.CantModifyAcceptedDocumentException;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;
import uph.ii.SIMS.UserModule.Dto.UserDto;

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
    
    /**
     *
     * Persystuje oświadczenie utworzone na podstawie przekazanego DTO. Właścicielem oświadczenia staje się aktualny użytkownik.
     *
     * @param porozumienieDto Dane potrzebne do zapisania porozumienia
     * @throws Exception
     */
    public void storeChanges(PorozumienieDto porozumienieDto, UserDto userDto, Boolean userIsAdmin) {
        Porozumienie porozumienie = porozumienieRepository.findById(porozumienieDto.getId());
        if (porozumienie.getStatusEnum().equals(StatusEnum.ACCEPTED) || porozumienie.getStatusEnum().equals(StatusEnum.DONE)) {
            throw new AccessDeniedException("Document already Accepted");
        }
        boolean userOwnsDocument = userDto.getId().equals(porozumienie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        if(porozumienie.getStatusEnum().equals(StatusEnum.ACCEPTED)){
            throw new CantModifyAcceptedDocumentException("You can't modify accepted document");
        }
        porozumienie.setCompanyName(porozumienieDto.getCompanyName());
        porozumienie.setCompanyLocationCity(porozumienieDto.getCompanyLocationCity());
        porozumienie.setCompanyLocationStreet(porozumienieDto.getCompanyLocationStreet());
        
        porozumienie.setCompanyRepresentantName(porozumienieDto.getCompanyRepresentantName());
        porozumienie.setCompanyRepresentantSurname(porozumienieDto.getCompanyRepresentantSurname());
        
        porozumienie.setStudentInternshipStart(porozumienieDto.getStudentInternshipStart());
        porozumienie.setStudentInternshipEnd(porozumienieDto.getStudentInternshipEnd());
        porozumienie.setStudentStudyForm(porozumienieDto.getStudentStudyForm());
        porozumienie.setStudentSpecialization(porozumienieDto.getStudentSpecialization());
        
        porozumienie.setStatus(StatusEnum.NEW);
        porozumienieRepository.save(porozumienie);
    }
    
    public void createNew(PorozumienieDto porozumienieDto, Long studentId, Long groupId,String groupName, Boolean visible)  {
        Porozumienie porozumienie = new Porozumienie(
            studentId
        );
        porozumienie.setComment(porozumienieDto.getComment());
        porozumienie.setGroupId(groupId);
        porozumienie.setGroupName(groupName);
        porozumienie.setVisible(visible);
        porozumienieRepository.save(porozumienie);
    }
    
    /**
     *
     * Zwraca dane porozumienia o podanym id
     *
     * @param id id szukanego porozumienia
     * @param userDto
     * @param userIsAdmin
     * @return DTO z danymi porozumienia o podanym id
     */
    public PorozumienieDto find(Long id, UserDto userDto, Boolean userIsAdmin) {
        Porozumienie porozumienie = porozumienieRepository.findById(id);
        boolean userOwnsDocument = userDto.getId().equals(porozumienie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return porozumienieRepository.findById(id).porozumienieDto();
    }

    public PorozumienieDto find(Long id, Boolean userIsAdmin) {
        Porozumienie porozumienie = porozumienieRepository.findById(id);
        boolean userCanAccessDocument = userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return porozumienie.porozumienieDto();
    }

    public StatusEnum getStatus(Long ownerId, Long groupId, Boolean userIsAdmin) {
        Porozumienie porozumienie = porozumienieRepository.findByOwnerIdAndGroupId(ownerId,groupId);
        boolean userCanAccessDocument = userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return porozumienie.getStatusEnum();
    }
    
    public void setComment(Long id, String newComment, Boolean userIsAdmin) {
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can set comments on documents");
        }
        Porozumienie porozumienie = porozumienieRepository.findById(id);
        porozumienie.setComment(newComment);
        porozumienieRepository.save(porozumienie);
    }
    
    public void setStatus(Long id, StatusEnum status, Boolean userIsAdmin){
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can change status of documents");
        }
        Porozumienie porozumienie = porozumienieRepository.findById(id);
        porozumienie.setStatus(status);
        porozumienieRepository.save(porozumienie);
    }

    public PorozumienieDto find2(UserDto userDto, Boolean userIsAdmin, Long groupId) {
        Porozumienie porozumienie = porozumienieRepository.findByOwnerIdAndGroupId(userDto.getId(),groupId);
        boolean userOwnsDocument = userDto.getId().equals(porozumienie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return porozumienie.porozumienieDto();
    }

    public Porozumienie find2(Long groupId, Boolean userIsAdmin, Long studId) {
        Porozumienie porozumienie = porozumienieRepository.findByOwnerIdAndGroupId(studId,groupId);
        boolean userOwnsDocument = studId.equals(porozumienie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return porozumienie;
    }
}
