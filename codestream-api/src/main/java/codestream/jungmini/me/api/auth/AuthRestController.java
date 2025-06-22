package codestream.jungmini.me.api.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import codestream.jungmini.me.api.auth.dto.SendVerificationCodeRequest;
import codestream.jungmini.me.application.auth.AuthService;
import codestream.jungmini.me.support.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/auth/verification-code")
    public ApiResponse<?> sendVerificationCode(@RequestBody SendVerificationCodeRequest request) {
        authService.sendVerificationCode(request.email());

        return ApiResponse.success();
    }
}
