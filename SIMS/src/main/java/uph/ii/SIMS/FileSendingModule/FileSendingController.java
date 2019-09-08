package uph.ii.SIMS.FileSendingModule;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uph.ii.SIMS.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Complete refactor - create FileSendingFacade, move all the logic from the controller etc.
@RestController
public class FileSendingController {
    
    @GetMapping("/files")
    public List<FileInfoDTO> presentFileList() {
        File directory = new File("./");
        
        return Arrays.stream(directory.listFiles())
            .map(f -> new FileInfoDTO(f.getName(), f.length(), "test"))
            .collect(Collectors.toList());
        
        
    }
    
    @GetMapping(
        value = "file/{filename}",
        produces = {MediaType.IMAGE_JPEG_VALUE}
    )
    public @ResponseBody
    byte[] downloadFileWithGet(@PathVariable String filename) throws IOException {
        
        
        File directory = new File("./");
        
        System.out.println(filename);
        File file =
            Arrays.stream(directory.listFiles()).
                filter(f -> f.getName().contains(filename))
                .findFirst()
                .orElse(null);
        
        
        FileInputStream fileInputStream = new FileInputStream(file);
        
        return fileInputStream.readAllBytes();
        
    }
    
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public Response uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        
        String originalFilename = file.getOriginalFilename();
        try (FileOutputStream fileOutputStream = new FileOutputStream(originalFilename)) {
            fileOutputStream.write(file.getBytes());
        }
        
        return new Response("success");
    }
    
}
