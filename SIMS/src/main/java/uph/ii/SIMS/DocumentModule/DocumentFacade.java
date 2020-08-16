package uph.ii.SIMS.DocumentModule;


import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.*;
import uph.ii.SIMS.DocumentModule.DziennikPraktyk.DziennikPraktykFacade;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieFacade;
import uph.ii.SIMS.DocumentModule.PlanPraktyki.PlanPraktykiFacade;
import uph.ii.SIMS.DocumentModule.Porozumienie.PorozumienieFacade;
import uph.ii.SIMS.DocumentModule.Zaswiadczenie.ZaswiadczenieFacade;
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienie;
import uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie.ZaswiadczenieZatrudnienieFacade;
import uph.ii.SIMS.PdfCreationService.Dto.*;
import uph.ii.SIMS.PdfCreationService.PdfBuilder;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.UserFacade;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa zawiera wszystkie metody związane z dokumentami. Odpowiada za komunikację z innymi modułami, {@link DocumentController} udostępnia jej metody przez HTTP
 *
 * @see DocumentController
 */
@AllArgsConstructor
public class DocumentFacade {

    public OswiadczenieFacade oswiadczenieFacade;
    private PdfBuilder pdfBuilder;
    private PorozumienieFacade porozumienieFacade;
    private DocumentRepository documentRepository;
    private UserFacade userFacade;
    private ZaswiadczenieFacade zaswiadczenieFacade;
    private DziennikPraktykFacade dziennikPraktykFacade;
    private PlanPraktykiFacade planPraktykiFacade;
    private ZaswiadczenieZatrudnienieFacade zaswiadczenieZatrudnienieFacade;

    byte[] createPdf(String templateName, OswiadczeniePdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    byte[] createPdf(String templateName, PorozumieniePdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    byte[] createPdf(String templateName, ZaswiadczeniePdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    byte[] createPdf(String templateName, DziennikPraktykPdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    byte[] createPdf(String templateName, PlanPraktykiPdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    byte[] createPdf(String templateName, ZaswiadczenieZatrudnieniePdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    /**
     * Tworzy pdf oświadczenia, wypełnia wszystkie pola wartościami pobranymi z BD
     *
     * @param id Id oświadczenia
     * @return Pdf oświadczenia (jako tablica bajtów) z wartościami wszystkich pól pobranymi z BD
     * @throws Exception
     */
    public byte[] printOswiadczenieToPdf(Long id) throws Exception {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        var oswiadczenieDto = oswiadczenieFacade.find(id, currentUser, isAdmin);
        var userDto = userFacade.getUserById(oswiadczenieDto.getOwnerId());
        var pdfDto = OswiadczeniePdfDto.builder()
                .studentName(userDto.getName())
                .studentSurname(userDto.getSurname())
                .carerName(oswiadczenieDto.getOpiekunI())
                .carerSurname(oswiadczenieDto.getOpiekunN())
                .carerEmail(oswiadczenieDto.getOpiekunMail())
                .carerPhone(oswiadczenieDto.getOpiekunTel())
                .studentDuties(oswiadczenieDto.getStudentDuties())
                .build();

        return pdfBuilder.getPdfFromObject("Oswiadczenie", pdfDto);
    }

    /**
     * Tworzy pdf porozumienia, wypełnia wszystkie pola wartościami pobranymi z BD
     *
     * @param id Id porozumienia
     * @return Pdf porozumienia (jako tablica bajtów) z wartościami wszystkich pól pobranymi z BD
     * @throws Exception
     */
    public byte[] printPorozumienieToPdf(Long id) throws Exception {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        var porozumienieDto = porozumienieFacade.find(id, currentUser, isAdmin);
        var userDto = userFacade.getUserById(porozumienieDto.getOwnerId());
        var group = userFacade.getGroupById(porozumienieDto.getGroupId());


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateStartFormatted = dateFormat.format(porozumienieDto.getStudentInternshipStart());
        String dateEndFormatted = dateFormat.format(porozumienieDto.getStudentInternshipEnd());

        var pdfDto = PorozumieniePdfDto.builder()
                .studentName(userDto.getName())
                .studentSurname(userDto.getSurname())

                .studentInternshipDuration(group.getDurationInWeeks() + " tyg.")
                .studentInternshipStart(dateStartFormatted)
                .studentInternshipEnd(dateEndFormatted)
                .studentStudyForm(porozumienieDto.getStudentStudyForm())
                .studentSpecialization(porozumienieDto.getStudentSpecialization())
                .groupAdminName(group.getGroupAdminName())
                .groupAdminSurname(group.getGroupAdminSurname())
                .companyName(porozumienieDto.getCompanyName())
                .companyLocationCity(porozumienieDto.getCompanyLocationCity())
                .companyLocationStreet(porozumienieDto.getCompanyLocationStreet())
                .department(porozumienieDto.getDepartment())

                .companyRepresentantName(porozumienieDto.getCompanyRepresentantName())
                .companyRepresentantSurname(porozumienieDto.getCompanyRepresentantSurname())
                .build();

        return pdfBuilder.getPdfFromObject("Porozumienie", pdfDto);
    }

    /**
     * Tworzy pdf porozumienia, wypełnia wszystkie pola wartościami pobranymi z BD
     *
     * @param id Id zaswiadczenia
     * @return Pdf zaswiadczenia (jako tablica bajtów) z wartościami wszystkich pól pobranymi z BD
     * @throws Exception
     */

    public byte[] printZaswiadczenieToPdf(Long id) throws Exception {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        var zaswiadczenieDto = zaswiadczenieFacade.find(id, currentUser, isAdmin);
        var userDto = userFacade.getUserById(zaswiadczenieDto.getOwnerId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateStartFormatted = dateFormat.format(zaswiadczenieDto.getStudentInternshipStart());
        String dateEndFormatted = dateFormat.format(zaswiadczenieDto.getStudentInternshipEnd());


        var pdfDto = ZaswiadczeniePdfDto.builder()
                .studentName(userDto.getName())
                .studentSurname(userDto.getSurname())
                .studentWorks(zaswiadczenieDto.getStudentWorks())
                .studentInternshipStart(dateStartFormatted)
                .studentInternshipEnd(dateEndFormatted)

                .studentRating1(zaswiadczenieDto.getStudentRating1())
                .studentRating2(zaswiadczenieDto.getStudentRating2())
                .studentRating3(zaswiadczenieDto.getStudentRating3())

                .studentRating(zaswiadczenieDto.getStudentRating())
                .studentInterests(zaswiadczenieDto.getStudentInterests())
                .build();

        return pdfBuilder.getPdfFromObject("Zaswiadczenie", pdfDto);
    }

    /**
     * Tworzy pdf porozumienia, wypełnia wszystkie pola wartościami pobranymi z BD
     *
     * @param id Id dziennika praktyk
     * @return Pdf dziennika praktyk (jako tablica bajtów) z wartościami wszystkich pól pobranymi z BD
     * @throws Exception
     */

    public byte[] printDziennikPraktykToPdf(Long id) throws Exception {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        var oswiadczenieDto = dziennikPraktykFacade.find(id, currentUser, isAdmin);
        var userDto = userFacade.getUserById(oswiadczenieDto.getOwnerId());
        var pdfDto = DziennikPraktykPdfDto.builder()
                .studentName(userDto.getName())
                .studentSurname(userDto.getSurname())
                .companyName(oswiadczenieDto.getCompanyName())
                .periodFrom(oswiadczenieDto.getPeriodFrom())
                .periodTo(oswiadczenieDto.getPeriodTo())
                .studentAlbumNumber(oswiadczenieDto.getStudentAlbumNumber())
                .diary(oswiadczenieDto.getDiary())
                .build();

        return pdfBuilder.getPdfFromObject("DziennikPraktyk", pdfDto);
    }


    /**
     * Tworzy pdf porozumienia, wypełnia wszystkie pola wartościami pobranymi z BD
     *
     * @param id Id planu praktyki
     * @return Pdf planu praktyki (jako tablica bajtów) z wartościami wszystkich pól pobranymi z BD
     * @throws Exception
     */

    public byte[] printPlanPraktykiToPdf(Long id) throws Exception {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        var planPraktykiDto = planPraktykiFacade.find(id, currentUser, isAdmin);
        var userDto = userFacade.getUserById(planPraktykiDto.getOwnerId());
        var pdfDto = PlanPraktykiPdfDto.builder()
                .studentName(userDto.getName())
                .studentSurname(userDto.getSurname())
                .studentInternshipStart(planPraktykiDto.getStudentInternshipStart())
                .studentInternshipEnd(planPraktykiDto.getStudentInternshipEnd())
                .studentTasks(planPraktykiDto.getStudentTasks())
                .studentPesel(planPraktykiDto.getStudentPesel())
                .build();

        return pdfBuilder.getPdfFromObject("PlanPraktyki", pdfDto);
    }

    /**
     * Tworzy pdf zaswiadczenia o zatrudnieniu, wypełnia wszystkie pola wartościami pobranymi z BD
     *
     * @param id Id zaswiadczenia o zatrudnieniu
     * @return Pdf zaswiadczenia o zatrudnieniu (jako tablica bajtów) z wartościami wszystkich pól pobranymi z BD
     * @throws Exception
     */

    public byte[] printZaswiadczenieZatrudnienieToPdf(Long id) throws Exception {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        var zaswiadczenieZatrudnienieDto = zaswiadczenieZatrudnienieFacade.find(id, currentUser, isAdmin);
        var userDto = userFacade.getUserById(zaswiadczenieZatrudnienieDto.getOwnerId());
        var pdfDto = ZaswiadczenieZatrudnieniePdfDto.builder()
                .studentName(userDto.getName())
                .studentSurname(userDto.getSurname())
                .studentInternshipStart(zaswiadczenieZatrudnienieDto.getStudentInternshipStart())
                .studentInternshipEnd(zaswiadczenieZatrudnienieDto.getStudentInternshipEnd())
                .studentTasks(zaswiadczenieZatrudnienieDto.getStudentTasks())
                .studentPesel(zaswiadczenieZatrudnienieDto.getStudentPesel())
                .companyName(zaswiadczenieZatrudnienieDto.getCompanyName())
                .hoursPerWeek(zaswiadczenieZatrudnienieDto.getHoursPerWeek())
                .studentCity(zaswiadczenieZatrudnienieDto.getStudentCity())
                .studentPosition(zaswiadczenieZatrudnienieDto.getStudentPosition())
                .studentRoad(zaswiadczenieZatrudnienieDto.getStudentRoad())
                .studentZip(zaswiadczenieZatrudnienieDto.getStudentZip())
                .build();

        return pdfBuilder.getPdfFromObject("ZaswiadczenieZatrudnienie", pdfDto);
    }


    public OswiadczenieDto fetchOswiadczenie(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean isGroupAdmin = userFacade.currentUserIsGroupAdmin();
        return oswiadczenieFacade.find(id, currentUser, isGroupAdmin);
    }


    /**
     * Persystuje przekazane oświadczenie, jesli w bazie danych nie ma oświadczenia z id takim, jak w przekazanym dto zostaje utworzone nowe oświadczenie,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param oswiadczenieDto oswiadczenie do zapisania
     * @throws Exception
     */
    public void storeOswiadczenie(OswiadczenieDto oswiadczenieDto) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        oswiadczenieFacade.storeChanges(oswiadczenieDto, currentUser, userIsGroupAdmin);
    }

    public void storeOswiadczenie(OswiadczenieDto oswiadczenieDto, Long studentId, Long groupId, String groupName) {
        oswiadczenieFacade.createNew(oswiadczenieDto, studentId, groupId, groupName);
    }

    /**
     * Zwraca Dto porozumienia o podanym id, wywołuje {@link PorozumienieFacade#find(Long, UserDto, Boolean)}
     *
     * @param id Id porozumienia
     * @return Dto porozumienia
     */
    public PorozumienieDto fetchPorozumienie(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        return porozumienieFacade.find(id, currentUser, isAdmin);
    }

    /**
     * Persystuje przekazane porozumienie, jesli w bazie danych nie ma porozumienie z id takim, jak w przekazanym dto zostaje utworzone nowe porozumienie,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param porozumienieDto oswiadczenie do zapisania
     * @throws Exception
     */
    public void storePorozumienie(PorozumienieDto porozumienieDto) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        porozumienieFacade.storeChanges(porozumienieDto, currentUser, userIsGroupAdmin);
    }

    public void storePorozumienie(PorozumienieDto porozumienieDto, Long studentId, Long groupId, String groupName) {
        porozumienieFacade.createNew(porozumienieDto, studentId, groupId, groupName);
    }

    public ZaswiadczenieDto fetchZaswiadczenie(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();


        ZaswiadczenieDto zaswiadczenieDto = zaswiadczenieFacade.find(id, currentUser, isAdmin);
        if(zaswiadczenieDto.getStatus().equals(StatusEnum.EMPTY.toString())) {
            PorozumienieDto porozumienieDto = porozumienieFacade.find2(currentUser, isAdmin, zaswiadczenieDto.getGroupId());
            if (porozumienieDto.getStatus() == StatusEnum.ACCEPTED.toString()) {
                zaswiadczenieDto.setStudentInternshipStart(porozumienieDto.getStudentInternshipStart());
                zaswiadczenieDto.setStudentInternshipEnd(porozumienieDto.getStudentInternshipEnd());
            }
        }
        return zaswiadczenieDto;
    }

    /**
     * Persystuje przekazane porozumienie, jesli w bazie danych nie ma porozumienie z id takim, jak w przekazanym dto zostaje utworzone nowe porozumienie,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param zaswiadczenieDto oswiadczenie do zapisania
     * @throws Exception
     */

    public void storeZaswiadczenie(ZaswiadczenieDto zaswiadczenieDto) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        zaswiadczenieFacade.storeChanges(zaswiadczenieDto, currentUser, userIsGroupAdmin);
    }
    public void storeZaswiadczenie(ZaswiadczenieDto zaswiadczenieDto, Long studentId, Long groupId, String groupName) {
        zaswiadczenieFacade.createNew(zaswiadczenieDto, studentId, groupId, groupName);
    }



    public DziennikPraktykDto fetchDziennikPraktyk(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();

        DziennikPraktykDto dziennikPraktykDto = dziennikPraktykFacade.find(id, currentUser, isAdmin);
        dziennikPraktykDto.setStudentAlbumNumber(currentUser.getAlbum());
        if(dziennikPraktykDto.getStatus().equals(StatusEnum.EMPTY.toString())) {
            PorozumienieDto porozumienieDto = porozumienieFacade.find2(currentUser, isAdmin, dziennikPraktykDto.getGroupId());
            if (porozumienieDto.getStatus().equals(StatusEnum.ACCEPTED.toString())) {
                dziennikPraktykDto.setCompanyName(porozumienieDto.getCompanyName());
                dziennikPraktykDto.setPeriodFrom(porozumienieDto.getStudentInternshipStart());
                dziennikPraktykDto.setPeriodTo(porozumienieDto.getStudentInternshipEnd());
            }
        }
        return dziennikPraktykDto;
    }

    /**
     * Persystuje przekazane dziennika praktyki, jesli w bazie danych nie ma porozumienie z id takim, jak w przekazanym dto zostaje utworzony nowy dziennik praktyki,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param dziennikPraktykDto oswiadczenie do zapisania
     * @throws Exception
     */

    public void storeDziennikPraktyk(DziennikPraktykDto dziennikPraktykDto) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        dziennikPraktykFacade.storeChanges(dziennikPraktykDto, currentUser, userIsGroupAdmin);
    }
    public void storeDziennikPraktyk(DziennikPraktykDto dziennikPraktykDto, Long studentId, Long groupId, String groupName) {
        dziennikPraktykFacade.createNew(dziennikPraktykDto, studentId, groupId, groupName);
    }



    public PlanPraktykiDto fetchPlanPraktyki(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();

        PlanPraktykiDto planPraktykiDto = planPraktykiFacade.find(id, currentUser, isAdmin);

        if(planPraktykiDto.getStatus().equals(StatusEnum.EMPTY.toString())) {
            PorozumienieDto porozumienieDto = porozumienieFacade.find2(currentUser, isAdmin, planPraktykiDto.getGroupId());
            if (porozumienieDto.getStatus().equals(StatusEnum.ACCEPTED.toString())) {
                planPraktykiDto.setStudentInternshipStart(porozumienieDto.getStudentInternshipStart());
                planPraktykiDto.setStudentInternshipEnd(porozumienieDto.getStudentInternshipEnd());
            }
        }
        return planPraktykiDto;
    }

    /**
     * Persystuje przekazany plan praktyki, jesli w bazie danych nie ma porozumienie z id takim, jak w przekazanym dto zostaje utworzony nowy plan praktyki,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param planPraktykiDto Plan Praktyki do zapisania
     * @throws Exception
     */

    public void storePlanPraktyki(PlanPraktykiDto planPraktykiDto) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        planPraktykiFacade.storeChanges(planPraktykiDto, currentUser, userIsGroupAdmin);
    }
    public void storePlanPraktyki(PlanPraktykiDto planPraktykiDto, Long studentId, Long groupId, String groupName) {
        planPraktykiFacade.createNew(planPraktykiDto, studentId, groupId, groupName);
    }

    public ZaswiadczenieZatrudnienieDto fetchZaswiadczenieZatrudnienie(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
         Boolean isAdmin = userFacade.currentUserIsGroupAdmin();
        return zaswiadczenieZatrudnienieFacade.find(id, currentUser, isAdmin);
    }


    /**
     * Persystuje przekazany plan praktyki, jesli w bazie danych nie ma porozumienie z id takim, jak w przekazanym dto zostaje utworzony nowy plan praktyki,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param zaswiadczenieZatrudnienieDto Plan Praktyki do zapisania
     * @throws Exception
     */

    public void storeZaswiadczenieZatrudnienie(ZaswiadczenieZatrudnienieDto zaswiadczenieZatrudnienieDto) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        zaswiadczenieZatrudnienieFacade.storeChanges(zaswiadczenieZatrudnienieDto, currentUser, userIsGroupAdmin);
    }
    public void storeZaswiadczenieZatrudnienie(ZaswiadczenieZatrudnienieDto zaswiadczenieZatrudnienieDto, Long studentId, Long groupId, String groupName, Boolean visible) {
        zaswiadczenieZatrudnienieFacade.createNew(zaswiadczenieZatrudnienieDto, studentId, groupId, groupName, visible);
    }


    public void setOswiadczenieStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        oswiadczenieFacade.setStatus(id, statusEnum, userIsGroupAdmin);
    }

    public void setOswiadczenieComment(Long id, String newComment) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        oswiadczenieFacade.setComment(id, newComment, userIsGroupAdmin);
    }

    public void setPorozumienieStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        porozumienieFacade.setStatus(id, statusEnum, userIsGroupAdmin);
    }

    public void setPorozumienieComment(Long id, String newComment) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        porozumienieFacade.setComment(id, newComment, userIsGroupAdmin);
    }


    public void setZaswiadczenieStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        zaswiadczenieFacade.setStatus(id, statusEnum, userIsGroupAdmin);
    }

    public void setZaswiadczenieComment(Long id, String newComment) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        zaswiadczenieFacade.setComment(id, newComment, userIsGroupAdmin);
    }

    public void setDziennikPraktykStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        dziennikPraktykFacade.setStatus(id, statusEnum, userIsGroupAdmin);
    }

    public void setDziennikPraktykComment(Long id, String newComment) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        dziennikPraktykFacade.setComment(id, newComment, userIsGroupAdmin);
    }


    public void setPlanPraktykiStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        planPraktykiFacade.setStatus(id, statusEnum, userIsGroupAdmin);
    }

    public void setPlanPraktykiComment(Long id, String newComment) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        planPraktykiFacade.setComment(id, newComment, userIsGroupAdmin);
    }

    public void setZaswiadczenieZatrudnienieStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        zaswiadczenieZatrudnienieFacade.setStatus(id, statusEnum, userIsGroupAdmin);
    }

    public void setZaswiadczenieZatrudnienieComment(Long id, String newComment) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        zaswiadczenieZatrudnienieFacade.setComment(id, newComment, userIsGroupAdmin);
    }

    public void setZaswiadczenieZatrudnienieVisible(Long id, Boolean visible) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        zaswiadczenieZatrudnienieFacade.setVisible(id, userIsGroupAdmin, visible);
    }

    public List<DocumentDto> listMyDocuments() {
        UserDto currentUser = userFacade.getCurrentUser();
        List<DocumentDto> documentDtos = documentRepository.getAllByOwnerId(currentUser.getId()).stream().filter(Document::getVisible)
                .map(Document::dto)
                .collect(Collectors.toList());

        return documentDtos;
    }

    public List<DocumentDto> listStudentsDocuments(Long id) {
        return documentRepository.getAllByOwnerId(id).stream()
                .map(Document::dto)
                .collect(Collectors.toList());
    }

    public void chengeUsersDocuments(Long groupId,Long studentId) {
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        if(userIsGroupAdmin) {
            List<Document> documents = documentRepository.getAllByGroupIdAndAndOwnerId(groupId,studentId);

            documents.forEach(documentDto -> {
                if (documentDto.getVisible())
                    documentDto.setVisible(false);
                else
                    documentDto.setVisible(true);
                documentRepository.save(documentDto);
            });

        }
    }


    public List<DocumentDto> listGroupDocuments(Long id) {
        return documentRepository.getAllByGroupId(id).stream()
                .map(Document::dto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteStudentsDocumentsInGroup(Long groupId,Long studentId){
        Boolean userIsGroupAdmin = userFacade.currentUserIsGroupAdmin();
        if(userIsGroupAdmin)
        documentRepository.removeAllByGroupIdAndAndOwnerId(groupId,studentId);
    }

}
