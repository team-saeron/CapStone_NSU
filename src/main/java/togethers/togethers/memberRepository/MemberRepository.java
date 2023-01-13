package togethers.togethers.memberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import togethers.togethers.entity.Member;
import togethers.togethers.entity.Post;

import javax.persistence.EntityManager;
import java.util.*;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getById(String id);





}
