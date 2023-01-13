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

    public void saveMember(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
    }

     private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findById(member.getId());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
     }

    @Transactional
    public Long post_write(Post post)
    {
        memberRepository.post_save(post);
        return post.getPost_id();
    }


}

