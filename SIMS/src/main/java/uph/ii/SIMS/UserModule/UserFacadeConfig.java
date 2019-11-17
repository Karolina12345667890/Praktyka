package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@Configuration
public class UserFacadeConfig {
    private UserService userService;
    
    @Bean
    public UserFacade userFacade() {
        return new UserFacade(userService);
    }
    
    
    
}
