package uph.ii.SIMS.DocumentModule.PlanPraktyki;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.CantModifyAcceptedDocumentException;
import uph.ii.SIMS.DocumentModule.Dto.PlanPraktykiDto;
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
 * @see PlanPraktykiConfiguration klasa odpowiedzialna za tworzenie instancji fasady
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PlanPraktykiFacade {

    private PlanPraktykiRepository PlanPraktykiRepository;

    public void storeChanges(PlanPraktykiDto planPraktykiDto, UserDto userDto, Boolean userIsAdmin) {
        PlanPraktyki planPraktyki = PlanPraktykiRepository.findById(planPraktykiDto.getId());
        boolean userOwnsDocument = userDto.getId().equals(planPraktyki.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        if(planPraktyki.getStatusEnum().equals(StatusEnum.ACCEPTED)){
            throw new CantModifyAcceptedDocumentException("You can't modify accepted document");
        }
        planPraktyki.setStudentInternshipStart(planPraktykiDto.getStudentInternshipStart());
        planPraktyki.setStudentInternshipEnd(planPraktykiDto.getStudentInternshipEnd());
        planPraktyki.setStudentTasks(planPraktykiDto.getStudentTasks());
        planPraktyki.setStudentPesel(planPraktykiDto.getStudentPesel());

        planPraktyki.setStatus(StatusEnum.NEW);
        PlanPraktykiRepository.save(planPraktyki);
    }

    public void createNew(PlanPraktykiDto planPraktykiDto, Long studentId, Long groupId, String groupName) {
        PlanPraktyki planPraktyki = new PlanPraktyki(studentId);

        planPraktyki.setComment(planPraktykiDto.getComment());
        planPraktyki.setGroupId(groupId);
        planPraktyki.setGroupName(groupName);
        PlanPraktykiRepository.save(planPraktyki);
    }


    public void setComment(Long id, String newComment, Boolean userIsAdmin) {
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can set comments on documents");
        }

        PlanPraktyki planPraktyki = PlanPraktykiRepository.findById(id);
        planPraktyki.setComment(newComment);
        PlanPraktykiRepository.save(planPraktyki);
    }


    /**
     * Zwraca dane oświadczenia o podanym id
     *
     * @param id id szukanego oświadczenia
     * @return DTO z danymi oświadczenia o podanym id
     */
    public PlanPraktykiDto find(Long id, UserDto userDto, Boolean userIsAdmin) {
        PlanPraktyki planPraktyki = PlanPraktykiRepository.findById(id);
        boolean userOwnsDocument = userDto.getId().equals(planPraktyki.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return planPraktyki.PlanPraktykiDto();
    }

    public void setStatus(Long id, StatusEnum status, Boolean userIsAdmin){
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can change status of documents");
        }

        PlanPraktyki planPraktyki = PlanPraktykiRepository.findById(id);
        planPraktyki.setStatus(status);
        PlanPraktykiRepository.save(planPraktyki);
    }
}
