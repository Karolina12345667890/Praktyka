package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.Dto.UserRegistrationDto;

import java.util.Map;

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
    void editUsersName(@RequestBody Object value) {
        Map<String, Object> obj = (Map<String, Object>) value;
        userService.editUsersName(obj.get("name").toString());
    }

    @PostMapping("/api/user/edit/surname")
    void editUsersSurname(@RequestBody Object value) {
        Map<String, Object> obj = (Map<String, Object>) value;
        userService.editUsersSurname(obj.get("surname").toString());
    }

    @PostMapping("/api/user/edit/album")
    void editUsersAlbum(@RequestBody Object value) {
        Map<String, Object> obj = (Map<String, Object>) value;
       userService.editUsersAlbum(obj.get("album").toString());
    }

    @PostMapping("/api/user/edit/email")
    void editUsersEmail(@RequestBody Object value) {
        Map<String, Object> obj = (Map<String, Object>) value;
        userService.editUsersEmail(obj.get("email").toString());
    }

    @PostMapping("/api/user/edit/pass")
    void editUsersPass(@RequestBody Object value) {
        Map<String, Object> obj = (Map<String, Object>) value;
        userService.editUsersPass(obj.get("pass").toString());
    }

}


