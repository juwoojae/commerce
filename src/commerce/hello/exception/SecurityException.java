package commerce.hello.exception;

/**
 * 관리자가 패스워드를 틀렸을때 접근을 막기위해서 던지는 예외
 */
public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}
