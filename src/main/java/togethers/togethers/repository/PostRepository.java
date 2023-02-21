package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    void deleteBypostId(Long PostId);

    Optional<Post>findBypostId(Long PostId);
}

