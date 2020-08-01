package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DiaryLog implements Serializable {
    private Date date;
    private String text;
}
