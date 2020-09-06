package uph.ii.SIMS.FileSendingModule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.Response;
import uph.ii.SIMS.UserModule.UserFacade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class FileSendingController {

    @Autowired
    private FileDataService fileDataService;


    @GetMapping(value = "file/{docId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long docId) {
        return fileDataService.getFile(docId);
    }

    @PostMapping(value = "/upload/{docId}")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable Long docId, @RequestParam("file") MultipartFile file) throws IOException {
    return fileDataService.uploadFile(docId,file);
    }
    @PostMapping(value = "/delete/{docId}")public void deleteFile(@PathVariable Long docId)  {
        fileDataService.removeFile(docId);

    }


}
