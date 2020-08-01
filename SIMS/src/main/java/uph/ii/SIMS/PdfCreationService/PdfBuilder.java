package uph.ii.SIMS.PdfCreationService;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.AllArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import uph.ii.SIMS.DocumentModule.DziennikPraktyk.DziennikPraktyk;
import uph.ii.SIMS.PdfCreationService.Dto.DziennikPraktykPdfDto;
import uph.ii.SIMS.PdfCreationService.Dto.OswiadczeniePdfDto;
import uph.ii.SIMS.PdfCreationService.Dto.PorozumieniePdfDto;
import uph.ii.SIMS.PdfCreationService.Dto.ZaswiadczeniePdfDto;

import java.io.ByteArrayOutputStream;

/**
 * Klasa tworząca dokument pdf na podstawie danego szablonu thymeleaf i danych do zapełnienia brakujących pól
 */
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
    
    
    /**
     * Tworzy dokument pdf na podstawie szablonu i danych
     *
     * @param templateName nazwa szablonu
     * @param pdfDto       dane, którymi zostanie wypełniony szablon
     * @return
     * @throws Exception
     */
    //TODO Klasa zawierajaca nazwy wszystkich uzywanych szablonow i uzywanie stalych, a nie "golych" stringow
    public byte[] getPdfFromObject(String templateName, OswiadczeniePdfDto pdfDto) throws Exception {
        String processedHtml = processHtmlWithObject(templateName, pdfDto);
        return renderPdf(processedHtml);
    }
    
    /**
     * Tworzy dokument pdf na podstawie szablonu i danych
     *
     * @param templateName nazwa szablonu
     * @param pdfDto       dane, którymi zostanie wypełniony szablon
     * @return
     * @throws Exception
     */
    //TODO Klasa zawierajaca nazwy wszystkich uzywanych szablonow i uzywanie stalych, a nie "golych" stringow
    public byte[] getPdfFromObject(String templateName, PorozumieniePdfDto pdfDto) throws Exception {
        String processedHtml = processHtmlWithObject(templateName, pdfDto);
        System.out.println(pdfDto.toString());
        return renderPdf(processedHtml);
    }

    /**
     * Tworzy dokument pdf na podstawie szablonu i danych
     *
     * @param templateName nazwa szablonu
     * @param pdfDto       dane, którymi zostanie wypełniony szablon
     * @return
     * @throws Exception
     */
    //TODO Klasa zawierajaca nazwy wszystkich uzywanych szablonow i uzywanie stalych, a nie "golych" stringow
    public byte[] getPdfFromObject(String templateName, ZaswiadczeniePdfDto pdfDto) throws Exception {
        String processedHtml = processHtmlWithObject(templateName, pdfDto);
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
    /**
     * Tworzy dokument pdf na podstawie szablonu i danych
     *
     * @param templateName nazwa szablonu
     * @param pdfDto       dane, którymi zostanie wypełniony szablon
     * @return
     * @throws Exception
     */
    //TODO Klasa zawierajaca nazwy wszystkich uzywanych szablonow i uzywanie stalych, a nie "golych" stringow
    public byte[] getPdfFromObject(String templateName, DziennikPraktykPdfDto pdfDto) throws Exception {
        String processedHtml = processHtmlWithObject(templateName, pdfDto);
        return renderPdf(processedHtml);
    }
    /**
     * Przetwarza szablon na czysty html, uzupelnia wszystkie luki danymi z przekazanego dto
     *
     * @param templateName nazwa szablonu, który ma zostać użyty
     * @param dataToBind   dto z potrzebnymi danymi
     * @return
     */
    private String processHtmlWithObject(String templateName, Object dataToBind) {
        Context context = new Context();
        context.setVariable("dto", dataToBind);
        return templateEngine.process(templateName, context);
    }
    
    /**
     * Tworzy dokument pdf z czystego html (uzyskanego z {@link #renderPdf(String)}
     *
     * @param processedHtml czysty html do zamiany na pdf
     * @return dokument pdf w postaci tablicy bajtów
     * @throws Exception
     */
    private byte[] renderPdf(String processedHtml) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            rendererBuilder.toStream(outputStream);
            rendererBuilder.withHtmlContent(processedHtml, STATIC_RESOURCES_URI);
            rendererBuilder.run();
            return outputStream.toByteArray();
        }
    }
    
    
}
