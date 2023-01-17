package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.test.Board;

public interface PostRepository extends JpaRepository<Post,Long> {
}

