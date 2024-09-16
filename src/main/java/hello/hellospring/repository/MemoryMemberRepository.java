package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>(); // save 할때 메모리니까 저장을 해야함
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member); // key-value
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // 값이 null이여도 감싸서 반환 가능 -> 클라이언트
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // values에 대한 루프를 돈다.
                .filter(member -> member.getName().equals(name)) // filter : member가 파라미터로 넘어온 애랑 같으면
                .findAny(); // 하나라도 있으면 그걸로 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
