package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import uph.ii.SIMS.UserModule.Dto.FormOfStudyEnum;
import uph.ii.SIMS.UserModule.Dto.UserDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @Autowired
    GroupApplicationRepository groupApplicationRepository;
    
    @Bean
    @Transactional
    public void init() throws Exception {
        roleRepository.deleteAll();
        Role role_user = new Role("ROLE_USER");
        Role role_admin = new Role("ROLE_ADMIN");
        Role role_group_admin = new Role("ROLE_GROUP_ADMIN");
        roleRepository.save(role_user);
        roleRepository.save(role_group_admin);
        roleRepository.save(role_admin);
        
        Group group = new Group("grupa1_19/20", 40, new Date());
        group.setFormOfStudy(FormOfStudyEnum.FULL_TIME);
        group.setFieldOfStudy("Informatyka");
        group.setSpeciality("Programowanie systemów i baz danych");
        group.setGroupAdminId((long) 1);
        group.setGroupAdminName("Andrzej");
        group.setGroupAdminSurname("Aaa");

        Group group2 = new Group("grupa2_19/20", 40, new Date());
        group2.setFormOfStudy(FormOfStudyEnum.FULL_TIME);
        group2.setFieldOfStudy("Informatyka");
        group2.setSpeciality("Programowanie systemów i baz danych");
        group2.setGroupAdminId((long) 3);
        group2.setGroupAdminName("gg2");
        group2.setGroupAdminSurname("Aaa2");

        groupRepository.save(group);
        groupRepository.save(group2);
        
        userRepository.deleteAll();
        List<Role> rolesAdmin = Arrays.asList(role_admin,role_group_admin, role_user);
        List<Role> rolesGAdmin = Arrays.asList(role_group_admin, role_user);
        List<Role> rolesUser = Arrays.asList(role_user);
        
        User user = userService.createNewUser(new UserDto(null, "332211", "Andrzej", "Aaa", "test@gmai.com"),
            "admin", "admin", rolesAdmin);
        User guser = userService.createNewUser(new UserDto(null, "g", "gg", "Aaa", "test7@gmai.com"),
                "gadmin", "admin", rolesGAdmin);
        User g2user = userService.createNewUser(new UserDto(null, "g2", "gg2", "Aaa2", "test8@gmai.com"),
                "gadmin2", "admin", rolesGAdmin);
        User user1 = userService.createNewUser(new UserDto(null, "221133", "Barbara", "Bbb", "test2@gmai.com"),
            "user1", "user", rolesUser);
        User user2 = userService.createNewUser(new UserDto(null, "54321", "Cezary", "Ccc", "test3@gmai.com"),
            "user2", "user", rolesUser);
        User user3 = userService.createNewUser(new UserDto(null, "12345", "Danuta", "Dddd", "test4@gmai.com"),
            "user3", "user", rolesUser);
        User user4 = userService.createNewUser(new UserDto(null, "536563", "eeeee", "eeee", "teffst5@gmai.com"),
            "user4", "user", rolesUser);
        User user5 = userService.createNewUser(new UserDto(null, "123213", "fffff", "fffff", "test455@gmai.com"),
            "user5", "user", rolesUser);
        
        groupService.addGroupApplication(user4.getId(), group.getId());
        groupService.addGroupApplication(user5.getId(), group.getId());
        
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
