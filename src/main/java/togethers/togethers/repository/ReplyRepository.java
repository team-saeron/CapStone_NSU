package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    List<Reply> findAllByPost_PostId(Long PostId);

    void deleteByPost_PostIdAndUser_Id(Long PostId,Long UserId);

    Optional<Reply>findByPost_PostIdAndUser_Id(Long post_id, Long user_id);

}
