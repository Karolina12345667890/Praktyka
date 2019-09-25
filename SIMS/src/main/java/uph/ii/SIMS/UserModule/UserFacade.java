package uph.ii.SIMS.UserModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import uph.ii.SIMS.UserModule.Dto.UserDto;

@Configuration
public class UserFacade {
    public UserDto getCurrentUser() {
        return new UserDto(1L, "", "", "");
    }
    
    @Bean
    public UserFacade ff(){return new UserFacade();}
}
