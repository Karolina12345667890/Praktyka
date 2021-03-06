package uph.ii.SIMS.UserModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.DocumentModule.Document;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.Dto.*;
import uph.ii.SIMS.UserModule.Dto.*;

import javax.persistence.Lob;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
        groupDto.forEach(group -> {
                    if (group.getIsOpen()) {
                        List<DocumentDto> documentDtos = documentFacade.listGroupDocuments(group.getId());
                        for (int i = 0; i < documentDtos.size(); i++) {
                            if (documentDtos.get(i).getStatus().equals(StatusEnum.NEW.toString())) {
                                group.setChanged(true);
                                break;
                            }
                        }
                        ;

                        if (groupApplicationRepository.getAllByGroupId(group.getId()).size() > 0) {
                            group.setChanged(true);
                        }
                    }
                }
        );


        Stream<GroupDto> groupDtoStream = groupDto
                .stream()
                .map(Group::dto);

        if (userService.currentUserIsStudent()) {
            groupDtoStream = groupDtoStream.filter(GroupDto::getIsOpen);
        }
        if (userService.currentUserIsGroupAdmin() && !userService.currentUserIsAdmin()) {
            groupDtoStream = groupDtoStream.filter(GroupDto -> GroupDto.getGroupAdminId().equals(userService.getCurrentUser().getId()));
        }


        return groupDtoStream.collect(Collectors.toList());
    }

    public GroupWithStudentsDto getGroupByIdWithStudents(Long id) {
        Group group = groupRepository
                .getOne(id);

        List<DocumentDto> documentDtos = documentFacade.listGroupDocuments(id);

        return new GroupWithStudentsDto(
                group.getId(),
                group.getGroupName(),
                group.getDurationInWeeks(),
                group.getUsers().stream().map(user -> new UserWithDocumentsDto(
                                user.getId(),
                                user.getAlbum(),
                                user.getName(),
                                user.getSurname(),
                                documentFacade.fetchCompanyNameByGroupIdAndOwnerId(group.getId(),user.getId()),
                                documentDtos.stream().filter(document -> document.getOwnerId().equals(user.getId()) && document.getVisible())
                                        .collect(Collectors.toList())
                        )
                ).collect(Collectors.toList()),
                group.getIsOpen(),
                group.getDateStart()
        );
    }


    int getGroupDurationFromId(Long id) {
        return groupRepository.getOne(id).getDurationInWeeks();
    }

    GroupDto getGroupById(Long id) {
        return groupRepository.getOne(id).dto();
    }


    public void addUserToGroup(Long groupId, Long studentId) {
        User user = userService.loadUserById(studentId);
        Group group = groupRepository.getOne(groupId);
        Collection<Group> groups = user.getGroups();
        AtomicBoolean czy = new AtomicBoolean(false);
        group.getUsers().forEach(stud -> {
            if (stud.getId() == studentId) {
                czy.set(true);
            }
        });
        if (!czy.get()) {
            groups.add(group);
            user.setGroups(groups);

            documentFacade.storeZaswiadczenieZatrudnienie(
                    new ZaswiadczenieZatrudnienieDto(null, groupId, studentId, new Date(), new Date()),
                    studentId,
                    groupId,
                    group.getGroupName(),
                    false
            );
            documentFacade.storeOswiadczenie(
                    new OswiadczenieDto(null, groupId, studentId),
                    studentId,
                    groupId,
                    group.getGroupName(),
                    true
            );
            documentFacade.storePorozumienie(
                    new PorozumienieDto(null, groupId, studentId, new Date(), new Date()),
                    studentId,
                    groupId,
                    group.getGroupName(),
                    true
            );
            documentFacade.storePlanPraktyki(
                    new PlanPraktykiDto(null, groupId, studentId, new Date(), new Date()),
                    studentId,
                    groupId,
                    group.getGroupName(),
                    false
            );
            documentFacade.storeDziennikPraktyk(
                    new DziennikPraktykDto(null, groupId, studentId),
                    studentId,
                    groupId,
                    group.getGroupName(),
                    false
            );
            documentFacade.storeZaswiadczenie(
                    new ZaswiadczenieDto(null, groupId, studentId, new Date(), new Date()),
                    studentId,
                    groupId,
                    group.getGroupName(),
                    false
            );


        } else {
            throw new GroupApplicationDuplicationException("Can't have multiples of the same student in a group");
        }
    }

    public void persistGroup(GroupModifyDto dto) {
        Group group;
        if (dto.getId() != null) {
            group = groupRepository.getOne(dto.getId());
        } else {
            group = new Group();
        }
        User currentUser = userService.getCurrentUser();
        group.setGroupName(dto.getGroupName());
        group.setDateStart(dto.getStartDate());
        group.setDurationInWeeks(dto.getDurationInWeeks());
        group.setIsOpen(dto.getIsOpen());
        group.setFormOfStudy(dto.getFormOfStudy());
        group.setFieldOfStudy(dto.getFieldOfStudy());
        group.setSpeciality(dto.getSpeciality());
        group.setGroupAdminId(currentUser.getId());
        group.setGroupAdminName(currentUser.getName());
        group.setGroupAdminSurname(currentUser.getSurname());
        group.setGroupAdminEmail(currentUser.getEmail());
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

    public void dropUserFromGroup(Long groupId, Long studentId) {

        if (userService.currentUserIsAdmin()) {
            Optional<Group> group = groupRepository.findById(groupId);

            group.ifPresent(app -> {
                app.getUsers().removeIf(user -> user.getId().equals(studentId));
                groupRepository.save(app);
            });
            userService.removeGroup(groupId, studentId);
            documentFacade.deleteStudentsDocumentsInGroup(groupId, studentId);
        }

    }

    public void changeUserDocuments(Long groupId, Long studentId) {
        documentFacade.chengeUsersDocuments(groupId, studentId);

    }

    public byte[] createPodsumowaniePdf(Long groupId, String docContent) throws Exception {
        GroupDto group = getGroupById(groupId);
        return documentFacade.printPodsumowanieGrupyToPdf(groupId,docContent,group.getGroupAdminName(),group.getGroupAdminSurname());
    }


   public List<GroupSummaryDto> summarizeGroup(Long groupId){
       if(userService.currentUserIsAdmin()) {
           Map<String, Integer> groupSummaryMap = new HashMap<String, Integer>();
           List<GroupSummaryDto> groupSummaryDtoList = new ArrayList<>();
       Group group = groupRepository.findById(groupId).get();
        group.getUsers().forEach( stud -> {
           GroupSummaryDto groupSummaryDto = documentFacade.sumarizeDocuments(stud.getId(),groupId);
           if(groupSummaryMap.containsKey(groupSummaryDto.getCompanyInfo())){
               groupSummaryMap.put(groupSummaryDto.getCompanyInfo(),groupSummaryMap.get(groupSummaryDto.getCompanyInfo()) + 1);
           }else{
               groupSummaryMap.put(groupSummaryDto.getCompanyInfo(),groupSummaryDto.getNumberOfStudents());
           }


        });
           groupSummaryMap.forEach( (key,val) -> {
               if(!key.equals("")) {
                   groupSummaryDtoList.add(new GroupSummaryDto(key, val));
               }
           });
           return groupSummaryDtoList;
       }
       return null;

   }
}
