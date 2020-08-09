package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ZaswiadczenieZatrudnienieFillDto {

    private String studentRoad ="";
    private String studentCity ="";
    private String studentZip ="";
    private String studentPesel ="";
    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String companyName ="";
    private String studentPosition ="";
    private String hoursPerWeek ="";
    private String studentTasks ="";

}
