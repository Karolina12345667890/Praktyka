package uph.ii.SIMS.FileSendingModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uph.ii.SIMS.AttributeEncryptor;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "files")
public class FileData {

    @Id
    protected Long id;
    @Convert(converter = AttributeEncryptor.class)
    private String fileName;
    private String type;
    @Lob
    private byte[] data;

    public FileData(Long docId, String fileName, String type, byte[] data) {
        this.id = docId;
        this.fileName = fileName;
        this.type = type;
        this.data = data;
    }

    public FileData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", type='" + type + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
