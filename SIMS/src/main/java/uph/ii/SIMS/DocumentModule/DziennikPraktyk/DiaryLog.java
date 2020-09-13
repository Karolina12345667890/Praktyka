package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    private String text;
}
