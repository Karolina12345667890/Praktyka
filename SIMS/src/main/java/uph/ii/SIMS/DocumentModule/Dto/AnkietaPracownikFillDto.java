package uph.ii.SIMS.DocumentModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class AnkietaPracownikFillDto {
    private long groupeId = 0;
    private String answerTo15text = "";
    private String answerTo16text = "";
    private String companyInfo = "";
    private Integer numberOfStudent = 0;
    private ArrayList<String> answer = new ArrayList<String>();
}
