package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import togethers.togethers.controller.MemberForm;
import togethers.togethers.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }


}
