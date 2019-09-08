package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "oswiadczenia")

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
class Oswiadczenie extends Document {
    
    private String opiekunI;
    private String opiekunN;
    private String opiekunMail;
    private String opiekunTel;
    
    Oswiadczenie(Long owner, String opiekunI, String opiekunN, String opiekunMail, String opiekunTel) {
        super(owner);
        this.opiekunI = opiekunI;
        this.opiekunN = opiekunN;
        this.opiekunMail = opiekunMail;
        this.opiekunTel = opiekunTel;
    }
    
    OswiadczenieDto dto() {
        return OswiadczenieDto.builder()
            .id(this.id)
            .opiekunI(this.opiekunI)
            .opiekunN(this.opiekunN)
            .opiekunMail(this.opiekunMail)
            .opiekunTel(this.opiekunTel)
            .build();
    }
}
