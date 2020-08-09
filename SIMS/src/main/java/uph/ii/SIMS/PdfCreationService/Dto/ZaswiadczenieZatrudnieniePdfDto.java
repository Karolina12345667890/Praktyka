package uph.ii.SIMS.PdfCreationService.Dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ZaswiadczenieZatrudnieniePdfDto {

    private String studentName;
    private String studentSurname;
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
}
