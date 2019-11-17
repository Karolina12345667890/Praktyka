package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void createNewUser(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        User user = new User();
        user.setLogin(username);
        user.setPassword(passwordEncoder.encode(password));
        
        System.out.println(user + " created!");
        
        userRepository.save(user);
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("invalid username or password"));
    }
    
    public UserDetails loadCurrentUser() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findUserByLogin(principal).orElseThrow(() -> new RuntimeException());
        return loadUserByUsername(currentUser.getUsername());
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
