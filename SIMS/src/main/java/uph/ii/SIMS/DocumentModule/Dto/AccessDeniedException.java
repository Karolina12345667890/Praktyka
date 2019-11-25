package uph.ii.SIMS.DocumentModule.Dto;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
