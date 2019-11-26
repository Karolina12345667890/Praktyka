package uph.ii.SIMS.UserModule;

import uph.ii.SIMS.UserModule.Dto.*;

import java.util.List;

public class UserFacade {
    
    private UserService userService;
    private GroupService groupService;
    
    public UserFacade(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }
    
    
    public UserDto getCurrentUser() {
        User user = (User) userService.loadCurrentUser();
        return user.dto();
    }
    
    public Boolean currentUserIsAdmin(){
        return userService.currentUserIsAdmin();
    }
    
    public UserDto getUserById(Long id) {
        User user = userService.loadUserById(id);
        return user.dto();
    }
    
    public GroupDto getGroupById(Long id) {
        return groupService.getGroupById(id);
    }
    
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }
    
    public void persistGroup(GroupModifyDto dto) {
        groupService.persistGroup(dto);
    }
    
    public GroupWithStudentsDto getGroupByIdWithStudents(Long id) {
        return groupService.getGroupByIdWithStudents(id);
    }
    
    public List<GroupApplicationDto> getApplicationToGroup(Long groupId) {
        return groupService.groupApplications(groupId);
    }
    
    public void applyToGroup(Long groupId) {
        UserDto user = getCurrentUser();
        groupService.addGroupApplication(user.getId(), groupId);
    }
    
    public void addUserToGroup(Long groupId, Long studentId) throws Exception {
        groupService.addUserToGroup(groupId, studentId);
    }
    
    public void acceptGroupApplication(Long groupApplicationId) {
        groupService.acceptGroupApplication(groupApplicationId);
    }
    

}
