package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail,Long> {
}
