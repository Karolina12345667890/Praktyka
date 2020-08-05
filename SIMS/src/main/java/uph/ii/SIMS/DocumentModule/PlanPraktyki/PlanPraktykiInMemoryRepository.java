package uph.ii.SIMS.DocumentModule.PlanPraktyki;

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
class PlanPraktykiInMemoryRepository implements PlanPraktykiRepository {

    private Map<Long, PlanPraktyki> map = new HashMap<>();
    private Long idSequence = 1L;

    @Override
    public PlanPraktyki save(PlanPraktyki planPraktyki) {
        planPraktyki.setId(idSequence);
        map.put(idSequence++, planPraktyki);
        return planPraktyki;
    }

    @Override
    public PlanPraktyki findById(Long id) {
        return map.get(id);
    }

    @Override
    public Page<PlanPraktyki> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }

    @Override
    public Page<PlanPraktyki> findAllByOwnerId(Long ownerId, Pageable pageable) {
        List<PlanPraktyki> planPraktykis = map.values().stream()
                .filter(planPraktyki -> planPraktyki.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());

        return new PageImpl<>(planPraktykis, pageable, map.size());
    }
}
