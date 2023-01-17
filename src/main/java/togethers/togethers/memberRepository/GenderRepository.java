package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Gender;
import togethers.togethers.entity.Smoking;

public interface GenderRepository extends JpaRepository<Gender,Long> {
}
