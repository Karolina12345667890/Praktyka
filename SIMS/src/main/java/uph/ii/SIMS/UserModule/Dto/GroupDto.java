package uph.ii.SIMS.UserModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String groupName;
    private Integer durationInWeeks;
    private Boolean isOpen;
    private Date startDate;
    private String link;
}


