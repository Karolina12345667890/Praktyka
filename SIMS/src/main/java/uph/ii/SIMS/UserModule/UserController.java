package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Controller
@Configuration
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/register")
    String registerPage(){
        return "register";
    }

    @GetMapping("/login")
    String loginPage(Model model){
        return "login";
    }

//    @PostMapping("/register")
//    String register(){
//
//    }

    @Bean
    public ClassLoaderTemplateResolver loginRegisterTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);
        return templateResolver;
    }

}
