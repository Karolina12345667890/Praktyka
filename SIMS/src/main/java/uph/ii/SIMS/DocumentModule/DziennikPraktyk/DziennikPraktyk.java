package uph.ii.SIMS.DocumentModule.DziennikPraktyk;

import lombok.*;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.Dto.DziennikPraktykDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "dziennikipraktyk")

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
public class DziennikPraktyk extends Document {

    public static final String DOCUMENT_TYPE = "dziennikpraktyk";

    private Date periodFrom;
    private Date periodTo;
    private String studentAlbumNumber;
    private String companyName;
    //@Lob
    @Column(length =  100000)
    private ArrayList<DiaryLog> diary = new ArrayList<DiaryLog>();

    DziennikPraktyk(Long owner) {
        super(owner);
    }

    @Override
    public String getType() {
        return DOCUMENT_TYPE;
    }

    DziennikPraktykDto DziennikPraktykDto() {
        return new DziennikPraktykDto(
                id,
                groupId,
                ownerId,
                periodFrom,
                periodTo,
                studentAlbumNumber,
                companyName,
                diary,
                comment,
                getStatusString()
        );
    }

    @Override
    public String toString() {
        return "DziennikPraktyk{" +
                "periodFrom=" + periodFrom +
                ", periodTo=" + periodTo +
                ", studentAlbumNumber='" + studentAlbumNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", diary=" + diary +
                '}';
    }
}

