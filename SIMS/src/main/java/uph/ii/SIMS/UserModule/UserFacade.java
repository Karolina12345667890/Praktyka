package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.UserModule.Dto.GroupDto;
import uph.ii.SIMS.UserModule.Dto.GroupModifyDto;
import uph.ii.SIMS.UserModule.Dto.GroupWithStudentsDto;
import uph.ii.SIMS.UserModule.Dto.UserDto;

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
}
