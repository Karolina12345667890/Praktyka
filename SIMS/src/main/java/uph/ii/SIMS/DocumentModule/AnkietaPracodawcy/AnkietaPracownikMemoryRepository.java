package uph.ii.SIMS.DocumentModule.AnkietaPracodawcy;

import uph.ii.SIMS.DocumentModule.AnkietaStudenta.AnkietaStudenta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Klasa odpowiedzialna za utworzenie repozytorium do testów
 *
 * klasa nie jest ukończona
 */
public class AnkietaPracownikMemoryRepository implements AnkietaPracownikRepository {

    private Map<Long, AnkietaPracownik> map = new HashMap<>();
    private Long idSequence = 100L;

    @Override
    public AnkietaPracownik save(AnkietaPracownik ankietaPracownik) {
        ankietaPracownik.setId(idSequence);
        map.put(idSequence++, ankietaPracownik);
        return ankietaPracownik;
    }

    @Override
    public AnkietaPracownik findById(Long id) {
        return map.get(id);
    }

    @Override
    public Boolean existsByGroupIdAndOwnerId(Long groupId, Long OwnerId) {
        AnkietaPracownik a = (AnkietaPracownik) map.values().stream()
                .filter(osw -> osw.getOwnerId().equals(OwnerId) && osw.getGroupId().equals(groupId));
        if(!a.equals(null))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public List<AnkietaPracownik> findAllByGroupId(Long id) {
        List<AnkietaPracownik> ankietaPracowniks = map.values().stream()
                .filter(ankietaPracownik -> ankietaPracownik.getGroupId().equals(id))
                .collect(Collectors.toList());

        return ankietaPracowniks;
    }
}
