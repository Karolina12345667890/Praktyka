package uph.ii.SIMS.PdfCreationService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.AllArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import uph.ii.SIMS.PdfCreationService.Dto.OswiadczeniePdfDto;
import uph.ii.SIMS.PdfCreationService.Dto.PorozumieniePdfDto;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@AllArgsConstructor
public class PdfBuilder {
    
    private final String STATIC_RESOURCES_URI;
    private PdfRendererBuilder rendererBuilder;
    private TemplateEngine templateEngine;
    
//    public byte[] getPdfFromJsonString(String templateName, String json) throws Exception {
//        Map<String, Object> dataToBind = processJson(json);
//        String processedHtml = processHtmlWithMap(templateName, dataToBind);
//        return renderPdf(processedHtml);
//    }
    
    public byte[] getPdfFromObject(String templateName, OswiadczeniePdfDto object) throws Exception {
        String processedHtml = processHtmlWithObject(templateName, object);
        return renderPdf(processedHtml);
    }
    
    public byte[] getPdfFromObject(String templateName, PorozumieniePdfDto object) throws Exception {
        String processedHtml = processHtmlWithObject(templateName, object);
        return renderPdf(processedHtml);
    }
    
    
//    private Map<String, Object> processJson(String json) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
//        };
//
//        return mapper.readValue(json, typeReference);
//    }
    
//    private String processHtmlWithMap(String templateName, Map<String, Object> dataToBind) {
//        Context context = new Context();
//        context.setVariable("dto", dataToBind);
//        return templateEngine.process(templateName, context);
//    }
    
    private String processHtmlWithObject(String templateName, Object dataToBind) {
        Context context = new Context();
        context.setVariable("dto", dataToBind);
        return templateEngine.process(templateName, context);
    }
    
    private byte[] renderPdf(String processedHtml) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            rendererBuilder.toStream(outputStream);
            rendererBuilder.withHtmlContent(processedHtml, STATIC_RESOURCES_URI);
            rendererBuilder.run();
            return outputStream.toByteArray();
        }
    }
    
    
}
