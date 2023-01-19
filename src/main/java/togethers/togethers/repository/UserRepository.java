package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUid(String uid);
}
