package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.controller.MemberForm;
import togethers.togethers.domain.Member;
import togethers.togethers.memberRepository.MemberRepository;

import java.util.List;


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


}

