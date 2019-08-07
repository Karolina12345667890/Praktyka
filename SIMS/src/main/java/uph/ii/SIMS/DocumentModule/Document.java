package uph.ii.SIMS.DocumentModule;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
abstract public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "qqq")
    protected Long id;
    
    protected Long userId;
    
    public Document(Long owner) {
        this.userId = owner;
    }
    
}
