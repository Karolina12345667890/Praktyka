package uph.ii.SIMS;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class SecuredController {
    
    @GetMapping
    public Response securedResource(Authentication auth) {
        return new Response(
            "This is a SECURED resource. Authentication: " + auth.getName() + "; Authorities: " + auth.getAuthorities()
        );
        
    }
    
}
