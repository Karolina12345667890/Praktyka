package uph.ii.SIMS.DocumentModule.Zaswiadczenie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Repozytorium w pamięci (w postaci hashmapy), wykorzystywane w celach testowych (podniesienie kontekstu springa - 20 sekund, stworzenie mapy - milisekundy)
 */
class ZaswiadczenieInMemoryRepository implements ZaswiadczenieRepository {

    private HashMap<Long, Zaswiadczenie> map = new HashMap<>();
    private Long idSequence = 100L;

    @Override
    public Zaswiadczenie save(Zaswiadczenie Zaswiadczenie) {
        Zaswiadczenie.setId(idSequence++);
        map.put(Zaswiadczenie.getId(), Zaswiadczenie);
        return Zaswiadczenie;
    }

    @Override
    public Zaswiadczenie findById(Long id) {
        return map.get(id);
    }

    @Override
    public Page<Zaswiadczenie> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }

    @Override
    public Page<Zaswiadczenie> findAllByOwnerId(Long ownerId, Pageable pageable) {
        List<Zaswiadczenie> Zaswiadczenies = map.values().stream()
                .filter(Zaswiadczenie -> Zaswiadczenie.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());

        return new PageImpl<>(Zaswiadczenies, pageable, map.size());
    }
}
