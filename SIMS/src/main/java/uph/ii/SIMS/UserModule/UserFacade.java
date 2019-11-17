package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import uph.ii.SIMS.UserModule.Dto.UserDto;

@AllArgsConstructor
public class UserFacade {
    
    private UserService userService;
    
    public UserDto getCurrentUser() {
        User user = (User) userService.loadCurrentUser();
        return UserDto.builder()
            .name(user.getName())
            .surname(user.getSurname())
            .email(user.getUsername())
            .id(user.getId())
            .build();
    }
    
}
