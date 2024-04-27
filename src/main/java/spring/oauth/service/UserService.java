package spring.oauth.service;

import static spring.oauth.exception.constants.UserExceptionMessages.DUPLICATE_NAME;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.oauth.exception.UserException;
import spring.oauth.dto.UserSignUpRequest;
import spring.oauth.enums.Role;
import spring.oauth.entity.User;
import spring.oauth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Transactional
    public void signUp(UserSignUpRequest requestParam) {
        validateDuplicateName(requestParam.username());
        saveUser(requestParam);
    }

    // 이름의 중복 검증
    private void validateDuplicateName(String username){
        Optional<User> findMember = userRepository.findByUsername(username);
        if (findMember.isPresent()) {
            throw new UserException(DUPLICATE_NAME.getMessage());
        }
    }

    // User 엔티티 저장
    private void saveUser(UserSignUpRequest requestParam) {
        User user = User.builder()
                .username(requestParam.username())
                .password(passwordEncoder.encode(requestParam.password()))
                .email(requestParam.email())
                .roles(Collections.singletonList(Role.USER))
                .build();
        userRepository.save(user);
    }

}
