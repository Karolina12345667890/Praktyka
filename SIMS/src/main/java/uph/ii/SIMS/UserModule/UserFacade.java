package uph.ii.SIMS.UserModule;

import lombok.AllArgsConstructor;
import uph.ii.SIMS.UserModule.Dto.UserDto;


/**
 * Klasa zawiera wszystkie metody związane z użytkownikami. Odpowiada za komunikację z innymi modułami
 */
//TODO Spring security
@AllArgsConstructor
public class UserFacade {
    
    private UserRepository userRepository;
    
    /**
     * Zwraca dane aktualnie zalogowanego użytkownika
     *
     * @return dane aktualnie zalogowanego użytkownika
     * @throws Exception
     */
    //TODO Obsluga błędów
    public UserDto getCurrentUser() throws Exception {
        return new UserDto(1L, "Maciej", "Nazarczuk", "abc@gmail.com");


//        return userRepository.getUserById(1L)
//            .map(user -> user.dto())
//            .orElseThrow(() -> new Exception());
    }
    
}
