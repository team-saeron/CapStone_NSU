package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User getByUid(String uid);
    Optional<User> getByUid(String uid);

    Optional<User> getByName(String name);
    Optional<User> findByUid(String id);
}
