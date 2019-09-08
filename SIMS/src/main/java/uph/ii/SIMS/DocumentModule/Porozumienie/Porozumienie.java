package uph.ii.SIMS.DocumentModule.Porozumienie;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "porozumienia")
@Builder
@Getter
@AllArgsConstructor
class Porozumienie extends Document {
    
    public Porozumienie(Long owner) {
        super(owner);
    }
    
    PorozumienieDto dto() {
        return PorozumienieDto.builder()
            .id(id)
            .build();
    }
    
}
