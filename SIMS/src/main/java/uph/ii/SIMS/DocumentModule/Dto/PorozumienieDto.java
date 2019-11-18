package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu porozumienia
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class PorozumienieDto {
    private Long id;
    private String comment;
    private String status;
    
}
