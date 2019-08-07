package uph.ii.SIMS.DocumentModule.Oswiadczenie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

interface OswiadczenieRepository extends Repository<Oswiadczenie, Long> {
    
    Oswiadczenie save(Oswiadczenie oswiadczenie);
    
    Oswiadczenie findById(Long id);
    
    Page<Oswiadczenie> findAll(Pageable pageable);
    
    Page<Oswiadczenie> findAllByOwnerId(Long ownerId, Pageable pageable);
}
