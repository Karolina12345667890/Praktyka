package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.*;
import uph.ii.SIMS.AttributeEncryptor;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;

import javax.persistence.*;

/**
 * Klasa reprezentująca oswiadczenie. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */
@Entity
@Table(name = "oswiadczenia")

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
class Oswiadczenie extends Document {
    
    public static final String DOCUMENT_TYPE = "oswiadczenie";
    @Convert(converter = AttributeEncryptor.class)
    private String opiekunI = "";
    @Convert(converter = AttributeEncryptor.class)
    private String opiekunN = "";
    @Convert(converter = AttributeEncryptor.class)
    private String opiekunMail = "";
    @Convert(converter = AttributeEncryptor.class)
    private String opiekunTel = "";
    //@Lob
    @Column(length = 2048)
    private String studentDuties;
    
    Oswiadczenie(Long owner) {
        super(owner);
    }
    
    /**
     * Metoda tworząca obiekt klasy {@link OswiadczenieDto}
     *
     * @return Obiekt klasy {@link OswiadczenieDto}, powstały na podstawie oswiadczenia, na którym wołana jest metoda
     */
    OswiadczenieDto oswiadczenieDto() {
        return new OswiadczenieDto(
            id,
            groupId,
            ownerId,
            opiekunI,
            opiekunN,
            opiekunMail,
            opiekunTel,
            studentDuties,
            comment,
            getStatusString()
        );
    }
    
    
    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }
}
