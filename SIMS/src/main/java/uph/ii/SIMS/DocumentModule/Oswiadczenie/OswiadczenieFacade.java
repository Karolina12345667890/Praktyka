package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.UserModule.UserFacade;

@AllArgsConstructor
public class OswiadczenieFacade {
    
    private OswiadczenieRepository oswiadczenieRepository;
    private UserFacade userFacade;
    
    public void save(OswiadczenieDto dto) throws Exception {
        var owner2 = userFacade.getCurrentUser();
        Long owner = userFacade.getCurrentUser().getId();
        oswiadczenieRepository.save(
            new Oswiadczenie(
                owner,
                dto.getOpiekunI(),
                dto.getOpiekunN(),
                dto.getOpiekunMail(),
                dto.getOpiekunTel())
        );
    }
    
    public OswiadczenieDto find(Long id) {
        return oswiadczenieRepository.findById(id).dto();
    }
    
    public Page<OswiadczenieDto> findMyDocuments() throws Exception {
        Long ownerId = userFacade.getCurrentUser().getId();
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        return oswiadczenieRepository.findAllByOwnerId(ownerId, pageRequest)
            .map(oswiadczenie -> oswiadczenie.dto());
    }
}
