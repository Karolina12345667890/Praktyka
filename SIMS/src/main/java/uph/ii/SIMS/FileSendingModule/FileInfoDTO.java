package uph.ii.SIMS.FileSendingModule;

public class FileInfoDTO {
    private String fileName;
    private Long size;
    private String fileExtension;
    
    public FileInfoDTO(String fileName, Long size, String fileExtension) {
        this.fileName = fileName;
        this.size = size;
        this.fileExtension = fileExtension;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public Long getSize() {
        return size;
    }
    
    public String getFileExtension() {
        return fileExtension;
    }
}
