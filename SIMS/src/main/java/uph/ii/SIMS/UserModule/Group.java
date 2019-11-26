package uph.ii.SIMS.UserModule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uph.ii.SIMS.UserModule.Dto.FormOfStudyEnum;
import uph.ii.SIMS.UserModule.Dto.GroupDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    private Integer durationInWeeks;
    private String fieldOfStudy;
    @Enumerated(EnumType.STRING)
    private FormOfStudyEnum formOfStudy;
    private String speciality;
    private Date dateStart;
    private Boolean isOpen = false;
    
    @ManyToMany(mappedBy = "groups")
    private Collection<User> users = new ArrayList<>();
    
    public Group(String groupName, Integer durationInWeeks, Date dateStart) {
        this.groupName = groupName;
        this.durationInWeeks = durationInWeeks;
        this.dateStart = dateStart;
    }
    
    public void addStudent(User user) {
        user.getGroups().add(this);
        users.add(user);
    }
    
    public GroupDto dto() {
        return new GroupDto(
            id,
            groupName,
            durationInWeeks,
            fieldOfStudy,
            formOfStudy.name(),
            speciality,
            isOpen,
            dateStart,
            "/api/group/" + id
        );
    }
}
