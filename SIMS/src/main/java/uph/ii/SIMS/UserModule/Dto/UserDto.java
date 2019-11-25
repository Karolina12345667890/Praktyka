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
@Setter
public class UserDto {
    private Long id;
    private String album;
    private String name;
    private String surname;
    private String email;
    private List<String> roles;
    
    public UserDto(Long id, String album, String name, String surname, String email) {
        this.id = id;
        this.album = album;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
