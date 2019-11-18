package uph.ii.SIMS.UserModule.Dto;

import lombok.*;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zawierająca wszystkie wymagane dane o użytkowniku
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Setter
public class UserDto {
    private Long id;
    private String album;
    private String name;
    private String surname;
    private String email;
}
