package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User getByUid(String uid);
    Optional<User> findByName(String name);
    Optional<User> findByUid(String Uid); //id로 찾기
    Optional<User>findByPost_PostId(Long PostId);
    Optional<User>findById(Long id); //pk로 찾기

    Optional<User> findByUserDetail_UserDetailId(Long id);


    Optional<User>findByNameAndPhoneNum(String name, String phoneNum);

    Optional<User>findByNameAndEmail(String name, String email);

    Optional<User>findByNameAndEmailAndUid(String name, String email, String Uid);

}
