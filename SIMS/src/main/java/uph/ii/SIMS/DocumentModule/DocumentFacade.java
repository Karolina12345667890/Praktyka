package uph.ii.SIMS.DocumentModule;


import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieFacade;
import uph.ii.SIMS.DocumentModule.Porozumienie.PorozumienieFacade;
import uph.ii.SIMS.PdfCreationService.Dto.OswiadczeniePdfDto;
import uph.ii.SIMS.PdfCreationService.Dto.PorozumieniePdfDto;
import uph.ii.SIMS.PdfCreationService.PdfBuilder;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.UserFacade;

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
    
    byte[] createPdf(String templateName, OswiadczeniePdfDto pdfDto) throws Exception {
        return pdfBuilder.getPdfFromObject(templateName, pdfDto);
    }
    
    byte[] createPdf(String templateName, PorozumieniePdfDto pdfDto) throws Exception {
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
        var oswiadczenieDto = oswiadczenieFacade.find(id);
        var currentUserDto = userFacade.getCurrentUser();
        var pdfDto = OswiadczeniePdfDto.builder()
            .studentName(currentUserDto.getName())
            .studentSurname(currentUserDto.getSurname())
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
        var porozumienieDto = porozumienieFacade.find(id);
        var currentUserDto = userFacade.getCurrentUser();
        var pdfDto = PorozumieniePdfDto.builder()
            .studentName(currentUserDto.getName())
            .studentSurname(currentUserDto.getSurname())
            .studentSpecialization("SPECJALIZACJA")
            .studentInternshipDuration("40 tyg")
            .studentInternshipStart("01-09-2019")
            .studentInternshipEnd("30-09-2019")
            
            .companyName("NAZWA FIRMY")
            .companyLocationCity("MIASTO")
            .companyLocationStreet("ULICA")
            
            .companyRepresentantName("IMIE REPREZENTANTA")
            .companyRepresentantSurname("NAZWISKO REPREZENTANTA")
            .build();
        
        return pdfBuilder.getPdfFromObject("Porozumienie", pdfDto);
    }
    
    
    /**
     * Zwraca Dto oswiadczenia o podanym id, wywołuje {@link OswiadczenieFacade#find(Long)}
     *
     * @param id Id oswiadczenia
     * @return Dto oswiadczenia
     */
    public OswiadczenieDto fetchOswiadczenie(Long id) {
        return oswiadczenieFacade.find(id);
    }
    
    /**
     * Persystuje przekazane oświadczenie, jesli w bazie danych nie ma oświadczenia z id takim, jak w przekazanym dto zostaje utworzone nowe oświadczenie,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param oswiadczenieDto oswiadczenie do zapisania
     * @throws Exception
     */
    public void storeOswiadczenie(OswiadczenieDto oswiadczenieDto) throws Exception {
        oswiadczenieFacade.save(oswiadczenieDto);
    }
    
    public void storeOswiadczenie(OswiadczenieDto oswiadczenieDto, Long studentId, Long groupId) throws Exception {
        oswiadczenieFacade.save(oswiadczenieDto, studentId, groupId);
    }
    
    /**
     * Zwraca Dto porozumienia o podanym id, wywołuje {@link PorozumienieFacade#find(Long)}
     *
     * @param id Id porozumienia
     * @return Dto porozumienia
     */
    public PorozumienieDto fetchPorozumienie(Long id) {
        return porozumienieFacade.find(id);
    }
    
    /**
     * Persystuje przekazane porozumienie, jesli w bazie danych nie ma porozumienie z id takim, jak w przekazanym dto zostaje utworzone nowe porozumienie,
     * w przeciwnym razie aktualizowany jest już istniejący dokument
     *
     * @param porozumienieDto oswiadczenie do zapisania
     * @throws Exception
     */
    public void storePorozumienie(PorozumienieDto porozumienieDto) throws Exception {
        porozumienieFacade.save(porozumienieDto);
    }
    
    public void storePorozumienie(PorozumienieDto porozumienieDto, Long studentId, Long groupId) throws Exception {
        porozumienieFacade.save(porozumienieDto, studentId, groupId);
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
    
    
}
