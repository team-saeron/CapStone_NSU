package togethers.togethers.repository;

import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User getByUid(String uid);
    Optional<User> getByUid(String uid);

    Optional<User> findByUserDetail_UserDetailId(Long id);

    Optional<User> findByUid(String id);

    Optional<User> findByName(String name);

}
