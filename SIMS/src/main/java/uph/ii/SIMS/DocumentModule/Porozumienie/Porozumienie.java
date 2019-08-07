package uph.ii.SIMS.DocumentModule.Porozumienie;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uph.ii.SIMS.DocumentModule.Document;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Entity
@Table(name = "porozumienia")
@Builder
@Getter
public class Porozumienie extends Document {


}
