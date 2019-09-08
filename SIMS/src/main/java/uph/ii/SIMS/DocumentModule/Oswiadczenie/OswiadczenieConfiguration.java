package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.UserModule.UserFacade;

@Configuration
@AllArgsConstructor
public class OswiadczenieConfiguration {
    
    UserFacade userFacade;
    
    @Bean
    OswiadczenieFacade oswiadczenieFacade(OswiadczenieRepository oswiadczenieRepository) {
        return new OswiadczenieFacade(oswiadczenieRepository, userFacade);
    }
    
    public OswiadczenieFacade oswiadczenieFacadeInMemoryIO() {
        OswiadczenieRepository oswiadczenieInMemoryRepository = new OswiadczenieInMemoryRepository();
        return new OswiadczenieFacade(oswiadczenieInMemoryRepository, userFacade);
    }
}
