package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.UserDetail;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail,Long> {
    Optional<UserDetail> findByUserDetailId(Long id);
}
