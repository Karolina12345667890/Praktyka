package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class UsersInitializer {
    
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Bean
    public void init(){
        roleRepository.deleteAll();
        Role role_user = new Role("ROLE_USER");
        Role role_admin = new Role("ROLE_ADMIN");
        roleRepository.save(role_user);
        roleRepository.save(role_admin);
        
        userRepository.deleteAll();
        userService.createNewUser("admin", "admin", Arrays.asList(role_admin, role_user));
        userService.createNewUser("user1", "user");
        userService.createNewUser("user2", "user");
        userService.createNewUser("user3", "user");
        
    }

}
