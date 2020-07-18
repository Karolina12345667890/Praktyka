package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PorozumienieFillDto {
    private String companyName = "";
    private String companyLocationCity = "";
    private String companyLocationStreet = "";
    private String companyRepresentantName = "";
    private String companyRepresentantSurname = "";
    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String studentStudyForm = "";
    private String studentSpecialization = "";
}
