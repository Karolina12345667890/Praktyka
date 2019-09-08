package uph.ii.SIMS.PdfCreationService;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;

@Configuration
/**
 * <p>
 *     Klasa odpowiedzialna za utworzenie skonfigurowanej instancji klasy {@link PdfBuilder}
 * </p>
 * <p>
 *     Posiada konfigurację czcionek, oraz ustawienia resolvera widoków
 * </p>
 */
class PdfBuilderConfiguration {
    
    private static final String FONTS_DIRECTORY = "src/main/resources/fonts/";
    
    @Bean
    /**
     * @return Skonfigurowana instancja {@link PdfBuilder}
     */
    PdfBuilder pdfBuilder() {
        return new PdfBuilder(staticResourcesUri(), config(), templateEngine());
    }
    
    /**
     * Zwraca ścieżkę do folderu z zasobami
     * @return ścieżka do folderu z zasobami
     */
    private String staticResourcesUri() {
        return new File("src/main/resources").toURI().toString();
    }
    
    /**
     * Zwraca skonfigurowany PdfRendererBuilder (z biblioteki openhtmltopdf)
     * @return skonfigurowany PdfRendererBuilder
     */
    private PdfRendererBuilder config() {
        return new PdfRendererBuilder()
            .useFastMode()
            .useFont(new File(FONTS_DIRECTORY + "times.ttf"), "times")
            .useFont(new File(FONTS_DIRECTORY + "timesbd.ttf"), "timesbd")
            .useFont(new File(FONTS_DIRECTORY + "arial.ttf"), "arial")
            .useFont(new File(FONTS_DIRECTORY + "arialbd.ttf"), "arialbd");
    }
    
    /**
     * Zwraca template engine z ustawionym resolverem widokow z {@link #templateResolver()}
     * @return skonfigurowany template engine
     */
    private SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }
    
    /**
     * Zwraca skonfigurowany template resolver
     * @return skonfigurowany template resolver
     */
    private ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);
        return templateResolver;
    }
    
    
}
