package uph.ii.SIMS.UserModule.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uph.ii.SIMS.UserModule.Group;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupWithStudentsDto {
    private Long id;
    private String groupName;
    private Integer durationInWeeks;
    private List<UserWithDocumentsDto> students;
    private Boolean isOpen;
    private Date startDate;
}


