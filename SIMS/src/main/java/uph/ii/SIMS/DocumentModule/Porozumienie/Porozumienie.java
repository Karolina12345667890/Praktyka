package uph.ii.SIMS.DocumentModule.Porozumienie;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Klasa reprezentująca porozumienie. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */
@Entity
@Table(name = "porozumienia")
@Builder
@Getter
@AllArgsConstructor
class Porozumienie extends Document {
    
    public Porozumienie(Long owner) {
        super(owner);
    }
    
    /**
     * Metoda tworząca obiekt klasy {@link OswiadczenieDto}
     *
     * @return Obiekt klasy {@link OswiadczenieDto}, powstały na podstawie porozumienia, na którym wołana jest metoda
     */
    PorozumienieDto dto() {
        return PorozumienieDto.builder()
            .id(id)
            .build();
    }
    
}
