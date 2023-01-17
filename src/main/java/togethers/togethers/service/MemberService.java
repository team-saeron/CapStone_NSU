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

    @Transactional
    public void post_save(Postform postform, MultipartFile file) throws Exception
    {
        Post post = new Post(postform);
        RoomPicture roomPicture = new RoomPicture();
        Member member = memberRepository.findOne("akahd135");

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid+"_"+file.getOriginalFilename();


        File saveFile = new File(projectPath,fileName);

        file.transferTo(saveFile);

        roomPicture.setFilename(fileName);
        roomPicture.setFilepath("/files/"+fileName);

        member.setPost(post);
        roomPicture.setPost(post);

        memberRepository.post_save(post,roomPicture);
    }

    @Transactional
    public Post findPost(Long postid)
    {
        Post post = memberRepository.findPost(postid);
        return post;
    }

    @Transactional
    public void Member_Post(Post post,String memberid)
    {
        memberRepository.Member_Post(post,memberid);
    }









}

