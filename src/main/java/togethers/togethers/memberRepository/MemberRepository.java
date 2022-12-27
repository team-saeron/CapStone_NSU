package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import togethers.togethers.controller.MemberForm;
import togethers.togethers.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

   public Member findId(String id){
         return em.find(Member.class, id);

    }


}
