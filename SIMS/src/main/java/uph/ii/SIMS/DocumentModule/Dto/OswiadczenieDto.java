package uph.ii.SIMS.DocumentModule.Dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class OswiadczenieDto {
    private Long id;
    private String opiekunI;
    private String opiekunN;
    private String opiekunMail;
    private String opiekunTel;
}
