package uph.ii.SIMS.UserModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class GroupApplicationDto {
    private String studentName;
    private String studentSurname;
    private String studentAlbum;
    private Long id;
    private String acceptLink;
    private String declineLink;
    private Date date;
}
