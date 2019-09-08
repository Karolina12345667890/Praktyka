package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Repozytorium w pamięci (w postaci hashmapy), wykorzystywane w celach testowych (podniesienie kontekstu springa - 20 sekund, stworzenie mapy - milisekundy)
 */
class OswiadczenieInMemoryRepository implements OswiadczenieRepository {
    
    private Map<Long, Oswiadczenie> map = new HashMap<>();
    private Long idSequence = 1L;
    
    @Override
    public Oswiadczenie save(Oswiadczenie oswiadczenie) {
        oswiadczenie.setId(idSequence);
        map.put(idSequence++, oswiadczenie);
        return oswiadczenie;
    }
    
    @Override
    public Oswiadczenie findById(Long id) {
        return map.get(id);
    }
    
    @Override
    public Page<Oswiadczenie> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }
    
    @Override
    public Page<Oswiadczenie> findAllByOwnerId(Long ownerId, Pageable pageable) {
        List<Oswiadczenie> oswiadczenies = map.values().stream()
            .filter(oswiadczenie -> oswiadczenie.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
        
        return new PageImpl<>(oswiadczenies, pageable, map.size());
    }
}
