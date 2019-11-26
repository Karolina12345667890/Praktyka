package uph.ii.SIMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.User;
import uph.ii.SIMS.UserModule.UserFacade;
import uph.ii.SIMS.UserModule.UserRepository;
import uph.ii.SIMS.UserModule.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class CustomTokenEnhancer implements TokenEnhancer {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        UserDto currentUser = userFacade.getCurrentUser();
        User user = (User) authentication.getUserAuthentication().getPrincipal();
        var authorities = user.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("organization", authentication.getName() + "test");
//        Collection<GrantedAuthority> authorities = authentication.getAuthorities();
        additionalInfo.put("roles", authorities);
        
//        additionalInfo.put("id_token ", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
