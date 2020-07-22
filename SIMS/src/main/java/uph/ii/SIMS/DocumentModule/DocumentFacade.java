package uph.ii.SIMS.DocumentModule;


import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.*;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieFacade;
import uph.ii.SIMS.DocumentModule.Porozumienie.PorozumienieFacade;
import uph.ii.SIMS.DocumentModule.Zaswiadczenie.ZaswiadczenieFacade;
import uph.ii.SIMS.PdfCreationService.Dto.OswiadczeniePdfDto;
import uph.ii.SIMS.PdfCreationService.Dto.PorozumieniePdfDto;
import uph.ii.SIMS.PdfCreationService.Dto.ZaswiadczeniePdfDto;
import uph.ii.SIMS.PdfCreationService.PdfBuilder;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.GroupRepository;
import uph.ii.SIMS.UserModule.UserFacade;

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

    byte[] createPdf(String templateName, OswiadczeniePdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    byte[] createPdf(String templateName, PorozumieniePdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }

    byte[] createPdf(String templateName, ZaswiadczeniePdfDto pdfDto) throws Exception {
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
        Boolean isAdmin = userFacade.currentUserIsAdmin();
        var oswiadczenieDto = oswiadczenieFacade.find(id, currentUser, isAdmin);
        var userDto = userFacade.getUserById(oswiadczenieDto.getOwnerId());
        var pdfDto = OswiadczeniePdfDto.builder()
                .studentName(userDto.getName())
                .studentSurname(userDto.getSurname())
                .carerName(oswiadczenieDto.getOpiekunI())
                .carerSurname(oswiadczenieDto.getOpiekunN())
                .carerEmail(oswiadczenieDto.getOpiekunMail())
                .carerPhone(oswiadczenieDto.getOpiekunTel())
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
        Boolean isAdmin = userFacade.currentUserIsAdmin();
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
                .department("Nauk ścisłych i przyrodniczych")

                .companyRepresentantName(porozumienieDto.getCompanyRepresentantName())
                .companyRepresentantSurname(porozumienieDto.getCompanyRepresentantSurname())
                .build();

        return pdfBuilder.getPdfFromObject("Porozumienie", pdfDto);
    }

    public byte[] printZaswiadczenieToPdf(Long id) throws Exception {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean isAdmin = userFacade.currentUserIsAdmin();
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

    public OswiadczenieDto fetchOswiadczenie(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean isAdmin = userFacade.currentUserIsAdmin();
        return oswiadczenieFacade.find(id, currentUser, isAdmin);
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
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        oswiadczenieFacade.storeChanges(oswiadczenieDto, currentUser, userIsAdmin);
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
        Boolean isAdmin = userFacade.currentUserIsAdmin();
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
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        porozumienieFacade.storeChanges(porozumienieDto, currentUser, userIsAdmin);
    }

    public void storePorozumienie(PorozumienieDto porozumienieDto, Long studentId, Long groupId, String groupName) {
        porozumienieFacade.createNew(porozumienieDto, studentId, groupId, groupName);
    }

    public ZaswiadczenieDto fetchZaswiadczenie(Long id) {
        UserDto currentUser = userFacade.getCurrentUser();
        Boolean isAdmin = userFacade.currentUserIsAdmin();


        ZaswiadczenieDto zaswiadczenieDto = zaswiadczenieFacade.find(id, currentUser, isAdmin);
        PorozumienieDto porozumienieDto = porozumienieFacade.find2(currentUser, isAdmin, zaswiadczenieDto.getGroupId());
        if (porozumienieDto.getStatus() == StatusEnum.ACCEPTED.toString()) {
            zaswiadczenieDto.setStudentInternshipStart(porozumienieDto.getStudentInternshipStart());
            zaswiadczenieDto.setStudentInternshipEnd(porozumienieDto.getStudentInternshipEnd());
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
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        zaswiadczenieFacade.storeChanges(zaswiadczenieDto, currentUser, userIsAdmin);
    }

    public void storeZaswiadczenie(ZaswiadczenieDto zaswiadczenieDto, Long studentId, Long groupId, String groupName) {
        zaswiadczenieFacade.createNew(zaswiadczenieDto, studentId, groupId, groupName);
    }


    public List<DocumentDto> listMyDocuments() {
        UserDto currentUser = userFacade.getCurrentUser();
        return documentRepository.getAllByOwnerId(currentUser.getId()).stream()
                .map(Document::dto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> listStudentsDocuments(Long id) {
        return documentRepository.getAllByOwnerId(id).stream()
                .map(Document::dto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> listGroupDocuments(Long id) {
        return documentRepository.getAllByGroupId(id).stream()
                .map(Document::dto)
                .collect(Collectors.toList());
    }

    public void setPorozumienieComment(Long id, String newComment) {
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        porozumienieFacade.setComment(id, newComment, userIsAdmin);
    }

    public void setZaswiadczenieComment(Long id, String newComment) {
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        zaswiadczenieFacade.setComment(id, newComment, userIsAdmin);
    }

    public void setOswiadczenieComment(Long id, String newComment) {
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        oswiadczenieFacade.setComment(id, newComment, userIsAdmin);
    }

    public void setOswiadczenieStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        oswiadczenieFacade.setStatus(id, statusEnum, userIsAdmin);
    }

    public void setPorozumienieStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        porozumienieFacade.setStatus(id, statusEnum, userIsAdmin);
    }

    public void setZaswiadczenieStatus(Long id, StatusEnum statusEnum) {
        Boolean userIsAdmin = userFacade.currentUserIsAdmin();
        zaswiadczenieFacade.setStatus(id, statusEnum, userIsAdmin);
    }
}
