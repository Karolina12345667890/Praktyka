package uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie;

import lombok.*;
import uph.ii.SIMS.AttributeEncryptor;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieZatrudnienieDto;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Klasa reprezentująca zaswiadczenie o zatrudnieniu. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */
@Entity
@Table(name = "zaswiadczeniaZatrudnienie")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZaswiadczenieZatrudnienie extends Document {

    public static final String DOCUMENT_TYPE = "zaswiadczeniezatrudnienie";

    private String studentRoad;
    private String studentCity;
    private String studentZip;
    @Convert(converter = AttributeEncryptor.class)
    private String studentPesel = "";
    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String companyName;
    private String studentPosition;
    private String hoursPerWeek;
    private String studentTasks;

    public ZaswiadczenieZatrudnienie(Long owner) {
        super(owner);
    }

    /**
     * Metoda tworząca obiekt klasy {@link ZaswiadczenieZatrudnienieDto}
     *
     * @return Obiekt klasy {@link ZaswiadczenieZatrudnienieDto}, powstały na podstawie porozumienia, na którym wołana jest metoda
     */

    ZaswiadczenieZatrudnienieDto ZaswiadczenieZatrudnienieDto() {
        return new ZaswiadczenieZatrudnienieDto(
                id,
                groupId,
                ownerId,
                studentRoad,
                studentCity,
                studentZip,
                studentPesel,
                studentInternshipStart,
                studentInternshipEnd,
                companyName,
                studentPosition,
                hoursPerWeek,
                studentTasks,
                comment,
                getStatusString()
        );
    }


    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }
}
