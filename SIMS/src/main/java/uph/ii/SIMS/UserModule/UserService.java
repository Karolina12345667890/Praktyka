package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.UserModule.Dto.UserDto;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User createNewUser(UserDto userDto, String username, String password, List<Role> roles) {
        System.out.println(username);
        System.out.println(password);
        User user = new User();
        user.setLogin(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setAlbum(userDto.getAlbum());
        
        System.out.println(user + " created!");
        
        userRepository.save(user);
        return user;
    }
    
    public User createNewUser(UserDto userDto, String username, String password) {
       return createNewUser(userDto, username, password, Arrays.asList(roleRepository.findByName("ROLE_USER")));
    }
    
    
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("invalid username or password"));
    }
    
    public User loadUserById(Long id) {
        return userRepository.getOne(id);
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
