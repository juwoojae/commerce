package commerce.hello.domain.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {
    /**
     * 회원 가입 관리
     * (과제내용에 없음)
     */
    private static final Map<Long, Member> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public void save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
