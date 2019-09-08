package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import uph.ii.SIMS.UserModule.Dto.UserDto;

import javax.persistence.*;

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
    
    UserDto dto() {
        return UserDto.builder()
            .id(id)
            .name(name)
            .surname(surname)
            .email(email)
            .build();
    }
}
