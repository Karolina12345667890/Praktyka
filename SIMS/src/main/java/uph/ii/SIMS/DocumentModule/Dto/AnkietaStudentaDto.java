package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa zawierająca wszystkie informacje potrzebne do stworzenia ankiety studenta
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnkietaStudentaDto {
    private Long id;
    private Long groupId;
    private Long ownerId;

    private String studentName = "";
    private String studentSurname = "";
    private String studentSpecialization = "";
    private String instytutionType = "";
    private String companyNameAndLocation = "";
    private String studentInternshipStart = "";
    private String studentInternshipEnd = "";
    private String answerTo1 = "";
    private String answerTo2 = "";
    private String answerTo3 = "";
    private String answerTo4 = "";
    private String answerTo5 = "";
    private String answerTo5atext = "";
    private String answerTo6 = "";
    private String answerTo7 = "";
    private String answerTo7atext = "";
    private String answerTo8 = "";
    private String answerTo91 = "";
    private String answerTo92 = "";
    private String answerTo93 = "";
    private String answerTo10 = "";
    private String answerTo11 = "";
    private String answerTo11text = "";
    private String answerTo12 = "";
    private String answerTo12atext = "";
    private String answerTo12btext = "";
    private String answerTo13 = "";
    private String answerTo13text = "";
    private String answerTo14text = "";

    private String status = "";

    public AnkietaStudentaDto(Long groupId, Long ownerId) {
        this.groupId = groupId;
        this.ownerId = ownerId;
    }

}
