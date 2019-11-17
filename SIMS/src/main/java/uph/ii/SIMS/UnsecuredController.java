package uph.ii.SIMS;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class UnsecuredController {
    
    @GetMapping
    public Response unsecuredResource() {
        return new Response("This is an unsecured resource");
    }
    
}
