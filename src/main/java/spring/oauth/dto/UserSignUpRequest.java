package spring.oauth.dto;

import org.antlr.v4.runtime.misc.NotNull;

public record UserSignUpRequest(@NotNull String username, @NotNull String email, @NotNull String password) {
    public UserSignUpRequest {
    }
}
