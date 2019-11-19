package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.UserModule.Dto.*;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GroupService {
    @Autowired
    EntityManager entityManager;
    private GroupRepository groupRepository;
    private UserService userService;
    private GroupApplicationRepository groupApplicationRepository;
    private DocumentFacade documentFacade;
    
    public GroupService(GroupRepository groupRepository, UserService userService, GroupApplicationRepository groupApplicationRepository) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.groupApplicationRepository = groupApplicationRepository;
    }
    
    @Autowired
    @Lazy
    public void setDocumentFacade(DocumentFacade documentFacade) {
        this.documentFacade = documentFacade;
    }
    
    public List<GroupDto> getAllGroups() {
        Stream<GroupDto> groupDtoStream = groupRepository
            .findAll()
            .stream()
            .map(group -> new GroupDto(
                group.getId(),
                group.getGroupName(),
                group.getDurationInWeeks(),
                group.getIsOpen(),
                group.getDateStart(),
                "/api/group/" + group.getId()
            ));
        
        if(userService.currentUserIsStudent()){
            groupDtoStream = groupDtoStream.filter(groupDto -> groupDto.getIsOpen());
        }
        
        return groupDtoStream.collect(Collectors.toList());
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
    
    
    public void addUserToGroup(Long groupId, Long studentId)  {
        User user = userService.loadUserById(studentId);
        Group group = groupRepository.getOne(groupId);
        Collection<Group> groups = user.getGroups();
        groups.add(group);
        user.setGroups(groups);
        
        documentFacade.storeOswiadczenie(new OswiadczenieDto(null, "", "", "", "", "", ""), studentId, groupId);
        documentFacade.storePorozumienie(new PorozumienieDto(null, "", ""), studentId, groupId);
    }
    
    public void persistGroup(GroupModifyDto dto) {
        Group group;
        if (dto.getId() != null) {
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
    
    public List<GroupApplicationDto> groupApplications(Long groupId) {
        List<GroupApplication> applicationsByGroupId = groupApplicationRepository.getAllByGroupId(groupId);
        List<Long> userIdList = applicationsByGroupId.stream()
            .map(e -> e.getStudentId())
            .distinct()
            .collect(Collectors.toList());
        
        Map<Long, User> usersApplying = userService.listUsersWithIdInList(userIdList).stream()
            .collect(Collectors.toMap(
                user -> user.getId(),
                user -> user));
    
        return applicationsByGroupId
            .stream()
            .map(e ->
                new GroupApplicationDto(
                    usersApplying.get(e.getStudentId()).getName(),
                    usersApplying.get(e.getStudentId()).getSurname(),
                    usersApplying.get(e.getStudentId()).getAlbum(),
                    e.getId(),
                    "/api/group/" + e.getGroupId() + "/applications/" + e.getId(),
                    e.getDate()))
            .collect(Collectors.toList());
    }
    
    public void addGroupApplication(Long studentId, Long groupId) {
        boolean alreadyPresent = groupApplicationRepository.getByStudentIdAndGroupId(studentId, groupId).isPresent();
        if(alreadyPresent){
            throw new GroupApplicationDuplicationException("Exception");
        }
    
        GroupApplication groupApplication = new GroupApplication(null, groupId, studentId, new Date());
        groupApplicationRepository.save(groupApplication);
    }
    
    public void acceptGroupApplication(Long groupApplicationId) {
        Optional<GroupApplication> groupApplication = groupApplicationRepository.findById(groupApplicationId);
        groupApplication.ifPresent(
            app ->  {
                addUserToGroup(app.getGroupId(), app.getStudentId());
                groupApplicationRepository.delete(app);
            }
        );
        
    }
    
    
}
