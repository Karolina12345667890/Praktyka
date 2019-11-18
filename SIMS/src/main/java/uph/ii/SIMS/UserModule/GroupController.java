package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uph.ii.SIMS.Response;
import uph.ii.SIMS.UserModule.Dto.GroupDto;
import uph.ii.SIMS.UserModule.Dto.GroupModifyDto;
import uph.ii.SIMS.UserModule.Dto.GroupWithStudentsDto;

import java.util.List;
import java.util.Optional;

@RestController
public class GroupController {
    @Autowired
    UserFacade userFacade;
    
    @GetMapping("/api/groups")
    public List<GroupDto> a(){
       return userFacade.getAllGroups();
    }
    
    @GetMapping("/api/group/{id}")
    public GroupWithStudentsDto ab(@PathVariable Long id){
        return userFacade.getGroupById(id);
    }
    
//    @PostMapping("/api/group/{id}")
//    public void modifyGroup(@PathVariable Long id, @RequestBody GroupModifyDto dto){
//        userFacade.persistGroup(dto);
//    }
    
    @PostMapping("/api/groups")
    public void modifyGroup(@RequestBody GroupModifyDto dto){
        userFacade.persistGroup(dto);
    }
    
}
