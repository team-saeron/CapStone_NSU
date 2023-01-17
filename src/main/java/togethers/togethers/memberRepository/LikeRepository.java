package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Like;
import togethers.togethers.entity.Smoking;

public interface LikeRepository extends JpaRepository<Like,Long> {
}
