package uph.ii.SIMS.DocumentModule.Porozumienie;


import lombok.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * Klasa reprezentująca porozumienie. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */
@Entity
@Table(name = "porozumienia")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Porozumienie extends Document {
    
    public static final String DOCUMENT_TYPE = "porozumienie";
    
    private String companyName;
    private String companyLocationCity;
    private String companyLocationStreet;
    private String companyRepresentantName;
    private String companyRepresentantSurname;
    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    
    public Porozumienie(Long owner) {
        super(owner);
    }
    /**
     *
     * Metoda tworząca obiekt klasy {@link OswiadczenieDto}
     *
     * @return Obiekt klasy {@link OswiadczenieDto}, powstały na podstawie porozumienia, na którym wołana jest metoda
     */
    PorozumienieDto porozumienieDto() {
        return new PorozumienieDto(
            id,
            groupId,
            ownerId,
            companyName,
            companyLocationCity,
            companyLocationStreet,
            companyRepresentantName,
            companyRepresentantSurname,
            studentInternshipStart,
            studentInternshipEnd,
            comment,
            getStatusString()
        );
    }
    
    
    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }
}
