package uph.ii.SIMS.UserModule;

public class UserDuplicateLoginException extends RuntimeException {
    public  UserDuplicateLoginException(String message) {
        super(message);
    }
}

