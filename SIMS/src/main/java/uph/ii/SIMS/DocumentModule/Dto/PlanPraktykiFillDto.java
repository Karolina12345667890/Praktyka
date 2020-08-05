package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
@Getter
@AllArgsConstructor
public class PlanPraktykiFillDto {

    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String studentTasks = "";
    private String studentPesel = "";

}
