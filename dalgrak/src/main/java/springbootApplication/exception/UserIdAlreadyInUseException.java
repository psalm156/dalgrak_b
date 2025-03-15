package springbootApplication.exception;

public class UserIdAlreadyInUseException extends RuntimeException {
    public UserIdAlreadyInUseException(String message) {
        super(message);
    }
}
