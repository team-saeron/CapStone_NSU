package togethers.togethers.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import togethers.togethers.dto.UserDetails;
import togethers.togethers.repository.UserRepository;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username){
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepository.findByUid(username).get();
    }
}
