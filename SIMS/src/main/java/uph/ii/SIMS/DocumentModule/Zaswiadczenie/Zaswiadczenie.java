package uph.ii.SIMS.DocumentModule.Zaswiadczenie;

import lombok.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.DocumentModule.Dto.ZaswiadczenieDto;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * Klasa reprezentująca zaswiadczenia. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */
@Entity
@Table(name = "zaswiadczenia")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Zaswiadczenie extends Document {

    public static final String DOCUMENT_TYPE = "zaswiadczenie";

    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String studentRating1;
    private String studentRating2;
    private String studentRating3;
    private String studentRating;
    private String studentWorks;
    private String studentInterests;

    public Zaswiadczenie(Long owner) {
        super(owner);
    }

    /**
     * Metoda tworząca obiekt klasy {@link ZaswiadczenieDto}
     *
     * @return Obiekt klasy {@link ZaswiadczenieDto}, powstały na podstawie porozumienia, na którym wołana jest metoda
     */
    ZaswiadczenieDto ZaswiadczenieDto() {
        return new ZaswiadczenieDto(
                id,
                groupId,
                ownerId,
                studentInternshipStart,
                studentInternshipEnd,
                studentRating1,
                studentRating2,
                studentRating3,
                studentRating,
                studentWorks,
                studentInterests,
                comment,
                getStatusString()
        );
    }


    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }
}
