package uph.ii.SIMS.DocumentModule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.AllArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@AllArgsConstructor
class PdfBuilder {
    
    private final String STATIC_RESOURCES_URI;
    private PdfRendererBuilder rendererBuilder;
    private TemplateEngine templateEngine;
    
    byte[] getPdf(String templateName, String json) throws Exception {
        var dataToBind = processJson(json);
        var processedHtml = processHtml(templateName, dataToBind);
        return renderPdf(processedHtml);
    }
    
    private Map<String, Object> processJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
        };
        
        return mapper.readValue(json, typeReference);
    }
    
    private String processHtml(String templateName, Map<String, Object> dataToBind) {
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
