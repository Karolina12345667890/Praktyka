package uph.ii.SIMS.DocumentModule;

import lombok.AllArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class DocumentController {
    
    private DocumentFacade documentFacade;
    
    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createPdf() throws Exception {
        
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
    
    @GetMapping(value = "/pdf2", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    byte[] createPdf2() throws Exception {
        return documentFacade.createPdf("Porozumienie", "{}");
    }
    
}
