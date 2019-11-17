package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersInitializer {
    
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Bean
    public void init(){
        userRepository.deleteAll();
        
        userService.createNewUser("admin", "admin");
        userService.createNewUser("user1", "user");
        userService.createNewUser("user2", "user");
        userService.createNewUser("user3", "user");
    }

}
