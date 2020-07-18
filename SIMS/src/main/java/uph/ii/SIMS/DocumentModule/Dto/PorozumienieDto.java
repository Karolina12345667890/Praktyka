package uph.ii.SIMS.DocumentModule.Dto;

import lombok.*;

import java.util.Date;

/**
 *
 * Klasa, zawierająca wszystkie dane potrzebne do utworzenia nowego dokumentu porozumienia
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PorozumienieDto {
    private Long id;
    private Long groupId;
    private Long ownerId;
    
    private String companyName = "";
    private String companyLocationCity = "";
    private String companyLocationStreet = "";
    private String companyRepresentantName = "";
    private String companyRepresentantSurname = "";
    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String studentStudyForm;
    private String studentSpecialization;

    private String comment = "";
    private String status = "NEW";
    
    
    public PorozumienieDto(Long id, Long groupId, Long ownerId, Date studentInternshipStart, Date studentInternshipEnd) {
        this.id = id;
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.studentInternshipStart = studentInternshipStart;
        this.studentInternshipEnd = studentInternshipEnd;
    }
}
