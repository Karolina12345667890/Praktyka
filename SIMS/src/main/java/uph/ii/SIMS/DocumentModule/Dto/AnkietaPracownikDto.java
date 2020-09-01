package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu ankieta pracownik
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnkietaPracownikDto {
    private Long id;
    private Long groupId;
    private Long ownerId;

    private String companyInfo = "";
    private Integer numberOfStudent = 0;
    private String answerTo = "";
    private String answerTo1 = "";
    private String answerTo2 = "";
    private String answerTo3 = "";
    private String answerTo4 = "";
    private String answerTo5 = "";
    private String answerTo6 = "";
    private String answerTo7 = "";
    private String answerTo8 = "";
    private String answerTo9 = "";
    private String answerTo10 = "";
    private String answerTo11 = "";
    private String answerTo12 = "";
    private String answerTo13 = "";
    private String answerTo14 = "";
    private String answerTo15 = "";
    private String answerTo15text = "";
    private String answerTo16text = "";
    private String status = "";

    public AnkietaPracownikDto(Long groupId, Long ownerId) {
        this.groupId = groupId;
        this.ownerId = ownerId;
    }
}
