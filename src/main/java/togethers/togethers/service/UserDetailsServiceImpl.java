package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import togethers.togethers.data.dto.UserDetails;
import togethers.togethers.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepository.getByUid(username);
    }
}
