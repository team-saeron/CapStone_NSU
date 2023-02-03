package togethers.togethers.service;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import togethers.togethers.dto.UserDetails;


public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
