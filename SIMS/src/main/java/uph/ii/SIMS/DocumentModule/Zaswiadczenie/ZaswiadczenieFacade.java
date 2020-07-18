package uph.ii.SIMS.DocumentModule.Zaswiadczenie;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.CantModifyAcceptedDocumentException;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;
import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieDto;
import uph.ii.SIMS.UserModule.Dto.UserDto;

/**
 *
 * <p>
 *     Klasa udostępniająca wszystkie operacje na dokumencie oświadczenia
 * </p>
 * <p>
 *     Wykorzystywana przez {@link uph.ii.SIMS.DocumentModule.DocumentFacade}.
 * </p>
 * @see ZaswiadczenieConfiguration klasa odpowiedzialna za tworzenie instancji fasady
 *
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ZaswiadczenieFacade {

    private ZaswiadczenieRepository ZaswiadczenieRepository;

    /**
     *
     * Persystuje oświadczenie utworzone na podstawie przekazanego DTO. Właścicielem oświadczenia staje się aktualny użytkownik.
     *
     * @param ZaswiadczenieDto Dane potrzebne do zapisania porozumienia
     * @throws Exception
     */
    public void storeChanges(ZaswiadczenieDto ZaswiadczenieDto, UserDto userDto, Boolean userIsAdmin) {
        Zaswiadczenie Zaswiadczenie = ZaswiadczenieRepository.findById(ZaswiadczenieDto.getId());
        boolean userOwnsDocument = userDto.getId().equals(Zaswiadczenie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        if(Zaswiadczenie.getStatusEnum().equals(StatusEnum.ACCEPTED)){
            throw new CantModifyAcceptedDocumentException("You can't modify accepted document");
        }
        Zaswiadczenie.setStudentWorks(ZaswiadczenieDto.getStudentWorks());
        Zaswiadczenie.setStudentRating1(ZaswiadczenieDto.getStudentRating1());
        Zaswiadczenie.setStudentRating2(ZaswiadczenieDto.getStudentRating2());
        Zaswiadczenie.setStudentRating3(ZaswiadczenieDto.getStudentRating3());
        Zaswiadczenie.setStudentRating(ZaswiadczenieDto.getStudentRating());

        Zaswiadczenie.setStudentInterests(ZaswiadczenieDto.getStudentInterests());

        Zaswiadczenie.setStudentInternshipStart(ZaswiadczenieDto.getStudentInternshipStart());
        Zaswiadczenie.setStudentInternshipEnd(ZaswiadczenieDto.getStudentInternshipEnd());

        Zaswiadczenie.setStatus(StatusEnum.NEW);
        ZaswiadczenieRepository.save(Zaswiadczenie);
    }

    public void createNew(ZaswiadczenieDto ZaswiadczenieDto, Long studentId, Long groupId,String groupName)  {
        Zaswiadczenie Zaswiadczenie = new Zaswiadczenie(
                studentId
        );
        Zaswiadczenie.setComment(ZaswiadczenieDto.getComment());
        Zaswiadczenie.setGroupId(groupId);
        Zaswiadczenie.setGroupName(groupName);
        ZaswiadczenieRepository.save(Zaswiadczenie);
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
    public ZaswiadczenieDto find(Long id, UserDto userDto, Boolean userIsAdmin) {
        Zaswiadczenie Zaswiadczenie = ZaswiadczenieRepository.findById(id);
        boolean userOwnsDocument = userDto.getId().equals(Zaswiadczenie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return ZaswiadczenieRepository.findById(id).ZaswiadczenieDto();
    }

    public void setComment(Long id, String newComment, Boolean userIsAdmin) {
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can set comments on documents");
        }
        Zaswiadczenie Zaswiadczenie = ZaswiadczenieRepository.findById(id);
        Zaswiadczenie.setComment(newComment);
        ZaswiadczenieRepository.save(Zaswiadczenie);
    }

    public void setStatus(Long id, StatusEnum status, Boolean userIsAdmin){
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can change status of documents");
        }
        Zaswiadczenie Zaswiadczenie = ZaswiadczenieRepository.findById(id);
        Zaswiadczenie.setStatus(status);
        ZaswiadczenieRepository.save(Zaswiadczenie);
    }
}