package uph.ii.SIMS.DocumentModule.Porozumienie;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uph.ii.SIMS.UserModule.UserFacade;

@Configuration
@AllArgsConstructor
public class PorozumienieConfiguration {
    
    UserFacade userFacade;
    
    @Bean
    PorozumienieFacade PorozumienieFacade(PorozumienieRepository porozumienieRepository) {
        return new PorozumienieFacade(porozumienieRepository, userFacade);
    }
    
    public PorozumienieFacade porozumienieFacadeInMemoryIO() {
        PorozumienieRepository PorozumienieInMemoryRepository = new PorozumienieInMemoryRepository();
        return new PorozumienieFacade(PorozumienieInMemoryRepository, userFacade);
    }
    
}
