package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import togethers.togethers.entity.User;
import togethers.togethers.entity.MemberDetail;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.User;
import togethers.togethers.form.MemberDetailForm;
import togethers.togethers.form.replyForm;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }






    public void post_save(Post post)
    {
        em.persist(post);
    }



   //string reply가 돌아가야함  그래서 string reply가 replyform에 갔음
     public void reply(Reply reply){
        em.persist(reply);
    }

    public void Comment(replyForm reply) {
        em.persist(reply);
    }


//db한테 저장을 부탁한다는 문
    public void memberDetail(MemberDetail memberDetail) {
        em.persist(memberDetail);
    }
}
