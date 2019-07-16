package uph.ii.SIMS.DocumentModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
class DocumentController {

    private DocumentFacade documentFacade;
    
    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody byte[] createPdf() throws Exception{
        
        @Language("Json")
        String oswiadczenieJson = "{\n" +
            "  \"studentName\": \"nameTest\",\n" +
            "  \"studentSurname\": \"surnameTest\",\n" +
            "  \"studentDuties\": [\"test duty no. 1\", \"test duty no. 2\"],\n" +
            "  \"carerName\": \"carer Test Name\",\n" +
            "  \"carerSurname\": \"carer Test Surname\",\n" +
            "  \"carerPhone\": \"123456789\",\n" +
            "  \"carerEmail\": \"test123@test.com\"\n" +
            "}";
      
        return documentFacade.createPdf("Oswiadczenie", oswiadczenieJson);
    }
    
    @PostMapping(value = "/pdfPost", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody byte[] createPdfPost(@RequestParam String data) throws Exception{
        return documentFacade.createPdf("Oswiadczenie", data);
    }
    
    
}
