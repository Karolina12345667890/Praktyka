package uph.ii.SIMS.DocumentModule.Dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu oswiadczenie
 */
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
