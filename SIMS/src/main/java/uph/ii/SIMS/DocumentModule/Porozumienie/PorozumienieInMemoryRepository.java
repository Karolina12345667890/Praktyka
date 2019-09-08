package uph.ii.SIMS.DocumentModule.Porozumienie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repozytorium w pamięci (w postaci hashmapy), wykorzystywane w celach testowych (podniesienie kontekstu springa - 20 sekund, stworzenie mapy - milisekundy)
 */
class PorozumienieInMemoryRepository implements PorozumienieRepository {
    
    private HashMap<Long, Porozumienie> map = new HashMap<>();
    private Long idSequence = 100L;
    
    @Override
    public Porozumienie save(Porozumienie porozumienie) {
        porozumienie.setId(idSequence++);
        map.put(porozumienie.getId(), porozumienie);
        return porozumienie;
    }
    
    @Override
    public Porozumienie findById(Long id) {
        return map.get(id);
    }
    
    @Override
    public Page<Porozumienie> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }
    
    @Override
    public Page<Porozumienie> findAllByOwnerId(Long ownerId, Pageable pageable) {
        List<Porozumienie> porozumienies = map.values().stream()
            .filter(porozumienie -> porozumienie.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
        
        return new PageImpl<>(porozumienies, pageable, map.size());
    }
}
