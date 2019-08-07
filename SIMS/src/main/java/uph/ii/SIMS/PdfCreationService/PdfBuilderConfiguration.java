package uph.ii.SIMS.PdfCreationService;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;

@Configuration
class PdfBuilderConfiguration {
    
    private static final String FONTS_DIRECTORY = "src/main/resources/fonts/";
    
    @Bean
    PdfBuilder pdfBuilder() {
        String static_resources_uri = new File("src/main/resources").toURI().toString();
        return new PdfBuilder(static_resources_uri, config(), templateEngine());
    }
    
    private PdfRendererBuilder config() {
        return new PdfRendererBuilder()
            .useFastMode()
            .useFont(new File(FONTS_DIRECTORY + "times.ttf"), "times")
            .useFont(new File(FONTS_DIRECTORY + "timesbd.ttf"), "timesbd")
            .useFont(new File(FONTS_DIRECTORY + "arial.ttf"), "arial")
            .useFont(new File(FONTS_DIRECTORY + "arialbd.ttf"), "arialbd");
    }
    
    
    private SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }
    
    
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
