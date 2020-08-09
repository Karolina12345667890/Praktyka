package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu zaswiadczenia o zatrudnieniu
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ZaswiadczenieZatrudnienieDto {

    private Long id;
    private Long groupId;
    private Long ownerId;

    private String studentRoad;
    private String studentCity;
    private String studentZip;
    private String studentPesel;
    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String companyName;
    private String studentPosition;
    private String hoursPerWeek;
    private String studentTasks;

    private String comment = "";
    private String status = "NEW";

    public ZaswiadczenieZatrudnienieDto(Long id, Long groupId, Long ownerId, Date studentInternshipStart, Date studentInternshipEnd) {
        this.id = id;
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.studentInternshipStart = studentInternshipStart;
        this.studentInternshipEnd = studentInternshipEnd;
    }
}
