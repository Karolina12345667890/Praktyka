package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.Date;

/**
 *
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu planu praktyk
 */

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PlanPraktykiDto {

    private Long id;
    private Long groupId;
    private Long ownerId;

    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    @Lob
    private String studentTasks;
    private String studentPesel;

    private String comment = "";
    private String status = "NEW";


    public PlanPraktykiDto(Long id, Long groupId, Long ownerId, Date studentInternshipStart, Date studentInternshipEnd) {
        this.id = id;
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.studentInternshipStart = studentInternshipStart;
        this.studentInternshipEnd = studentInternshipEnd;
    }
}
