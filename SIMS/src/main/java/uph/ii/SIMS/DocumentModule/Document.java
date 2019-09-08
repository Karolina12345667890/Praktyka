package uph.ii.SIMS.DocumentModule;

import lombok.*;

import javax.persistence.*;


/**
 * Abstrakcyjna klasa dokumentu, zawiera pola Id i Id właściciela dokumentu
 */
@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
abstract public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    protected Long ownerId;
    
    protected Document(Long owner) {
        this.ownerId = owner;
    }
}
