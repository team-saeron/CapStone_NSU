package togethers.togethers.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.MemberDetail;
import togethers.togethers.entity.Smoking;

public interface MemberDetailRepository extends JpaRepository<MemberDetail,Long> {
}
