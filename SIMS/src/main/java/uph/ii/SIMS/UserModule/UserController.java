package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uph.ii.SIMS.UserModule.Dto.UserDto;

@RestController
@AllArgsConstructor
public class UserController {
    
    private UserService userService;
    
    @GetMapping("/api/user/{id}")
    UserDto userDto(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
