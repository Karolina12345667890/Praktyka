package uph.ii.SIMS.FileSendingModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uph.ii.SIMS.AttributeEncryptor;

import javax.persistence.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Entity
@Table(name = "files")
public class FileData {

    @Id
    protected Long id;
    @Convert(converter = AttributeEncryptor.class)
    private String fileName;
    private String type;
    @Convert(converter = AttributeEncryptor.class)
    @Lob
    private String data;

    public FileData(Long docId, String fileName, String type, byte[] data) {
        this.id = docId;
        this.fileName = fileName;
        this.type = type;
        this.data = StandardCharsets.ISO_8859_1.decode(ByteBuffer.wrap(data))
                .toString();
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

        return StandardCharsets.ISO_8859_1.encode(data).array();
    }

    public void setData(byte[] data) {

        this.data = new String(data);
    }

}
