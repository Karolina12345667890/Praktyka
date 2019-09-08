package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.UserModule.Dto.UserDto;

import javax.persistence.*;

/**
 * Klasa reprezentująca użytkownika. Posiada adnotację {@link javax.persistence.Entity}, przez co posiada również reprezentację w bazie danych
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder
class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String surname;
    private String email;
    
    /**
     * Metoda tworząca obiekt klasy {@link UserDto}
     * @return Obiekt klasy {@link UserDto}, powstały na podstawie użytkownika, na którym wołana jest metoda
     */
    UserDto dto() {
        return UserDto.builder()
            .id(id)
            .name(name)
            .surname(surname)
            .email(email)
            .build();
    }
}
