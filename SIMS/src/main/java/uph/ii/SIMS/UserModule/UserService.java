package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
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
        User user = new User();
        user.setLogin(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setAlbum(userDto.getAlbum());

        //System.out.println(user + " created!");

        try {
            userRepository.save(user);
        } catch (Error error) {
            throw error;
        }
        return user;
    }

    public User createNewStudentUser(UserDto userDto, String username, String password) {
        Role role_user = roleRepository.findByName("ROLE_USER");
            if(userRepository.existsByLogin(username)){
                throw new UserDuplicateLoginException("Login już zajęty!") ;
            }else if(userRepository.existsByEmail(userDto.getEmail())){
                throw new UserDuplicateEmailException("Email już zajęty!") ;
            } else {
               return createNewUser(userDto, username, password, List.of(role_user));
            }
    }

    public User createNewGroupAdminUser(UserDto userDto, String username, String password) {

        if (currentUserIsAdmin()) {
            Role role_user = roleRepository.findByName("ROLE_USER");
            Role role_group_admin = roleRepository.findByName("ROLE_GROUP_ADMIN");
            if(userRepository.existsByLogin(username)){
                throw new UserDuplicateLoginException("Login już zajęty!") ;
            }else if(userRepository.existsByEmail(userDto.getEmail())){
                throw new UserDuplicateEmailException("Email już zajęty!") ;
            } else {
                return createNewUser(userDto, username, password, List.of(role_user,role_group_admin));
            }
        }
        throw new AccessDeniedException("Access Denied") ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Zły login lub hasło!"));
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

    public boolean currentUserIsAdmin() {
        UserDetails userDetails = loadCurrentUser();
        boolean isAdmin = false;
        for (GrantedAuthority role : userDetails.getAuthorities()) {
            if ("ROLE_ADMIN".equals(role.getAuthority())) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    public boolean currentUserIsStudent() {
        UserDetails userDetails = loadCurrentUser();
        boolean isAdmin = false;
        boolean isUser = false;
        boolean isGroupAdmin = false;
        for (GrantedAuthority role : userDetails.getAuthorities()) {
            switch (role.getAuthority()) {
                case "ROLE_ADMIN":
                    isAdmin = true;
                    break;
                case "ROLE_GROUP_ADMIN":
                    isGroupAdmin = true;
                    break;
                case "ROLE_USER":
                    isUser = true;
                    break;
            }
        }
        return isUser && !isAdmin && !isGroupAdmin;
    }

    public boolean currentUserIsGroupAdmin() {
        UserDetails userDetails = loadCurrentUser();
        boolean isGroupAdmin = false;
        for (GrantedAuthority role : userDetails.getAuthorities()) {
            if ("ROLE_GROUP_ADMIN".equals(role.getAuthority())) {
                isGroupAdmin = true;
            }
        }
        return isGroupAdmin;
    }

    public List<User> listUsersWithIdInList(List<Long> ids) {
        return userRepository.findAllByIdIn(ids);
    }

    public UserDto getUserById(Long id) {
        if (currentUserIsGroupAdmin() || getCurrentUser().getId().equals(id))
            return userRepository.findById(id).get().dto();
        else
            return new UserDto();
    }

    public void removeGroup(Long groupId, Long studentId) {
        if (currentUserIsGroupAdmin()) {
            userRepository.findById(studentId).ifPresent(app -> {
                app.getGroups().removeIf(group -> group.getId().equals(groupId));
                userRepository.save(app);
            });
        }
    }

    public void editUsersName(String name) {
        try {
            User user = getCurrentUser();
            user.setName(name);
            userRepository.save(user);
        } catch (Error error) {
            throw error;
        }
    }

    public void editUsersSurname(String surname) {
        try {
            User user = getCurrentUser();
            user.setSurname(surname);
            userRepository.save(user);
        } catch (Error error) {
            throw error;
        }
    }

    public void editUsersAlbum(String album) {
        try {
            User user = getCurrentUser();
            user.setAlbum(album);
            userRepository.save(user);
        } catch (Error error) {
            throw error;
        }
    }

    public void editUsersEmail(String email) {
        try {
            User user = getCurrentUser();
            user.setEmail(email);
            userRepository.save(user);
        } catch (Error error) {
            throw error;
        }
    }

    public void editUsersPass(String pass) {
        try {
            User user = getCurrentUser();
            user.setPassword(pass);
            userRepository.save(user);
        } catch (Error error) {
            throw error;
        }
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
