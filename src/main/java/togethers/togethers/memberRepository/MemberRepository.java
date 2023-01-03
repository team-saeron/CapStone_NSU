package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;

import javax.persistence.EntityManager;

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






    public void post_save(Post post)
    {
        em.persist(post);

    }


    public void test_save(Object ob)
    {
       Member m1 = em.find(Member.class,member1.getId());
       Member m2 = em.getReference(Member.class,member1.getId());

    }




}
