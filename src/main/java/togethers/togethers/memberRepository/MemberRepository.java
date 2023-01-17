package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.RoomPicture;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public String save (Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOne(String id){
        return em.find(Member.class, id);
    }




    public void post_save(Post post, RoomPicture roomPicture) {
        em.persist(post);
        em.persist(roomPicture);
    }

    public Post findPost(Long postid) //detailPost와 연관되는 메소드
    {
        return em.find(Post.class,postid);
    }

    public void Member_Post(Post post,String memberid)
    {
        Member member = em.find(Member.class,memberid);
        member.setPost(post);
        em.persist(member);
    }










}
