package uph.ii.SIMS.DocumentModule.AnkietaPracodawcy;

import lombok.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaPracownikDto;

import javax.persistence.*;

/**
 * Klasa reprezentująca ankietę pracodawcy. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */

@Entity
@Table(name = "ankiety_pracownik")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
public class AnkietaPracownik extends Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public static final String DOCUMENT_TYPE = "ankieta_pracownik";

    private String companyInfo;
    private Integer numberOfStudent;
    private String answerTo;
    private String answerTo1;
    private String answerTo2;
    private String answerTo3;
    private String answerTo4;
    private String answerTo5;
    private String answerTo6;
    private String answerTo7;
    private String answerTo8;
    private String answerTo9;
    private String answerTo10;
    private String answerTo11;
    private String answerTo12;
    private String answerTo13;
    private String answerTo14;
    private String answerTo15;
    private String answerTo15text;
    private String answerTo16text;

    AnkietaPracownik(Long owner)
    {
        super(owner);
    }

    AnkietaPracownikDto ankietaPracownikDto() {
        return new AnkietaPracownikDto(
                id,
                groupId,
                ownerId,
                companyInfo,
                numberOfStudent,
                answerTo,
                answerTo1,
                answerTo2,
                answerTo3,
                answerTo4,
                answerTo5,
                answerTo6,
                answerTo7,
                answerTo8,
                answerTo9,
                answerTo10,
                answerTo11,
                answerTo12,
                answerTo13,
                answerTo14,
                answerTo15,
                answerTo15text,
                answerTo16text,
                getStatusString()
        );
    }

    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }
}
