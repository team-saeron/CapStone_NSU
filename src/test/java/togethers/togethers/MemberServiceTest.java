package togethers.togethers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import togethers.togethers.entity.Member;
import togethers.togethers.form.MemberForm;
import togethers.togethers.service.MemberService;

import javax.transaction.Transactional;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberForm memberForm = MemberForm.builder()
                .id("song11")
                .name("songhyeon")
                .password("1111")
                .email("songhyeon@gmail.com")
                .nickname("밀크")
                .phoneNum("0101111111")
                .birth(Date.valueOf("20221111"))
                .build();
        return Member.createMember(memberForm, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member = createMember();
        memberService.saveMember(member);
        }
}
