package uph.ii.SIMS.UserModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationDto {
    public UserDto userDto;
    public String username;
    public String password;

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "userDto=" + userDto +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
