package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import togethers.togethers.entity.Member;
import togethers.togethers.memberRepository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){
        return memberRepository.findByLoginId(loginId)
                .filter(m->m.getPassword().equals(password))
                .orElse(null);
//        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
//        Member member = findMemberOptional.get();
//        if(member.getPassword().equals(password)){
//            return member;
//        }else{
//            return null;
//        }
    }
}
