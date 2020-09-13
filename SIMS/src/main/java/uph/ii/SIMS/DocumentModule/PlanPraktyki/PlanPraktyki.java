package uph.ii.SIMS.DocumentModule.PlanPraktyki;


import lombok.*;
import uph.ii.SIMS.AttributeEncryptor;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.PlanPraktykiDto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "planpraktyki")

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
public class PlanPraktyki extends Document {

    public static final String DOCUMENT_TYPE = "planpraktyki";
    private Date studentInternshipStart;
    private Date studentInternshipEnd;
   // @Lob
   @Column(length = 2048)
    private String studentTasks;
    @Convert(converter = AttributeEncryptor.class)
    private String studentPesel = "";

    PlanPraktyki(Long owner) {
        super(owner);
    }

    /**
     * Metoda tworząca obiekt klasy {@link PlanPraktykiDto}
     *
     * @return Obiekt klasy {@link PlanPraktykiDto}, powstały na podstawie oswiadczenia, na którym wołana jest metoda
     */
    PlanPraktykiDto PlanPraktykiDto() {
        return new PlanPraktykiDto(
                id,
                groupId,
                ownerId,
                studentInternshipStart,
                studentInternshipEnd,
                studentTasks,
                studentPesel,
                comment,
                getStatusString()
        );
    }


    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }
}


