package spring.oauth.config.auth;

import static spring.oauth.exception.constants.UserExceptionMessages.USER_NAME_NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.oauth.entity.User;
import spring.oauth.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 이름으로 회원 조회
    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NAME_NOT_FOUND.getMessage()));
        return new CustomUserDetails(user);
    }
}
