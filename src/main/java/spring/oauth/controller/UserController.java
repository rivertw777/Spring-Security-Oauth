package spring.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import spring.oauth.config.auth.CustomUserDetails;
import spring.oauth.dto.UserSignUpRequest;
import spring.oauth.entity.User;
import spring.oauth.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 인덱스 페이지
    @GetMapping("")
    public String index() {
        return "index";
    }

    // 폼 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 소셜 로그인 페이지
    @GetMapping("/login/social")
    public String socialLogin() { return "socialLogin";}

    // 회원가입 페이지
    @GetMapping("/join")
    public String join() {
        return "join";
    }

    // 회원 가입
    @PostMapping("/joinProc")
    public String joinProc(@Valid @ModelAttribute UserSignUpRequest requestParam) {
        System.out.println(requestParam);
        userService.signUp(requestParam);
        return "redirect:/";
    }

    // 유저 페이지
    @GetMapping("/user")
    public void user(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        System.out.println(principal.getAttributes());
        User user = principal.getUser();
        model.addAttribute("user", user);
    }

}
