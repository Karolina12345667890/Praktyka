package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
    }

}
