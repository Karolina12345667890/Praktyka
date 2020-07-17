package uph.ii.SIMS.DocumentModule;

import lombok.*;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;

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
    protected Long groupId;
    protected String comment = "";
    protected String groupName = "";
    @Enumerated(EnumType.STRING)
    protected StatusEnum statusEnum = StatusEnum.EMPTY;
    
    protected Document(Long owner) {
        this.ownerId = owner;
    }
    
    public String getStatusString() {
        return statusEnum.name();
    }
    
    public void setStatus(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }
    
    public abstract String getType();
    
    public String getUrl() {
        return URL + getType() + "/" + id;
    }
    
    public DocumentDto dto(){
        return new DocumentDto(getId(), getComment(), getStatusString(), getUrl(), getType(), getGroupId(), getOwnerId(),getGroupName());
    }
}
