package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.UserModule.Dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GroupController {
    @Autowired
    UserFacade userFacade;
    @Autowired
    DocumentFacade documentFacade;
    
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
    public void addGroup(@RequestBody GroupModifyDto dto){
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

    @PostMapping("/api/group/{groupId}/applications/decline/{appId}")
    public void declineApplication(@PathVariable Long groupId, @PathVariable Long appId){
        userFacade.declineGroupApplication(appId);
    }

    @PostMapping("/api/group/{groupId}/users/drop/{appId}")
    public void dropUserFromGroup(@PathVariable Long groupId, @PathVariable Long appId){
        userFacade.dropUserFromGroup(groupId,appId);
    }

    @PostMapping("/api/group/{groupId}/users/job/{appId}")
    public void changeUserDocuments(@PathVariable Long groupId, @PathVariable Long appId){
        userFacade.changeUserDocuments(groupId,appId);
    }

    @PostMapping("/api/group/summary/{groupId}")
    public byte[] summarizeGroup(@PathVariable Long groupId,@RequestBody Object docContent) throws Exception {
        Map<String, String> obj = (Map<String, String>) docContent;
       return userFacade.createPodsumowaniePdf(groupId,obj.get("text"));
    }

}
