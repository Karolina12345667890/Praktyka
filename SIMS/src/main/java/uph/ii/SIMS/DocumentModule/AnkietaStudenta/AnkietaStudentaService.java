package uph.ii.SIMS.DocumentModule.AnkietaStudenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.DocumentRepository;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaDuplicateException;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaStudentaDto;
import uph.ii.SIMS.DocumentModule.Dto.StatusEnum;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.Group;
import uph.ii.SIMS.UserModule.GroupRepository;
import uph.ii.SIMS.UserModule.UserExistInGroupException;
import uph.ii.SIMS.UserModule.UserFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class AnkietaStudentaService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentFacade documentFacade;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private AnkietaStudentaRepository ankietaStudentaRepository;
    @Autowired
    private UserFacade userFacade;

    /**
     * Metoda odpowiedzialna za sprawdzenie czy dana ankieta istnieje i czy użytkownik ma dostęp do danej grupy
     * @param ankietaStudentaDto
     * @param studentId
     * @param groupId
     */
    public void createNewAnkietaStudenta(AnkietaStudentaDto ankietaStudentaDto, Long studentId, Long groupId)
    {
        if(ankietaStudentaRepository.existsByGroupIdAndOwnerId(groupId,studentId))
        {
            throw new AnkietaDuplicateException("Ankieta already exists");
        }
        else if(!documentRepository.existsByGroupIdAndOwnerId(groupId, studentId))
        {
            throw new UserExistInGroupException("User is not exists in this group");
        }
        else
        {
             createNewAnkietaStudentaAndAddToDocument(ankietaStudentaDto,studentId,groupId);
        }
    }

    /**
     * Metoda odpowiedzialna za utworzenie i zapisanie do bazy danych ankiety studenta
     * @param ankietaStudentaDto
     * @param studentId
     * @param groupId
     * @param groupName
     * @return
     */
    public AnkietaStudenta createAnkietaStudenta(AnkietaStudentaDto ankietaStudentaDto, Long studentId, Long groupId, String groupName)
    {
        AnkietaStudenta ankietaStudenta = new AnkietaStudenta(studentId);

        ankietaStudenta.setGroupId(groupId);
        ankietaStudenta.setGroupName(groupName);
        ankietaStudenta.setOwnerId(studentId);
        ankietaStudenta.setStudentSurname(ankietaStudentaDto.getStudentSurname());
        ankietaStudenta.setStudentName(ankietaStudentaDto.getStudentName());
        ankietaStudenta.setStudentSpecialization(ankietaStudentaDto.getStudentSpecialization());
        ankietaStudenta.setInstytutionType(ankietaStudentaDto.getInstytutionType());
        ankietaStudenta.setCompanyNameAndLocation(ankietaStudentaDto.getCompanyNameAndLocation());
        ankietaStudenta.setStudentInternshipStart(ankietaStudentaDto.getStudentInternshipStart());
        ankietaStudenta.setStudentInternshipEnd(ankietaStudentaDto.getStudentInternshipEnd());
        ankietaStudenta.setAnswerTo1(ankietaStudentaDto.getAnswerTo1());
        ankietaStudenta.setAnswerTo2(ankietaStudentaDto.getAnswerTo2());
        ankietaStudenta.setAnswerTo3(ankietaStudentaDto.getAnswerTo3());
        ankietaStudenta.setAnswerTo4(ankietaStudentaDto.getAnswerTo4());
        ankietaStudenta.setAnswerTo5(ankietaStudentaDto.getAnswerTo5());
        ankietaStudenta.setAnswerTo5atext(ankietaStudentaDto.getAnswerTo5atext());
        ankietaStudenta.setAnswerTo6(ankietaStudentaDto.getAnswerTo6());
        ankietaStudenta.setAnswerTo7(ankietaStudentaDto.getAnswerTo7());
        ankietaStudenta.setAnswerTo7atext(ankietaStudentaDto.getAnswerTo7atext());
        ankietaStudenta.setAnswerTo8(ankietaStudentaDto.getAnswerTo8());
        ankietaStudenta.setAnswerTo91(ankietaStudentaDto.getAnswerTo91());
        ankietaStudenta.setAnswerTo92(ankietaStudentaDto.getAnswerTo92());
        ankietaStudenta.setAnswerTo93(ankietaStudentaDto.getAnswerTo93());
        ankietaStudenta.setAnswerTo10(ankietaStudentaDto.getAnswerTo10());
        ankietaStudenta.setAnswerTo11(ankietaStudentaDto.getAnswerTo11());
        ankietaStudenta.setAnswerTo11text(ankietaStudentaDto.getAnswerTo11text());
        ankietaStudenta.setAnswerTo12(ankietaStudentaDto.getAnswerTo12());
        ankietaStudenta.setAnswerTo12atext(ankietaStudentaDto.getAnswerTo12atext());
        ankietaStudenta.setAnswerTo12btext(ankietaStudentaDto.getAnswerTo12btext());
        ankietaStudenta.setAnswerTo13(ankietaStudentaDto.getAnswerTo13());
        ankietaStudenta.setAnswerTo13text(ankietaStudentaDto.getAnswerTo13text());
        ankietaStudenta.setAnswerTo14text(ankietaStudentaDto.getAnswerTo14text());
        ankietaStudenta.setStatus(StatusEnum.NEW);
        ankietaStudenta.setVisible(false);

        try{
           return ankietaStudentaRepository.save(ankietaStudenta);
        }
        catch (Error error)
        {
            throw error;
        }
    }

    /**
     * Metoda jest odpowiedzialna za dodanie ankiety do Documents
     * @param ankietaStudentaDto
     * @param studentId
     * @param groupId
     */
    public void createNewAnkietaStudentaAndAddToDocument(AnkietaStudentaDto ankietaStudentaDto, Long studentId, Long groupId)
    {
        Group group = groupRepository.getOne(groupId);

        documentFacade.storeAnkietaStudenta(
                ankietaStudentaDto,
                studentId,
                groupId,
                group.getGroupName()
        );

    }

    /**
     * Metoda odpowiedzialna za zmianę z obiektu typu AnkietaPracownik na listę odpowiedzi jakie udzielił (a,b,c,d,e)
     * @param list
     * @return
     */
    public HashMap<Integer, List<String>> changeObjectToList(List<AnkietaStudenta> list)
    {
        HashMap<Integer, List<String>> map = new HashMap<Integer, List<String>>();
        List<String> answer;
        for(int i = 0; i < list.size(); i++)
        {
            answer = new ArrayList<>();
            answer.add(list.get(i).getAnswerTo1());
            answer.add(list.get(i).getAnswerTo2());
            answer.add(list.get(i).getAnswerTo3());
            answer.add(list.get(i).getAnswerTo4());
            answer.add(list.get(i).getAnswerTo5());
            answer.add(list.get(i).getAnswerTo6());
            answer.add(list.get(i).getAnswerTo7());
            answer.add(list.get(i).getAnswerTo8());
            answer.add(list.get(i).getAnswerTo91());
            answer.add(list.get(i).getAnswerTo92());
            answer.add(list.get(i).getAnswerTo93());
            answer.add(list.get(i).getAnswerTo10());
            answer.add(list.get(i).getAnswerTo11());
            answer.add(list.get(i).getAnswerTo12());
            answer.add(list.get(i).getAnswerTo13());

            map.put(i,answer);
        }
        return map;
    }

    /**
     * Metoda odpowiedzialana za utworzenie struktury HashMap do której będzie zapisywana ilość wystąpień danego wariantu odpowiedzi
     * @return
     */
    public HashMap<Integer, List<Object>> createStructure()
    {
        HashMap<Integer, List<Object>> structureMap = new HashMap<>();

        structureMap.put(0,new ArrayList<>()
        {{add(0);
            add(0);
        }});
        structureMap.put(1,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
        }});
        structureMap.put(2,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
            add(0);
        }});
        structureMap.put(3,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
        }});
        structureMap.put(4,new ArrayList<>()
        {{add(0);
            add(0);
        }});
        structureMap.put(5,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
            add(0);
        }});
        structureMap.put(6,new ArrayList<>()
        {{add(0);
            add(0);
        }});
        structureMap.put(7,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
        }});
        structureMap.put(8,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
            add(0);
        }});
        structureMap.put(9,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
            add(0);
        }});
        structureMap.put(10,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
            add(0);
        }});
        structureMap.put(11,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
        }});
        structureMap.put(12,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
            add(0);
        }});
        structureMap.put(13,new ArrayList<>()
        {{add(0);
            add(0);
        }});
        structureMap.put(14,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
        }});

        return structureMap;
    }

    /**
     * Metoda odpowiedzialana za utworzenie listy z uwagami zawartymi w ankietach pracownik
     * @param list
     * @return
     */
    public List<Object> findAllComments(List<AnkietaStudenta> list)
    {
        List<Object> commentsList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++)
        {
            if(!list.get(i).getAnswerTo14text().equals("....") & !list.get(i).getAnswerTo14text().equals(""))
            {
                commentsList.add(list.get(i).getAnswerTo14text());
            }
        }
        return commentsList;
    }

    /**
     * Metoda odpowiedzialna za sprawdzenie czy użytkownik ma uprawnienia do wyświetlenia podsumowania ankiet studenta
     * @param groupId
     * @return
     */
    public HashMap<Integer, List<Object>> makeSummary( Long groupId)
    {
        UserDto user = userFacade.getCurrentUser();
        Group group = groupRepository.getOne(groupId);
        if(!userFacade.currentUserIsGroupAdmin() || !user.getId().equals(group.getGroupAdminId()))
        {
            throw new AccessDeniedException("Nie masz uprawnień do tych danych");
        }
        else {
            return ankiety(groupId);
        }
    }

    /**
     * Metoda odpowiedzialana za utworzenie podsumowania ankiet studenta
     * @param id
     * @return
     */
    public HashMap<Integer, List<Object>> ankiety(Long id)
    {
        List<AnkietaStudenta> allForm =  ankietaStudentaRepository.findAllByGroupId(id);
        List<Object> commentList = findAllComments(allForm);
        HashMap<Integer, List<String>> allFormMap = changeObjectToList(allForm);
        HashMap<Integer, List<Object>> answerMap = createStructure();

        int a= 0;
        int b = 0;
        int c = 0;
        int d = 0;

        for(int i = 0; i < allFormMap.size(); i++)
        {
            for(int j = 0; j < allFormMap.get(i).size(); j++)
            {
                if(allFormMap.get(i).get(j).equals("a"))
                {
                    a = Integer.parseInt(answerMap.get(j).get(0).toString());
                    a++;
                    answerMap.get(j).set(0,a);
                }
                else if(allFormMap.get(i).get(j).equals("b"))
                {
                    b = Integer.parseInt(answerMap.get(j).get(1).toString());
                    b++;
                    answerMap.get(j).set(1,b);
                }
                else if(allFormMap.get(i).get(j).equals("c"))
                {
                    c = Integer.parseInt(answerMap.get(j).get(2).toString());
                    c++;
                    answerMap.get(j).set(2,c);
                }
                else if(allFormMap.get(i).get(j).equals("d"))
                {
                    d = Integer.parseInt(answerMap.get(j).get(3).toString());
                    d++;
                    answerMap.get(j).set(3,d);
                }
            }
        }

        answerMap.put(15,commentList);

        return answerMap;
    }
}
