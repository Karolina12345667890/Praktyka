package uph.ii.SIMS.PdfCreationService.Dto;

import lombok.Builder;
import lombok.Getter;
import uph.ii.SIMS.DocumentModule.DziennikPraktyk.DiaryLog;

import java.util.ArrayList;
import java.util.Date;
@Getter
@Builder
public class DziennikPraktykPdfDto {

    private String studentName;
    private String studentSurname;
    private Date periodFrom;
    private Date periodTo;
    private String studentAlbumNumber;
    private String companyName;
    private ArrayList<DiaryLog> diary;
}
