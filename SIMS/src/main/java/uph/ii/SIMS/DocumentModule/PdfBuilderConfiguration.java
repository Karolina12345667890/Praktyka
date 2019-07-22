package uph.ii.SIMS.DocumentModule;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;

class PdfBuilderConfiguration {
    
    private static final String FONTS_DIRECTORY = "src/main/resources/fonts/";
    
    PdfRendererBuilder config() {
        return new PdfRendererBuilder()
            .useFastMode()
            .useFont(new File(FONTS_DIRECTORY + "times.ttf"), "times")
            .useFont(new File(FONTS_DIRECTORY + "timesbd.ttf"), "timesbd")
            .useFont(new File(FONTS_DIRECTORY + "arial.ttf"), "arial")
            .useFont(new File(FONTS_DIRECTORY + "arialbd.ttf"), "arialbd");
    }
    
    
    SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }
    
    String getStaticResourcesLocation() {
        return new File("src/main/resources").toURI().toString();
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
