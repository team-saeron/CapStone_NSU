package togethers.togethers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.memberRepository.MemberRepository;
import togethers.togethers.memberRepository.PostRepository;
import togethers.togethers.memberRepository.RoompictureRepository;
import togethers.togethers.service.form.Postform;

import java.io.File;
import java.util.UUID;

@Service
public class PostService {

    private PostRepository postRepository;

    private MemberRepository memberRepository;

    private RoompictureRepository roompictureRepository;


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

        memberRepository.save(member);
        roompictureRepository.save(roomPicture);
        postRepository.save(post);
    }
}
