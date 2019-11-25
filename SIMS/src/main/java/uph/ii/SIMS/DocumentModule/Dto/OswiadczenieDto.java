package uph.ii.SIMS.DocumentModule.Dto;

import lombok.*;

/**
 *
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu oswiadczenie
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OswiadczenieDto {
    private Long id;
    private Long groupId;
    private Long ownerId;
    private String opiekunI;
    private String opiekunN;
    private String opiekunMail;
    private String opiekunTel;
    private String comment;
    private String status;
    
    public OswiadczenieDto(Long id, Long groupId, Long ownerId) {
        this.id = id;
        this.groupId = groupId;
        this.ownerId = ownerId;
    }
}
