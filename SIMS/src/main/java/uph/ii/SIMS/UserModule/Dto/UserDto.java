package uph.ii.SIMS.UserModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Klasa zawierająca wszystkie wymagane dane o użytkowniku
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
}
