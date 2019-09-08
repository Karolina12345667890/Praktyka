package uph.ii.SIMS.PdfCreationService.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OswiadczeniePdfDto {
    
    private String studentName;
    private String studentSurname;
    private String studentDuties;
    private String carerName;
    private String carerSurname;
    private String carerPhone;
    private String carerEmail;
    
}
