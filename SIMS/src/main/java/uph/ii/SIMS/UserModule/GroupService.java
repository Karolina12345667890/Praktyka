package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.DocumentDto;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Dto.PorozumienieDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;
import uph.ii.SIMS.UserModule.Dto.*;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GroupService {
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
        List<Group> groupDto = groupRepository
            .findAll();
        groupDto.forEach(group-> {
                    if (group.getIsOpen()) {
                        List<DocumentDto> documentDtos = documentFacade.listGroupDocuments(group.getId());
                        for (int i = 0; i < documentDtos.size(); i++) {
                           if(documentDtos.get(i).getStatus().equals(StatusEnum.NEW.toString())){
                           group.setChanged(true);
                           break;
                        }
                        };
                    }
            if(groupApplicationRepository.getAllByGroupId(group.getId()).size() > 0){
                group.setChanged(true);
            }

                }
        );



        Stream<GroupDto> groupDtoStream = groupDto
                .stream()
                .map(Group::dto);

        if (userService.currentUserIsStudent()) {
            groupDtoStream = groupDtoStream.filter(GroupDto::getIsOpen);
        }

        if(userService.currentUserIsGroupAdmin()){
            groupDtoStream = groupDtoStream.filter(GroupDto -> GroupDto.getGroupAdminId().equals(userService.getCurrentUser().getId()));
        }


        return groupDtoStream.collect(Collectors.toList());
    }
    
    public GroupWithStudentsDto getGroupByIdWithStudents(Long id) {
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
    
    
    
    int getGroupDurationFromId(Long id){
        return groupRepository.getOne(id).getDurationInWeeks();
    }
    
    GroupDto getGroupById(Long id){
        return groupRepository.getOne(id).dto();
    }
    
    
    public void addUserToGroup(Long groupId, Long studentId) {
        User user = userService.loadUserById(studentId);
        Group group = groupRepository.getOne(groupId);
        Collection<Group> groups = user.getGroups();
        groups.add(group);
        user.setGroups(groups);
        
        documentFacade.storeOswiadczenie(
            new OswiadczenieDto(null, groupId, studentId),
            studentId,
            groupId,
                group.getGroupName()
        );
        documentFacade.storePorozumienie(
            new PorozumienieDto(null, groupId, studentId, new Date(), new Date()),
            studentId,
            groupId,
                group.getGroupName()
        );
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
        group.setFormOfStudy(dto.getFormOfStudy());
        group.setFieldOfStudy(dto.getFieldOfStudy());
        group.setSpeciality(dto.getSpeciality());
        group.setGroupAdminId(userService.getCurrentUser().getId());
        group.setGroupAdminName(userService.getCurrentUser().getName());
        group.setGroupAdminSurname(userService.getCurrentUser().getSurname());
        groupRepository.save(group);
    }
    
    public List<GroupApplicationDto> groupApplications(Long groupId) {
        List<GroupApplication> applicationsByGroupId = groupApplicationRepository.getAllByGroupId(groupId);
        List<Long> userIdList = applicationsByGroupId.stream()
            .map(GroupApplication::getStudentId)
            .distinct()
            .collect(Collectors.toList());
        
        Map<Long, User> usersApplying = userService.listUsersWithIdInList(userIdList).stream()
            .collect(Collectors.toMap(
                User::getId,
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
                        "/api/group/" + e.getGroupId() + "/applications/decline/" + e.getId(),
                    e.getDate()))
            .collect(Collectors.toList());
    }
    
    public void addGroupApplication(Long studentId, Long groupId) {
        boolean applicationAlreadyPresent = groupApplicationRepository.getByStudentIdAndGroupId(studentId, groupId).isPresent();
        boolean studentAlreadyPresentInTheGroup = groupRepository.findById(groupId)
            .get()
            .getUsers()
            .stream()
            .anyMatch(s -> s.getId().equals(studentId));
        if (applicationAlreadyPresent || studentAlreadyPresentInTheGroup) {
            throw new GroupApplicationDuplicationException("Can't have multiples of the same student in a group");
        }
        
        GroupApplication groupApplication = new GroupApplication(null, groupId, studentId, new Date());
        groupApplicationRepository.save(groupApplication);
    }
    
    public void acceptGroupApplication(Long groupApplicationId) {
        Optional<GroupApplication> groupApplication = groupApplicationRepository.findById(groupApplicationId);
        groupApplication.ifPresent(
            app -> {
                addUserToGroup(app.getGroupId(), app.getStudentId());
                groupApplicationRepository.delete(app);
            }
        );
        
    }

    public void declineGroupApplication(Long groupApplicationId) {
        Optional<GroupApplication> groupApplication = groupApplicationRepository.findById(groupApplicationId);
        groupApplication.ifPresent(
                app -> {
                    groupApplicationRepository.delete(app);
                }
        );

    }
    
    
}
