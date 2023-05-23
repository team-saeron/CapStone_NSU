package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.AwsFileUrl;

import java.util.List;

public interface AwsFileUrlRepository extends JpaRepository<AwsFileUrl, Long> {

    List<AwsFileUrl> findAllByPost_PostId(Long postId);
}
