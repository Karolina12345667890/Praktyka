package uph.ii.SIMS.DocumentModule.Dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ZaswiadczenieDto {

    private Long id;
    private Long groupId;
    private Long ownerId;

    private Date studentInternshipStart;
    private Date studentInternshipEnd;
    private String studentRating1;
    private String studentRating2;
    private String studentRating3;
    private String studentRating;
    private String studentWorks;
    private String studentInterests;

    private String comment = "";
    private String status = "NEW";

    public ZaswiadczenieDto(Long id, Long groupId, Long ownerId, Date studentInternshipStart, Date studentInternshipEnd) {
        this.id = id;
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.studentInternshipStart = studentInternshipStart;
        this.studentInternshipEnd = studentInternshipEnd;
    }

    @Override
    public String toString() {
        return "ZaswiadczenieDto{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", ownerId=" + ownerId +
                ", studentInternshipStart=" + studentInternshipStart +
                ", studentInternshipEnd=" + studentInternshipEnd +
                ", studentRating1='" + studentRating1 + '\'' +
                ", studentRating2='" + studentRating2 + '\'' +
                ", studentRating3='" + studentRating3 + '\'' +
                ", studentRating='" + studentRating + '\'' +
                ", studentWorks='" + studentWorks + '\'' +
                ", studentInterests='" + studentInterests + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
