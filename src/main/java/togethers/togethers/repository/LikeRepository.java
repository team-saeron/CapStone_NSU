package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Like;

public interface LikeRepository extends JpaRepository<Like,Long> {
}
