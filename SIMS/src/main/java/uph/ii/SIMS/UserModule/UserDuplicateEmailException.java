package uph.ii.SIMS.UserModule;

public class UserDuplicateEmailException extends RuntimeException {
    public  UserDuplicateEmailException(String message) {
        super(message);
    }
}
