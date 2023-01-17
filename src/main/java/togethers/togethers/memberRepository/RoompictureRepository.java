package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.RoomPicture;
import togethers.togethers.entity.Smoking;

public interface RoompictureRepository extends JpaRepository<RoomPicture,Long> {
}
