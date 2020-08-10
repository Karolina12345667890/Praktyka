package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.UserModule.Dto.StudentRegistrationDto;
import uph.ii.SIMS.UserModule.Dto.UserDto;

@RestController
@AllArgsConstructor
public class UserRestController {

    private UserService userService;

    @GetMapping("/api/user/{id}")
    UserDto userDto(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/api/user")
    UserDto registerNewUser(@RequestBody StudentRegistrationDto newStudentDto) {
        var userDto = newStudentDto.userDto;
        var username = newStudentDto.username;
        var password = newStudentDto.password;
        if (userService.currentUserIsAdmin())
            return userService.createNewGroupAdminUser(userDto, username, password).dto();
        else
            return userService.createNewStudentUser(userDto, username, password).dto();
    }
}


