package uph.ii.SIMS;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/home")
    public Response home(){
        return new Response("Hello!");
    }
    
}
