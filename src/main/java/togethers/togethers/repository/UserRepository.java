package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User getByUid(String uid);
    Optional<User> findByName(String name);
    Optional<User> findByUid(String Uid); //id로 찾기

    Optional<User>findById(Long id); //pk로 찾기

    Optional<User> findByUserDetail_UserDetailId(Long id);
}
