package togethers.togethers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.User;
import togethers.togethers.repository.PostRepository;
import togethers.togethers.repository.RoompictureRepository;
//import togethers.togethers.repository.UserRepository;
import togethers.togethers.service.form.Postform;

import java.io.File;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private RoompictureRepository roompictureRepository;


    @Transactional
    public void post_save(Postform postform, MultipartFile file) throws Exception
    {
        Post post = new Post(postform);
        RoomPicture roomPicture = new RoomPicture();
//        User user = userRepository.findById(1L).get();

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid+"_"+file.getOriginalFilename();

        File saveFile = new File(projectPath,fileName);

        file.transferTo(saveFile);

        roomPicture.setFilename(fileName);
        roomPicture.setFilepath("/files/"+fileName);

//        user.setPost(post);
        roomPicture.setPost(post);

//        userRepository.save(user);
        roompictureRepository.save(roomPicture);
        postRepository.save(post);
    }
}
