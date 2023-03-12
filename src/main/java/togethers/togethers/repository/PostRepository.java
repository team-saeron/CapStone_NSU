package togethers.togethers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    void deleteBypostId(Long PostId);

    Optional<Post>findBypostId(Long PostId);

    Page<Post> findByTitleContaining(String keyword, PageRequest pageRequest);

    Page<Post>findByAreaContaining(String area,PageRequest pageRequest);

}

