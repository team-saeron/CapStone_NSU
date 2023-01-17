package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Category;
import togethers.togethers.entity.Smoking;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
