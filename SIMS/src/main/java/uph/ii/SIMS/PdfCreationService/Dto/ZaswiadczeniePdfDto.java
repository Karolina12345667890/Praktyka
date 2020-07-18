package uph.ii.SIMS.PdfCreationService.Dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ZaswiadczeniePdfDto {

    private String studentInternshipStart;
    private String studentInternshipEnd;
    private String studentRating1;
    private String studentRating2;
    private String studentRating3;
    private String studentRating;
    private String studentWorks;
    private String studentInterests;
    private String studentName;
    private String studentSurname;
}

