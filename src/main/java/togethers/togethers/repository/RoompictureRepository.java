package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.RoomPicture;

import java.util.Optional;

public interface RoompictureRepository extends JpaRepository<RoomPicture,Long> {

    Optional<RoomPicture> findByPost_PostId(Long PostId);
}
