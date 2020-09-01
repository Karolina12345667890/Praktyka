package uph.ii.SIMS.DocumentModule.AnkietaStudenta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * klasa potrzebna do testów?
 */
public class AnkietaStudentaMemoryRepository implements AnkietaStudentaRepository {
    private Map<Long, AnkietaStudenta> map = new HashMap<>();
    private Long idSequence = 100L;

    @Override
    public AnkietaStudenta save(AnkietaStudenta ankietaStudenta) {
        ankietaStudenta.setId(idSequence);
        map.put(idSequence++, ankietaStudenta);
        return ankietaStudenta;
    }

    @Override
    public AnkietaStudenta findById(Long id) {
        return map.get(id);
    }

    @Override
    public Boolean existsByGroupIdAndOwnerId(Long groupId, Long OwnerId) {
        AnkietaStudenta a = (AnkietaStudenta) map.values().stream()
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
    public List<AnkietaStudenta> findAllByGroupId(Long id) {
        List<AnkietaStudenta> ankietaStudentas = map.values().stream()
                .filter(ankietaStudenta -> ankietaStudenta.getGroupId().equals(id))
                .collect(Collectors.toList());

        return ankietaStudentas;
    }

}
