package uph.ii.SIMS.DocumentModule.Porozumienie;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.UserModule.UserFacade;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PorozumienieFacade {
    
    private PorozumienieRepository porozumienieRepository;
    private UserFacade userFacade;
    
    public PorozumienieDto find(Long id) {
        return porozumienieRepository.findById(id).dto();
    }
    
    public void save(PorozumienieDto porozumienieDto) throws Exception {
        Long ownerId = userFacade.getCurrentUser().getId();
        
        porozumienieRepository.save(new Porozumienie(
            ownerId
        ));
    }
}
