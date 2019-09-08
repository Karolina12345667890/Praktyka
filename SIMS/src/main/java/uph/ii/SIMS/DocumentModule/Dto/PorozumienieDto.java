package uph.ii.SIMS.DocumentModule.Dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu porozumienia
 */
@Getter
@Builder
@EqualsAndHashCode
public class PorozumienieDto {
    private Long id;
}
