package commerce.hello.domain.member;

/**
 * 고객의 정보를 관리하는 Customer 클래스
 */
public class Member {
    private long id;
    private String name;
    private String email;
    private Grade grade;

    public Member(String name, String email, Grade grade) {
        this.email = email;
        this.grade = grade;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
