package uph.ii.SIMS.DocumentModule.Dto;

import lombok.*;
import uph.ii.SIMS.DocumentModule.DziennikPraktyk.DiaryLog;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DziennikPraktykDto {
    private Long id;
    private Long groupId;
    private Long ownerId;

    public static final String DOCUMENT_TYPE = "dziennikpraktyk";

    private Date periodFrom;
    private Date periodTo;
    private String studentAlbumNumber;
    private String companyName;
    @Lob
    private ArrayList<DiaryLog> diary;

    private String comment = "";
    private String status = "";

    public DziennikPraktykDto(Long id, Long groupId, Long ownerId) {
        this.id = id;
        this.groupId = groupId;
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "DziennikPraktykDto{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", ownerId=" + ownerId +
                ", periodFrom=" + periodFrom +
                ", periodTo=" + periodTo +
                ", studentAlbumNumber='" + studentAlbumNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", diary=" + diary +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

