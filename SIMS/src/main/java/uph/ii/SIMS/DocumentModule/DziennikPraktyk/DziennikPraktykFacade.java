package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.CantModifyAcceptedDocumentException;
import uph.ii.SIMS.DocumentModule.Dto.DziennikPraktykDto;
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
 * @see DziennikPraktykConfiguration klasa odpowiedzialna za tworzenie instancji fasady
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DziennikPraktykFacade {

    private DziennikPraktykRepository DziennikPraktykRepository;

    public void storeChanges(DziennikPraktykDto dziennikPraktykDto, UserDto userDto, Boolean userIsAdmin) {
        DziennikPraktyk dziennikPraktyk = DziennikPraktykRepository.findById(dziennikPraktykDto.getId());
        if (dziennikPraktyk.getStatusEnum().equals(StatusEnum.ACCEPTED) || dziennikPraktyk.getStatusEnum().equals(StatusEnum.DONE)) {
            throw new AccessDeniedException("Document already Accepted");
        }
        boolean userOwnsDocument = userDto.getId().equals(dziennikPraktyk.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument) {
            throw new AccessDeniedException("You can't access this document");
        }
        if (dziennikPraktyk.getStatusEnum().equals(StatusEnum.ACCEPTED)) {
            throw new CantModifyAcceptedDocumentException("You can't modify accepted document");
        }
        dziennikPraktyk.setCompanyName(dziennikPraktykDto.getCompanyName());
        dziennikPraktyk.setPeriodFrom(dziennikPraktykDto.getPeriodFrom());
        dziennikPraktyk.setPeriodTo(dziennikPraktykDto.getPeriodTo());
        dziennikPraktyk.setStudentAlbumNumber(dziennikPraktykDto.getStudentAlbumNumber());
        dziennikPraktyk.setDiary(dziennikPraktykDto.getDiary());

        dziennikPraktyk.setStatus(StatusEnum.NEW);

        DziennikPraktykRepository.save(dziennikPraktyk);
    }

    public void createNew(DziennikPraktykDto DziennikPraktykDto, Long studentId, Long groupId, String groupName, Boolean visible) {
        DziennikPraktyk dziennikPraktyk = new DziennikPraktyk(studentId);

        dziennikPraktyk.setComment(DziennikPraktykDto.getComment());
        dziennikPraktyk.setGroupId(groupId);
        dziennikPraktyk.setGroupName(groupName);
        dziennikPraktyk.setVisible(visible);
        DziennikPraktykRepository.save(dziennikPraktyk);
    }


    public void setComment(Long id, String newComment, Boolean userIsAdmin) {
        if (!userIsAdmin) {
            throw new AccessDeniedException("Only admin can set comments on documents");
        }

        DziennikPraktyk DziennikPraktyk = DziennikPraktykRepository.findById(id);
        DziennikPraktyk.setComment(newComment);
        DziennikPraktykRepository.save(DziennikPraktyk);
    }


    /**
     * Zwraca dane oświadczenia o podanym id
     *
     * @param id id szukanego oświadczenia
     * @return DTO z danymi oświadczenia o podanym id
     */
    public DziennikPraktykDto find(Long id, UserDto userDto, Boolean userIsAdmin) {
        DziennikPraktyk DziennikPraktyk = DziennikPraktykRepository.findById(id);
        boolean userOwnsDocument = userDto.getId().equals(DziennikPraktyk.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument) {
            throw new AccessDeniedException("You can't access this document");
        }
        return DziennikPraktyk.DziennikPraktykDto();
    }

    public void setStatus(Long id, StatusEnum status, Boolean userIsAdmin) {
        if (!userIsAdmin) {
            throw new AccessDeniedException("Only admin can change status of documents");
        }

        DziennikPraktyk DziennikPraktyk = DziennikPraktykRepository.findById(id);
        DziennikPraktyk.setStatus(status);
        DziennikPraktykRepository.save(DziennikPraktyk);
    }
}
