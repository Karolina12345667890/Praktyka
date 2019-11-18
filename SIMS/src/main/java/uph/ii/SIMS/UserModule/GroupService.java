package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.UserModule.Dto.GroupDto;
import uph.ii.SIMS.UserModule.Dto.GroupModifyDto;
import uph.ii.SIMS.UserModule.Dto.GroupWithStudentsDto;
import uph.ii.SIMS.UserModule.Dto.UserWithDocumentsDto;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    EntityManager entityManager;
    private GroupRepository groupRepository;
    private UserService userService;
    private DocumentFacade documentFacade;
    
    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }
    
    @Autowired
    @Lazy
    public void setDocumentFacade(DocumentFacade documentFacade) {
        this.documentFacade = documentFacade;
    }
    
    public List<GroupDto> getAllGroups() {
        List<GroupDto> groupList = groupRepository
            .findAll()
            .stream()
            .map(group -> new GroupDto(
                group.getId(),
                group.getGroupName(),
                group.getDurationInWeeks(),
                group.getIsOpen(),
                group.getDateStart(),
                "api/group/" + group.getId()
            ))
            .collect(Collectors.toList());
        
        return groupList;
    }
    
    public GroupWithStudentsDto getGroupById(Long id) {
        Group group = groupRepository
            .getOne(id);
    
        List<DocumentDto> documentDtos = documentFacade.listGroupDocuments(id);
    
        GroupWithStudentsDto groupWithStudentsDto = new GroupWithStudentsDto(
            group.getId(),
            group.getGroupName(),
            group.getDurationInWeeks(),
            group.getUsers().stream().map(user -> new UserWithDocumentsDto(
                    user.getId(),
                    user.getAlbum(),
                    user.getName(),
                    user.getSurname(),
                    documentDtos.stream().filter(document -> document.getOwnerId().equals(user.getId()))
                    .collect(Collectors.toList())
                )
            ).collect(Collectors.toList()),
            group.getIsOpen(),
            group.getDateStart()
        );
    
        return groupWithStudentsDto;
    }
    
    
    
    public void addUserToGroup(Long groupId, Long studentId) throws Exception {
        User user = userService.loadUserById(studentId);
        Group group = groupRepository.getOne(groupId);
        Collection<Group> groups = user.getGroups();
        groups.add(group);
        user.setGroups(groups);
    
        documentFacade.storeOswiadczenie(new OswiadczenieDto(null, "", "", "", "", "", ""), studentId, groupId);
        documentFacade.storePorozumienie(new PorozumienieDto(null, "", ""), studentId, groupId);
    }
    
    public void persistGroup(GroupModifyDto dto){
        Group group;
        if(dto.getId() != null){
            group = groupRepository.getOne(dto.getId());
        } else {
            group = new Group();
        }
        group.setGroupName(dto.getGroupName());
        group.setDateStart(dto.getStartDate());
        group.setDurationInWeeks(dto.getDurationInWeeks());
        group.setIsOpen(dto.getIsOpen());
        groupRepository.save(group);
    }
    
    
}
