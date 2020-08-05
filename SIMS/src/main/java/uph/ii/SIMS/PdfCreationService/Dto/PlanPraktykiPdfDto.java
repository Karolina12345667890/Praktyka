package uph.ii.SIMS.PdfCreationService.Dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class PlanPraktykiPdfDto {

    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String studentTasks;
    private String studentPesel;
    private String studentName;
    private String studentSurname;

}
