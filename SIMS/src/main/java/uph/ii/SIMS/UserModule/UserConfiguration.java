package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class UserConfiguration {
    
    private UserRepository userRepository;
    
    @Bean
    UserFacade userFacade() {
        return new UserFacade(userRepository);
    }
    
    UserFacade userFacadeInMemoryIO() {
        return new UserFacade(new UserInMemoryRepository());
    }
}
