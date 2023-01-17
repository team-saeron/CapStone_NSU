package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Pet;

public interface PetRepository extends JpaRepository<Pet,Long> {
}
