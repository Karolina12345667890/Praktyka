package uph.ii.SIMS.FileSendingModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.UserModule.UserFacade;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class FileDataService {

    @Autowired
    private FileDBRepository fileDBRepository;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    DocumentFacade documentFacade;


    public ResponseEntity<ResponseMessage> uploadFile(Long docId, MultipartFile file) {
        String message = "";
        try {
            if (userFacade.getCurrentUser().getId().equals(documentFacade.getDocumentOwnerIdByDocumentId(docId)) || userFacade.currentUserIsAdmin()) {
                if (!fileDBRepository.existsById(docId)) {
                    String fileNewName = documentFacade.getDocumentTypeByDocumentId(docId) + "_" + userFacade.getCurrentUser().getName() + "_" + userFacade.getCurrentUser().getSurname() + "_" + documentFacade.getDocumentGroupNameByDocumentId(docId);
                    String[] split = StringUtils.cleanPath(file.getOriginalFilename()).split("\\.");
                    String newFileName = fileNewName + "." + split[split.length - 1];
                    FileData File = new FileData(docId, newFileName, file.getContentType(), file.getBytes());
                    System.out.println(File);

                    fileDBRepository.saveAndFlush(File);
                } else {
                    throw new AccessDeniedException("File already in database!");
                }
                documentFacade.setDocumentStatusDone(docId);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } else
                throw new AccessDeniedException("You dont have access to this document");
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity<byte[]> getFile(Long id) {
        if (userFacade.currentUserIsGroupAdmin()) {
            FileData fileDB = fileDBRepository.findById(id).get();

            HttpHeaders respHeaders = new HttpHeaders();

            String filename = fileDB.getFileName();
            String[] split = filename.split("\\.");

            respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=file" + split[split.length - 1]);
            respHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            return new ResponseEntity<byte[]>(fileDB.getData(), respHeaders, HttpStatus.OK);

//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
//                    .body(fileDB.getData());

        }
        throw new AccessDeniedException("You dont have access to this document");


    }

    public void removeFile(Long id) {
        if (userFacade.currentUserIsGroupAdmin()) {
            if (fileDBRepository.existsById(id)) {
                documentFacade.setDocumentStatusAccepted(id);
                fileDBRepository.deleteById(id);
            } else
                throw new AccessDeniedException("Document not in database");
        } else
            throw new AccessDeniedException("You dont have access to this document");

    }

    //public Stream<FileData> getAllFiles() {
    //   return fileDBRepository.findAll().stream();
    //}
}
