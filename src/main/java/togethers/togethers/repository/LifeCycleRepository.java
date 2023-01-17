package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.LifeCycle;

public interface LifeCycleRepository extends JpaRepository<LifeCycle,Long> {
}
