package codestream.jungmini.me.unit.auth;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import codestream.jungmini.me.application.auth.VerificationCodeGenerator;

public class VerificationCodeGeneratorTest {

    VerificationCodeGenerator generator = new VerificationCodeGenerator();

    @Test
    @DisplayName("인증 번호는 6자리 정수 문자열이다.")
    void generateTest() {
        String code = generator.generate();
        final int CODE_SIZE = 6;

        assertThat(code).hasSize(CODE_SIZE);
        assertThat(code.chars().allMatch(Character::isDigit)).isTrue();
    }
}
