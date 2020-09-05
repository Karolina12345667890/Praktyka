package uph.ii.SIMS.DocumentModule.AnkietaPracodawcy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uph.ii.SIMS.DocumentModule.AnkietaStudenta.AnkietaStudenta;
import uph.ii.SIMS.DocumentModule.DocumentFacade;
import uph.ii.SIMS.DocumentModule.DocumentRepository;
import uph.ii.SIMS.DocumentModule.Dto.*;
import uph.ii.SIMS.UserModule.Dto.UserDto;
import uph.ii.SIMS.UserModule.Group;
import uph.ii.SIMS.UserModule.GroupRepository;
import uph.ii.SIMS.UserModule.UserExistInGroupException;
import uph.ii.SIMS.UserModule.UserFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class AnkietaPracownikService {

    @Autowired
    private AnkietaPracownikRepository ankietaPracownikRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired @Lazy
    private DocumentFacade documentFacade;
    @Autowired
    private UserFacade userFacade;

    /**
     * Metoda odpowedzialna za sprawdzenie czy dana ankieta istnieje lub czy użytkownik znajduje się w danej grupie
     * @param ankietaPracownikDto
     * @param studentId
     * @param groupId
     */
    public void createNewAnkietaPracownik(AnkietaPracownikDto ankietaPracownikDto, Long studentId, Long groupId)
    {
        if(ankietaPracownikRepository.existsByGroupIdAndOwnerId(groupId,studentId))
        {
            throw new AnkietaDuplicateException("Ankieta already exists");
        }
        else if(!documentRepository.existsByGroupIdAndOwnerId(groupId, studentId))
        {
            throw new UserExistInGroupException("User is not exists in this group");
        }
        else
        {
            createNewAnkietaPracownikAndAddToDocument(ankietaPracownikDto,studentId,groupId);
        }
    }

    /**
     * Metoda odpowiedzialna za dodanie ankiety do Documents
     * @param ankietaPracownikDto
     * @param studentId
     * @param groupId
     */
    public void createNewAnkietaPracownikAndAddToDocument(AnkietaPracownikDto ankietaPracownikDto, Long studentId, Long groupId)
    {
        Group group = groupRepository.getOne(groupId);

        documentFacade.storePracownikAnkieta(
                ankietaPracownikDto,
                studentId,
                groupId,
                group.getGroupName()
        );

    }

    /**
     * Metoda odpowiedzialna za utworzenie i dodanie do bazy danych ankiety pracownika
     * @param ankietaPracownikDto
     * @param studentId
     * @param groupId
     * @param groupName
     * @return
     */
    public AnkietaPracownik createAnkietaPracownik(AnkietaPracownikDto ankietaPracownikDto, Long studentId, Long groupId, String groupName)
    {
        AnkietaPracownik ankietaPracownik = new AnkietaPracownik(studentId);

        ankietaPracownik.setGroupId(groupId);
        ankietaPracownik.setGroupName(groupName);
        ankietaPracownik.setOwnerId(studentId);
        ankietaPracownik.setNumberOfStudent(ankietaPracownikDto.getNumberOfStudent());
        ankietaPracownik.setCompanyInfo(ankietaPracownikDto.getCompanyInfo());
        ankietaPracownik.setAnswerTo(ankietaPracownikDto.getAnswerTo());
        ankietaPracownik.setAnswerTo1(ankietaPracownikDto.getAnswerTo1());
        ankietaPracownik.setAnswerTo2(ankietaPracownikDto.getAnswerTo2());
        ankietaPracownik.setAnswerTo3(ankietaPracownikDto.getAnswerTo3());
        ankietaPracownik.setAnswerTo4(ankietaPracownikDto.getAnswerTo4());
        ankietaPracownik.setAnswerTo5(ankietaPracownikDto.getAnswerTo5());
        ankietaPracownik.setAnswerTo6(ankietaPracownikDto.getAnswerTo6());
        ankietaPracownik.setAnswerTo7(ankietaPracownikDto.getAnswerTo7());
        ankietaPracownik.setAnswerTo8(ankietaPracownikDto.getAnswerTo8());
        ankietaPracownik.setAnswerTo9(ankietaPracownikDto.getAnswerTo9());
        ankietaPracownik.setAnswerTo10(ankietaPracownikDto.getAnswerTo10());
        ankietaPracownik.setAnswerTo11(ankietaPracownikDto.getAnswerTo11());
        ankietaPracownik.setAnswerTo12(ankietaPracownikDto.getAnswerTo12());
        ankietaPracownik.setAnswerTo13(ankietaPracownikDto.getAnswerTo13());
        ankietaPracownik.setAnswerTo14(ankietaPracownikDto.getAnswerTo14());
        ankietaPracownik.setAnswerTo15(ankietaPracownikDto.getAnswerTo15());
        ankietaPracownik.setAnswerTo15text(ankietaPracownikDto.getAnswerTo15text());
        ankietaPracownik.setAnswerTo16text(ankietaPracownikDto.getAnswerTo16text());
        ankietaPracownik.setStatus(StatusEnum.DONE);
        ankietaPracownik.setVisible(true);

        try{
           return ankietaPracownikRepository.save(ankietaPracownik);
        }
        catch (Error error)
        {
            throw error;
        }
    }

    /**
     * Metoda odpowiedzialna za sprawdzenie czy użytkownik ma uprawnienia do wyświetlenia podsumowania ankiet pracownika
     * @param groupId
     * @return
     */
    public HashMap<Integer, List<Object>> makeSummary(Long groupId)
    {
        UserDto user = userFacade.getCurrentUser();
        Group group = groupRepository.getOne(groupId);
        if(!userFacade.currentUserIsGroupAdmin() || !user.getId().equals(group.getGroupAdminId()))
        {
            throw new AccessDeniedException("Nie masz uprawnień do tych danych");
        }
        else {
            return ankieta(groupId);
        }
    }

    /**
     * Metoda odpowiedzialna za zmianę z obiektu typu AnkietaPracownik na listę odpowiedzi jakie udzielił (a,b,c,d,e)
     * @param list
     * @return
     */
    private HashMap<Integer,List<String>> changeObjectToList(List<AnkietaPracownik> list)
    {
        HashMap<Integer, List<String>> map = new HashMap<Integer, List<String>>();
        List<String> answer;
        for(int i = 0; i < list.size(); i++)
        {
            answer = new ArrayList<>();
            answer.add(list.get(i).getAnswerTo());
            answer.add(list.get(i).getAnswerTo1());
            answer.add(list.get(i).getAnswerTo2());
            answer.add(list.get(i).getAnswerTo3());
            answer.add(list.get(i).getAnswerTo4());
            answer.add(list.get(i).getAnswerTo5());
            answer.add(list.get(i).getAnswerTo6());
            answer.add(list.get(i).getAnswerTo7());
            answer.add(list.get(i).getAnswerTo8());
            answer.add(list.get(i).getAnswerTo9());
            answer.add(list.get(i).getAnswerTo10());
            answer.add(list.get(i).getAnswerTo11());
            answer.add(list.get(i).getAnswerTo12());
            answer.add(list.get(i).getAnswerTo13());
            answer.add(list.get(i).getAnswerTo14());
            answer.add(list.get(i).getAnswerTo15());

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
            add(0);
        }});
        structureMap.put(1,new ArrayList<>()
        {{add(0);
            add(0);
            add(0);
            add(0);
            add(0);
        }});

        for(int i = 1; i <= 8; i++)
        {
            structureMap.put(i,new ArrayList<>()
            {{add(0);
                add(0);
                add(0);
                add(0);
                add(0);
            }});
        }
        for(int i = 9; i <= 15; i++)
        {
            structureMap.put(i,new ArrayList<>()
            {{add(0);
                add(0);
                add(0);
                add(0);
            }});
        }
        return structureMap;
    }

    /**
     * Metoda odpowiedzialana za utworzenie listy z uwagami zawartymi w ankietach pracownik
     * @param list
     * @return
     */
    public List<Object> findAllComments(List<AnkietaPracownik> list)
    {
        List<Object> commentsList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++)
        {
            if(!list.get(i).getAnswerTo16text().equals("....") & !list.get(i).getAnswerTo16text().equals(""))
            {
                commentsList.add(list.get(i).getAnswerTo16text());
            }
        }
        return commentsList;
    }

    /**
     * Metoda odpowiedzialana za utworzenie podsumowania ankiet pracownika
     * @param id
     * @return
     */
    public HashMap<Integer, List<Object>> ankieta(Long id)
    {
        List<AnkietaPracownik> allForm =  ankietaPracownikRepository.findAllByGroupId(id);
        HashMap<Integer, List<String>> allFormMap = changeObjectToList(allForm);
        HashMap<Integer, List<Object>> answerMap = createStructure();
        List<Object> commentList = findAllComments(allForm);

        int a= 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;

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
                else if(allFormMap.get(i).get(j).equals("e"))
                {
                    e = Integer.parseInt(answerMap.get(j).get(4).toString());
                    e++;
                    answerMap.get(j).set(4,e);
                }
            }
        }
        answerMap.put(17,commentList);

        return answerMap;
    }

    /**
     * Metoda sprawdzająca czy użytkownik ma uprawnienia do podejrzenia danych ankiety i zwrócenie konkretnej ankiety
     * @param id - id ankiety
     * @return
     */
    public AnkietaPracownik getAnkieta(Long id)
    {
        UserDto user = userFacade.getCurrentUser();
        AnkietaPracownik ankietaPracownik = ankietaPracownikRepository.findById(id);
        Group group = groupRepository.getOne(ankietaPracownik.getGroupId());

        if(!userFacade.currentUserIsGroupAdmin() || !user.getId().equals(group.getGroupAdminId()))
        {
            throw new AccessDeniedException("Nie masz uprawnień do tych danych");
        }
        else {
            return ankietaPracownikRepository.findById(id);
        }
    }
}
