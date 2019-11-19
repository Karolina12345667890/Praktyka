package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.UserModule.Dto.*;

import java.util.List;

public class UserFacade {
    
    private UserService userService;
    private GroupService groupService;
    private DocumentFacade documentFacade;
    
    public UserFacade(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }
    
    @Autowired
    @Lazy
    public void setDocumentFacade(DocumentFacade documentFacade) {
        this.documentFacade = documentFacade;
    }
    
    
    public UserDto getCurrentUser() {
        User user = (User) userService.loadCurrentUser();
        return user.dto();
    }
    
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }
    
    public void persistGroup(GroupModifyDto dto){
        groupService.persistGroup(dto);
    }
    
    public GroupWithStudentsDto getGroupById(Long id) {
        return groupService.getGroupById(id);
    }
    
    public List<GroupApplicationDto> getApplicationToGroup(Long groupId) {
        return groupService.groupApplications(groupId);
    }
    
    public void applyToGroup(Long groupId){
        UserDto user = getCurrentUser();
        groupService.addGroupApplication(user.getId(), groupId);
    }
    
    public void addUserToGroup(Long groupId, Long studentId) throws Exception {
        groupService.addUserToGroup(groupId, studentId);
    }
    
    public void acceptGroupApplication(Long groupApplicationId){
        groupService.acceptGroupApplication(groupApplicationId);
    }
}
