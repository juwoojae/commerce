package commerce.hello.exception;

public class InvalidateCmdException extends RuntimeException {
    public InvalidateCmdException(String message) {
        super(message);
    }
}
