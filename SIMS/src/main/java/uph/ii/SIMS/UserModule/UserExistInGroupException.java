package uph.ii.SIMS.UserModule;

public class UserExistInGroupException extends RuntimeException {
    public UserExistInGroupException (String message)
    {
        super(message);
    }
}
