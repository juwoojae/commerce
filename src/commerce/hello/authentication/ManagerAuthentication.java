package commerce.hello.authentication;


public class ManagerAuthentication implements Authentication {
    private static final String MANAGER_PASSWORD = "1q2w3e4r!@";
    @Override
    public boolean authenticate(String password) {
        return MANAGER_PASSWORD.equals(password);
    }
}