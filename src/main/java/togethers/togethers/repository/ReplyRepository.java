package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
