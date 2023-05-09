package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Mbti;

import java.util.Optional;

public interface MbtiRepository extends JpaRepository<Mbti,Long> {
    Optional<Mbti> findByMyMbti(String mbti);
}
