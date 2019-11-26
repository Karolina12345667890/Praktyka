package uph.ii.SIMS.UserModule.Dto;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
