package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;

import javax.persistence.EntityManager;
import java.util.*;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    private static Map<String, Member> store = new HashMap<>();
    private static long sequence =0L;

    public void save(Member member){
        em.persist(member);
    }

    public Member findId(String id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream()
                .filter(m->m.getId().equals(loginId)).findFirst();

    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

    public void post_save(Post post)
    {
        em.persist(post);
    }




}
