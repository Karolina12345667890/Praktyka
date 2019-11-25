package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.UserModule.Dto.GroupApplicationDto;
import uph.ii.SIMS.UserModule.Dto.GroupDto;
import uph.ii.SIMS.UserModule.Dto.GroupModifyDto;
import uph.ii.SIMS.UserModule.Dto.GroupWithStudentsDto;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    UserFacade userFacade;
    
    @GetMapping("/api/groups")
    public List<GroupDto> getAllGroups(){
       return userFacade.getAllGroups();
    }
    
    @GetMapping("/api/group/{id}")
    public GroupWithStudentsDto getGroupDetailWithStudents(@PathVariable Long id){
        return userFacade.getGroupByIdWithStudents(id);
    }
    
    @GetMapping("/api/group/{id}/overview")
    public GroupDto getGroupById(@PathVariable Long id){
        return userFacade.getGroupById(id);
    }
    
    @PostMapping("/api/group/{id}")
    public void modifyGroup(@PathVariable Long id, @RequestBody GroupModifyDto dto){
        userFacade.persistGroup(dto);
    }
    
    @PostMapping("/api/groups")
    public void modifyGroup(@RequestBody GroupModifyDto dto){
        userFacade.persistGroup(dto);
    }
    
    @GetMapping("/api/group/{id}/applications")
    public List<GroupApplicationDto> getApplicationToGroup(@PathVariable Long id) {
       return userFacade.getApplicationToGroup(id);
    }
    
    @PostMapping("/api/group/{id}/applications/new")
    public void applyToGroup(@PathVariable Long id){
        userFacade.applyToGroup(id);
    }
    
    @PostMapping("/api/group/{groupId}/applications/{appId}")
    public void acceptApplication(@PathVariable Long groupId, @PathVariable Long appId){
        userFacade.acceptGroupApplication(appId);
    }
}
