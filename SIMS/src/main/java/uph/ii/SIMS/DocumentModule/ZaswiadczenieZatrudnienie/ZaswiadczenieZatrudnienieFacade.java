package uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.CantModifyAcceptedDocumentException;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;

import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieZatrudnienieDto;
import uph.ii.SIMS.UserModule.Dto.UserDto;

/**
 *
 * <p>
 *     Klasa udostępniająca wszystkie operacje na dokumencie zaswiadczenia o zatrudnieniu
 * </p>
 * <p>
 *     Wykorzystywana przez {@link uph.ii.SIMS.DocumentModule.DocumentFacade}.
 * </p>
 * @see ZaswiadczenieZatrudnienieConfiguration klasa odpowiedzialna za tworzenie instancji fasady
 *
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ZaswiadczenieZatrudnienieFacade {

    private ZaswiadczenieZatrudnienieRepository ZaswiadczenieZatrudnienieRepository;

    /**
     *
     * Persystuje zaswiadczenie o zatrudnieniu utworzone na podstawie przekazanego DTO. Właścicielem zaswiadczenie o zatrudnieniu staje się aktualny użytkownik.
     *
     * @param zaswiadczenieZatrudnienieDto Dane potrzebne do zapisania zaswiadczenia o zatrudnienia
     * @throws Exception
     */
    public void storeChanges(ZaswiadczenieZatrudnienieDto zaswiadczenieZatrudnienieDto, UserDto userDto, Boolean userIsAdmin) {
        ZaswiadczenieZatrudnienie zaswiadczenieZatrudnienie = ZaswiadczenieZatrudnienieRepository.findById(zaswiadczenieZatrudnienieDto.getId());
        boolean userOwnsDocument = userDto.getId().equals(zaswiadczenieZatrudnienie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        if(zaswiadczenieZatrudnienie.getStatusEnum().equals(StatusEnum.ACCEPTED)){
            throw new CantModifyAcceptedDocumentException("You can't modify accepted document");
        }
        zaswiadczenieZatrudnienie.setStudentRoad(zaswiadczenieZatrudnienieDto.getStudentRoad());
        zaswiadczenieZatrudnienie.setStudentCity(zaswiadczenieZatrudnienieDto.getStudentCity());
        zaswiadczenieZatrudnienie.setStudentZip(zaswiadczenieZatrudnienieDto.getStudentZip());
        zaswiadczenieZatrudnienie.setStudentPesel(zaswiadczenieZatrudnienieDto.getStudentPesel());
        zaswiadczenieZatrudnienie.setStudentInternshipStart(zaswiadczenieZatrudnienieDto.getStudentInternshipStart());
        zaswiadczenieZatrudnienie.setStudentInternshipEnd(zaswiadczenieZatrudnienieDto.getStudentInternshipEnd());
        zaswiadczenieZatrudnienie.setCompanyName(zaswiadczenieZatrudnienieDto.getCompanyName());
        zaswiadczenieZatrudnienie.setStudentPosition(zaswiadczenieZatrudnienieDto.getStudentPosition());
        zaswiadczenieZatrudnienie.setHoursPerWeek(zaswiadczenieZatrudnienieDto.getHoursPerWeek());
        zaswiadczenieZatrudnienie.setStudentTasks(zaswiadczenieZatrudnienieDto.getStudentTasks());


        zaswiadczenieZatrudnienie.setStatus(StatusEnum.NEW);
        ZaswiadczenieZatrudnienieRepository.save(zaswiadczenieZatrudnienie);
    }

    public void createNew(ZaswiadczenieZatrudnienieDto ZaswiadczenieZatrudnienieDto, Long studentId, Long groupId,String groupName,Boolean visible)  {
        ZaswiadczenieZatrudnienie ZaswiadczenieZatrudnienie = new ZaswiadczenieZatrudnienie(
                studentId
        );
        ZaswiadczenieZatrudnienie.setComment(ZaswiadczenieZatrudnienieDto.getComment());
        ZaswiadczenieZatrudnienie.setGroupId(groupId);
        ZaswiadczenieZatrudnienie.setGroupName(groupName);
        ZaswiadczenieZatrudnienie.setVisible(visible);
        ZaswiadczenieZatrudnienieRepository.save(ZaswiadczenieZatrudnienie);
    }

    /**
     *
     * Zwraca dane zaswiadczenia o zatrudnienia o podanym id
     *
     * @param id id szukanego zaswiadczenie o zatrudnieniu
     * @param userDto
     * @param userIsAdmin
     * @return DTO z danymi zaswiadczenia o zatrudnienia o podanym id
     */
    public ZaswiadczenieZatrudnienieDto find(Long id, UserDto userDto, Boolean userIsAdmin) {
        ZaswiadczenieZatrudnienie ZaswiadczenieZatrudnienie = ZaswiadczenieZatrudnienieRepository.findById(id);
        boolean userOwnsDocument = userDto.getId().equals(ZaswiadczenieZatrudnienie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return ZaswiadczenieZatrudnienieRepository.findById(id).ZaswiadczenieZatrudnienieDto();
    }

    public ZaswiadczenieZatrudnienieDto find(Long groupId, Long studId, Boolean userIsAdmin) {
        ZaswiadczenieZatrudnienie zaswiadczenieZatrudnienie = ZaswiadczenieZatrudnienieRepository.findByGroupIdAndOwnerId(groupId,studId);
        boolean userOwnsDocument = studId.equals(zaswiadczenieZatrudnienie.getOwnerId());
        boolean userCanAccessDocument = userOwnsDocument || userIsAdmin;
        if (!userCanAccessDocument ) {
            throw new AccessDeniedException("You can't access this document");
        }
        return zaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienieDto();
    }

    public void setComment(Long id, String newComment, Boolean userIsAdmin) {
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can set comments on documents");
        }
        ZaswiadczenieZatrudnienie ZaswiadczenieZatrudnienie = ZaswiadczenieZatrudnienieRepository.findById(id);
        ZaswiadczenieZatrudnienie.setComment(newComment);
        ZaswiadczenieZatrudnienieRepository.save(ZaswiadczenieZatrudnienie);
    }

    public void setStatus(Long id, StatusEnum status, Boolean userIsAdmin){
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can change status of documents");
        }
        ZaswiadczenieZatrudnienie ZaswiadczenieZatrudnienie = ZaswiadczenieZatrudnienieRepository.findById(id);
        ZaswiadczenieZatrudnienie.setStatus(status);
        ZaswiadczenieZatrudnienieRepository.save(ZaswiadczenieZatrudnienie);
    }

    public void setVisible(Long id, Boolean userIsAdmin,Boolean visible){
        if(!userIsAdmin){
            throw new AccessDeniedException("Only admin can change status of documents");
        }
        ZaswiadczenieZatrudnienie ZaswiadczenieZatrudnienie = ZaswiadczenieZatrudnienieRepository.findById(id);
        ZaswiadczenieZatrudnienie.setVisible(visible);
        ZaswiadczenieZatrudnienieRepository.save(ZaswiadczenieZatrudnienie);
    }

}
