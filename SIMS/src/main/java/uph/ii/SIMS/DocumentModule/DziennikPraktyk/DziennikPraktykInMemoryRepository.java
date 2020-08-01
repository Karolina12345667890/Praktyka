package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Repozytorium w pamięci (w postaci hashmapy), wykorzystywane w celach testowych (podniesienie kontekstu springa - 20 sekund, stworzenie mapy - milisekundy)
 */

class DziennikPraktykInMemoryRepository implements DziennikPraktykRepository {

    private Map<Long, DziennikPraktyk> map = new HashMap<>();
    private Long idSequence = 1L;

    @Override
    public DziennikPraktyk save(DziennikPraktyk DziennikPraktyk) {
        DziennikPraktyk.setId(idSequence);
        map.put(idSequence++, DziennikPraktyk);
        return DziennikPraktyk;
    }

    @Override
    public DziennikPraktyk findById(Long id) {
        return map.get(id);
    }

    @Override
    public Page<DziennikPraktyk> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }

    @Override
    public Page<DziennikPraktyk> findAllByOwnerId(Long ownerId, Pageable pageable) {
        List<DziennikPraktyk> DziennikPraktyks = map.values().stream()
                .filter(DziennikPraktyk -> DziennikPraktyk.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());

        return new PageImpl<>(DziennikPraktyks, pageable, map.size());
    }
}
