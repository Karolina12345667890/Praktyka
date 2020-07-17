package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.Dto.UserNotFoundException;

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
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("invalid username or password"));
    }

    public User getCurrentUser() throws UsernameNotFoundException {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository
                .findUserByLogin(principal)
                .orElseThrow(
                        () -> new UserNotFoundException("User " + principal + " does not exist")
                );

        return userRepository.findUserByLogin(currentUser.getUsername()).get();
    }

    public User loadUserById(Long id) {
        return userRepository.getOne(id);
    }
    
    public UserDetails loadCurrentUser() throws UserNotFoundException {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository
            .findUserByLogin(principal)
            .orElseThrow(
                () -> new UserNotFoundException("User " + principal + " does not exist")
            );
        return loadUserByUsername(currentUser.getUsername());
    }
    
    public boolean currentUserIsAdmin(){
        UserDetails userDetails = loadCurrentUser();
        boolean isAdmin = false;
        for(GrantedAuthority role: userDetails.getAuthorities()){
            if ("ROLE_ADMIN".equals(role.getAuthority())) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }
    public boolean currentUserIsStudent(){
        UserDetails userDetails = loadCurrentUser();
        boolean isAdmin = false;
        boolean isUser = false;
        boolean isGroupAdmin = false;
        for(GrantedAuthority role: userDetails.getAuthorities()){
            switch (role.getAuthority()){
                case "ROLE_ADMIN": isAdmin = true;
                break;
                case "ROLE_GROUP_ADMIN": isGroupAdmin = true;
                break;
                case "ROLE_USER": isUser = true;
                    break;
            }
        }
        return isUser && !isAdmin && !isGroupAdmin ;
    }

    public boolean currentUserIsGroupAdmin(){
        UserDetails userDetails = loadCurrentUser();
        boolean isGroupAdmin = false;
        for(GrantedAuthority role: userDetails.getAuthorities()){
            if ("ROLE_GROUP_ADMIN".equals(role.getAuthority())) {
                isGroupAdmin = true;
            }
        }
        return isGroupAdmin;
    }
    
    public List<User> listUsersWithIdInList(List<Long> ids){
        return userRepository.findAllByIdIn(ids);
    }
    
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).get().dto();
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
