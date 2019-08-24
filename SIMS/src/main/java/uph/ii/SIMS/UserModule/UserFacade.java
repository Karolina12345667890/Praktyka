package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import uph.ii.SIMS.UserModule.Dto.UserDto;


@AllArgsConstructor
public class UserFacade {
    
    private UserRepository userRepository;
    
    public UserDto getCurrentUser() throws Exception {
        return new UserDto(1L, "Maciej", "Nazarczuk", "abc@gmail.com");


//        return userRepository.getUserById(1L)
//            .map(user -> user.dto())
//            .orElseThrow(() -> new Exception());
    }
    
}
