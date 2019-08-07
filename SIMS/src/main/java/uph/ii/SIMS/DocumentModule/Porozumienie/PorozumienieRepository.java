package uph.ii.SIMS.DocumentModule.Porozumienie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

interface PorozumienieRepository extends Repository<Porozumienie, Long> {
    
    Porozumienie save(Porozumienie Porozumienie);
    
    Porozumienie findById(Long id);
    
    Page<Porozumienie> findAll(Pageable pageable);
    
    Page<Porozumienie> findAllByOwnerId(Long ownerId, Pageable pageable);
    
}
