package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Reply;
import togethers.togethers.entity.Smoking;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
