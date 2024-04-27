package spring.oauth.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessages {

    DUPLICATE_NAME("이미 존재하는 이름입니다."),
    USER_NAME_NOT_FOUND("해당하는 이름을 가진 회원이 없습니다.");

    private final String message;

}