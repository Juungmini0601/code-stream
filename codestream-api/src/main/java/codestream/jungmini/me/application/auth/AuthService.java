package codestream.jungmini.me.application.auth;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import codestream.jungmini.me.db.repository.UserRepository;
import codestream.jungmini.me.mail.service.MailService;
import codestream.jungmini.me.redis.service.RedisService;
import codestream.jungmini.me.support.error.CodeStreamException;
import codestream.jungmini.me.support.error.ErrorType;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final VerificationCodeGenerator verificationCodeGenerator;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final RedisService redisService;

    private static final String VERIFICATION_CODE_PREFIX = "auth:verification-code:";
    private static final Integer VERIFICATION_CODE_EXPIRE_SECONDS = 60 * 5;

    public void sendVerificationCode(final String email) {
        userRepository.findUserByEmail(email).ifPresent(user -> {
            throw new CodeStreamException(ErrorType.CONFLICT_ERROR, String.format("user already exists [%s]", email));
        });

        String verificationCode = verificationCodeGenerator.generate();
        redisService.set(VERIFICATION_CODE_PREFIX + email, verificationCode, VERIFICATION_CODE_EXPIRE_SECONDS);
        mailService.send(email, "[code stream] 인증 메일", String.format("회원 가입 창에 %s 를 입력해주세요", verificationCode));
    }
}
