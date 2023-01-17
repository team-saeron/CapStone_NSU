package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Gender;

public interface GenderRepository extends JpaRepository<Gender,Long> {
}
