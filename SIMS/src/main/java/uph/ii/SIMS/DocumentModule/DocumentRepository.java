package uph.ii.SIMS.DocumentModule;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface DocumentRepository extends Repository<Document, Long> {
    List<Document> getAllByOwnerId(Long ownerId);
    List<Document> getAllByGroupId(Long groupId);
    List<Document> getAllByGroupIdAndOwnerId(Long groupId,Long ownerId);
    List<Document> removeAllByGroupIdAndOwnerId(Long groupId,Long ownerId);
    Boolean existsByGroupIdAndOwnerId(Long groupId, Long ownerId);
   Document save(Document document);
}
