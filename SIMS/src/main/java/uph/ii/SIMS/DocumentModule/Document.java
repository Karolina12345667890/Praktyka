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
    
    public static final String URL = "/api/document/";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Long ownerId;
    protected String comment;
    
    protected Document(Long owner) {
        this.ownerId = owner;
    }
    
    public abstract String getStatus();
    
    public abstract String getType();
    
    public String getUrl() {
        return URL + getType() + "/" + id;
    }
}
