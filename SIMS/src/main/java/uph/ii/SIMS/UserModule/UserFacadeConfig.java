package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uph.ii.SIMS.DocumentModule.DocumentFacade;

@Configuration
public class UserFacadeConfig {
    private UserService userService;
    private GroupService groupService;
    
    public UserFacadeConfig(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }
    
    @Bean
    public UserFacade userFacade() {
        return new UserFacade(userService, groupService);
    }
    
    
    
}
