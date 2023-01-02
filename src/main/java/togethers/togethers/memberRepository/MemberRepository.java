package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import togethers.togethers.controller.MemberForm;
import togethers.togethers.domain.Member;

import javax.persistence.EntityManager;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    private static Map<String, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public void save(Member member){
        em.persist(member);
    }

   public Member findId(String id){
         return store.get(id);

    }

    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream().filter(m -> m.getLoginId().equals(loginId)).findFirst();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }


}
