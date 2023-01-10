package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;
import togethers.togethers.memberRepository.MemberRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public String join(Member member){

        memberRepository.save(member);
        return member.getId();
    }



//    public Member login(String loginId, String password){
//        return memberRepository.findByLoginId(loginId)
//                .filter(m->m.getPassword().equals(password))
//                .orElse(null);
//    }

    @Transactional
    public Long post_write(Post post)
    {
        memberRepository.post_save(post);
        return post.getPost_id();
    }


}

