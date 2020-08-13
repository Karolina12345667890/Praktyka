package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.Dto.UserRegistrationDto;

@RestController
@AllArgsConstructor
public class UserRestController {

    private UserService userService;

    @GetMapping("/api/user/{id}")
    UserDto userDto(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/api/user")
    UserDto registerNewStudent(@RequestBody UserRegistrationDto newStudentDto) {
        var userDto = newStudentDto.userDto;
        var username = newStudentDto.username;
        var password = newStudentDto.password;
        return userService.createNewStudentUser(userDto, username, password).dto();
    }
    @PostMapping("/api/userga")
    UserDto registerNewGroupAdmin(@RequestBody UserRegistrationDto newStudentDto) {
        var userDto = newStudentDto.userDto;
        var username = newStudentDto.username;
        var password = newStudentDto.password;
        System.out.println(newStudentDto);
        return userService.createNewGroupAdminUser(userDto, username, password).dto();
    }
}


