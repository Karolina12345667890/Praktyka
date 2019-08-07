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
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    UserDto dto() {
        return UserDto.builder()
            .id(this.id)
            .build();
    }
}
