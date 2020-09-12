package uph.ii.SIMS.DocumentModule.AnkietaStudenta;

import lombok.*;
import uph.ii.SIMS.AttributeEncryptor;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaStudentaDto;

import javax.persistence.*;

/**
 * Klasa reprezentująca ankietę studenta. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */

@Entity
@Table(name = "ankiety_student")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
public class AnkietaStudenta extends Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public static final String DOCUMENT_TYPE = "ankieta_studenta";
    @Convert(converter = AttributeEncryptor.class)
    private String studentName = "";
    @Convert(converter = AttributeEncryptor.class)
    private String studentSurname = "";
    @Convert(converter = AttributeEncryptor.class)
    private String studentSpecialization= "";
    private String instytutionType;
    private String companyNameAndLocation;
    private String studentInternshipStart;
    private String studentInternshipEnd;
    private String answerTo1;
    private String answerTo2;
    private String answerTo3;
    private String answerTo4;
    private String answerTo5;
    private String answerTo5atext;
    private String answerTo6;
    private String answerTo7;
    private String answerTo7atext;
    private String answerTo8;
    private String answerTo91;
    private String answerTo92;
    private String answerTo93;
    private String answerTo10;
    private String answerTo11;
    private String answerTo11text;
    private String answerTo12;
    private String answerTo12atext;
    private String answerTo12btext;
    private String answerTo13;
    private String answerTo13text;
    private String answerTo14text;

    AnkietaStudenta(Long owner) {
        super(owner);
    }


    AnkietaStudentaDto ankietaStudentaDto() {
        return new AnkietaStudentaDto(
                id,
                groupId,
                ownerId,
                studentName,
                studentSurname,
                studentSpecialization,
                instytutionType,
                companyNameAndLocation,
                studentInternshipStart,
                studentInternshipEnd,
                answerTo1,
                answerTo2,
                answerTo3,
                answerTo4,
                answerTo5,
                answerTo5atext,
                answerTo6,
                answerTo7,
                answerTo7atext,
                answerTo8,
                answerTo91,
                answerTo92,
                answerTo93,
                answerTo10,
                answerTo11,
                answerTo11text,
                answerTo12,
                answerTo12atext,
                answerTo12btext,
                answerTo13,
                answerTo13text,
                answerTo14text,
                getStatusString()
        );
    }

    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }
}
