package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.MemberDetail;
import togethers.togethers.entity.Post;
import togethers.togethers.form.MemberDetailForm;
import togethers.togethers.form.replyForm;
import togethers.togethers.memberRepository.MemberRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public String join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long post_write(Post post)
    {
        memberRepository.post_save(post);
        return post.getPost_id();
    }

    @Transactional
    public void Reply(replyForm reply){

        memberRepository.Comment(reply);
    }
    @Transactional
    public void memberDetail(MemberDetail memberDetail){
        memberRepository.memberDetail(memberDetail);
    }


}

