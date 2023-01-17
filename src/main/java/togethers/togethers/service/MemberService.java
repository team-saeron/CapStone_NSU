package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.memberRepository.MemberRepository;
import togethers.togethers.service.form.Postform;


import java.io.File;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    @Transactional
    public String join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Member findMember(String memberid)
    {
        Member findMember = memberRepository.findOne(memberid);
        return findMember;
    }











}

