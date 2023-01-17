package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
