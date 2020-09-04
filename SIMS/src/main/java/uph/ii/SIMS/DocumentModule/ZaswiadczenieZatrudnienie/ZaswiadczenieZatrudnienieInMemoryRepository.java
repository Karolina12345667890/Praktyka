package uph.ii.SIMS.DocumentModule.ZaswiadczenieZatrudnienie;

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
class ZaswiadczenieZatrudnienieInMemoryRepository implements ZaswiadczenieZatrudnienieRepository {

    private HashMap<Long, ZaswiadczenieZatrudnienie> map = new HashMap<>();
    private Long idSequence = 100L;

    @Override
    public ZaswiadczenieZatrudnienie save(ZaswiadczenieZatrudnienie zaswiadczenieZatrudnienie) {
        zaswiadczenieZatrudnienie.setId(idSequence++);
        map.put(zaswiadczenieZatrudnienie.getId(), zaswiadczenieZatrudnienie);
        return zaswiadczenieZatrudnienie;
    }

    @Override
    public ZaswiadczenieZatrudnienie findById(Long id) {
        return map.get(id);
    }

    @Override
    public Page<ZaswiadczenieZatrudnienie> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }

    @Override
    public Page<ZaswiadczenieZatrudnienie> findAllByOwnerId(Long ownerId, Pageable pageable) {
        List<ZaswiadczenieZatrudnienie> zaswiadczenieZatrudnienies = map.values().stream()
                .filter(zaswiadczenieZatrudnienie -> zaswiadczenieZatrudnienie.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());

        return new PageImpl<>(zaswiadczenieZatrudnienies, pageable, map.size());
    }

    @Override
    public ZaswiadczenieZatrudnienie findByGroupIdAndOwnerId(Long groupId, Long ownerId) {
        ZaswiadczenieZatrudnienie zaswiadczenieZatrudnienies = (ZaswiadczenieZatrudnienie) map.values().stream()
                .filter(zaswiadczenieZatrudnienie -> zaswiadczenieZatrudnienie.getOwnerId().equals(ownerId) && zaswiadczenieZatrudnienie.getGroupId().equals(groupId))
                .collect(Collectors.toList());

        return zaswiadczenieZatrudnienies;
    }
}
