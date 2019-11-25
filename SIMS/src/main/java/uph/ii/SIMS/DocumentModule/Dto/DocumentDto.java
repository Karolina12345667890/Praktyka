package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocumentDto {
    private Long id;
    private String comment;
    private String status;
    private String link;
    private String type;
    private Long groupId;
    private Long ownerId;
}
