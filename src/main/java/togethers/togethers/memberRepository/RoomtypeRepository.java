package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.RoomType;
import togethers.togethers.entity.Smoking;

public interface RoomtypeRepository extends JpaRepository<RoomType,Long> {
}
