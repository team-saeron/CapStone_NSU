package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    Optional<Favorite> findByPost_PostId(Long id);

    Optional<Favorite>findByPost_PostIdAndUser_Id(Long postId,Long userId);

    List<Favorite>findAllByUser_Id(Long Id);
}
