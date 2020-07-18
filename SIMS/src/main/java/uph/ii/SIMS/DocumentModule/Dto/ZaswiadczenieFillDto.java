package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ZaswiadczenieFillDto {

    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String studentRating1 ="";
    private String studentRating2 ="";
    private String studentRating3 ="";
    private String studentRating ="";
    private String studentWorks ="";
    private String studentInterests ="";
}
