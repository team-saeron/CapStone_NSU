package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}

