package uph.ii.SIMS.UserModule.Dto;

import lombok.*;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;

import java.util.List;

/**
 * Klasa zawierająca wszystkie wymagane dane o użytkowniku
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Setter
public class UserWithDocumentsDto {
    private Long id;
    private String album;
    private String name;
    private String surname;
    private List<DocumentDto> documents;
}
