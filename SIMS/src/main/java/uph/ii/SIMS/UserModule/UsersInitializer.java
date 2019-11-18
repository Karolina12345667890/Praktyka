package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import uph.ii.SIMS.UserModule.Dto.UserDto;

import java.util.Arrays;
import java.util.Date;

@Configuration
public class UsersInitializer {
    
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupService groupService;
    
    @Bean
    @Transactional
    public void init() throws Exception {
        roleRepository.deleteAll();
        Role role_user = new Role("ROLE_USER");
        Role role_admin = new Role("ROLE_ADMIN");
        roleRepository.save(role_user);
        roleRepository.save(role_admin);
        
        Group group = new Group("grupa1_19/20", 40, new Date());
        Group group2 = new Group("grupa2_19/20", 40, new Date());
        groupRepository.save(group);
        groupRepository.save(group2);
        
        userRepository.deleteAll();
        User user = userService.createNewUser(new UserDto(null, "332211", "Andrzej", "Aaa", "test@gmai.com"),
            "admin", "admin", Arrays.asList(role_admin, role_user));
        User user1 = userService.createNewUser(new UserDto(null, "221133", "Barbara", "Bbb", "test2@gmai.com"),
            "user1", "user");
        User user2 = userService.createNewUser(new UserDto(null, "54321", "Cezary", "Ccc", "test3@gmai.com"),
            "user2", "user");
        User user3 = userService.createNewUser(new UserDto(null, "12345", "Danuta", "Dddd", "test4@gmai.com"),
            "user3", "user");
        
        groupService.addUserToGroup(group.getId(), user1.getId());
        groupService.addUserToGroup(group.getId(), user2.getId());
        groupService.addUserToGroup(group.getId(), user3.getId());
        
        groupService.addUserToGroup(group2.getId(), user1.getId());
        groupService.addUserToGroup(group2.getId(), user2.getId());

//        user1.setGroups(Arrays.asList(group));
//        user2.setGroups(Arrays.asList(group));
//        user3.setGroups(Arrays.asList(group));
//
//        user2.setGroups(Arrays.asList(group2));
//        user3.setGroups(Arrays.asList(group2));
        
    }
    
}
