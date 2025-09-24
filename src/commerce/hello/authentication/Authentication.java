package commerce.hello.authentication;

/**
 * 관리자의 비밀번호를 검증
 */
public interface Authentication {
    boolean authenticate(String password);
}
