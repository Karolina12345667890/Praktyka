package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.Dto.UserRegistrationDto;

@RestController
@AllArgsConstructor
public class UserRestController {

    private UserService userService;

    @GetMapping("/api/user")
    UserDto userDto() {
        return userService.getCurrentUser().dto();
    }

    @GetMapping("/api/user/{id}")
    UserDto userDto(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/api/user")
    void registerNewStudent(@RequestBody UserRegistrationDto newStudentDto) {
        var userDto = newStudentDto.userDto;
        var username = newStudentDto.username;
        var password = newStudentDto.password;
        userService.createNewStudentUser(userDto, username, password).dto();
    }
    @PostMapping("/api/userga")
    void registerNewGroupAdmin(@RequestBody UserRegistrationDto newStudentDto) {
        var userDto = newStudentDto.userDto;
        var username = newStudentDto.username;
        var password = newStudentDto.password;
        userService.createNewGroupAdminUser(userDto, username, password).dto();
    }

    @PostMapping("/api/user/edit/name")
    void editUsersName(@RequestBody String name) {
        userService.editUsersName(name);
    }

    @PostMapping("/api/user/edit/surname")
    void editUsersSurname(@RequestBody String surname) {
        userService.editUsersSurname(surname);
    }

    @PostMapping("/api/user/edit/album")
    void editUsersAlbum(@RequestBody String album) {
       userService.editUsersAlbum(album);
    }

    @PostMapping("/api/user/edit/email")
    void editUsersEmail(@RequestBody String email) {
        userService.editUsersEmail(email);
    }

    @PostMapping("/api/user/edit/pass")
    void editUsersPass(@RequestBody String pass) {
        System.out.println(pass);
        userService.editUsersPass(pass);
    }

}


