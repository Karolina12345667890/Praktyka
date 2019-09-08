package uph.ii.SIMS.PdfCreationService.Dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Zawiera wszystkie pola wymagane do utworzenia pdf porozumienia
 */
@Getter
@Builder
public class PorozumieniePdfDto {
    private String companyName;
    private String companyLocationCity;
    private String companyLocationStreet;
    private String companyRepresentantName;
    private String companyRepresentantSurname;
    private String studentSpecialization;
    private String studentInternshipDuration;
    private String studentName;
    private String studentSurname;
    private String studentInternshipStart;
    private String studentInternshipEnd;
}
