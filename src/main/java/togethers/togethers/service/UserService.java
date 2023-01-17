package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.entity.User;
import togethers.togethers.repository.UserRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public String join(User user){
        userRepository.save(user);
        return user.getUid();
    }

    @Transactional
    public User findMember(String memberid)
    {

        User user = userRepository.getByUid(memberid);
        return user;
    }











}

