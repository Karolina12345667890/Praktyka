package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uph.ii.SIMS.DocumentModule.DziennikPraktyk.DiaryLog;

import java.util.ArrayList;
import java.util.Date;

@Getter
@AllArgsConstructor
public class DziennikPraktykFillDto {

    private Date periodFrom ;
    private Date periodTo;
    private String studentAlbumNumber ="";
    private String companyName = "";
    private ArrayList<DiaryLog> diary = new ArrayList<DiaryLog>();


}
