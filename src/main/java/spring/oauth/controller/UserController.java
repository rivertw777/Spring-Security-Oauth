package spring.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import spring.oauth.dto.UserSignUpRequest;
import spring.oauth.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("")
    public void signUp(@Valid @RequestBody UserSignUpRequest requestParam) {
        userService.signUp(requestParam);
    }
}
