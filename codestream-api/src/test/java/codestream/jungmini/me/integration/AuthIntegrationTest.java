package codestream.jungmini.me.integration;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import codestream.jungmini.me.api.auth.dto.SendVerificationCodeRequest;
import codestream.jungmini.me.db.repository.UserRepository;
import codestream.jungmini.me.domain.User;

import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
public class AuthIntegrationTest extends IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        doNothing().when(mailService).send(anyString(), anyString(), anyString());
    }

    @Nested
    @DisplayName("인증 메일 발송 테스트")
    class SendVerificationCodeTest {
        @Test
        void 성공() throws Exception {
            // given
            String email = "test@test.com";
            SendVerificationCodeRequest request = new SendVerificationCodeRequest(email);
            // when
            ResultActions response = mockMvc.perform(post("/auth/verification-code")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
            // then
            response.andExpect(status().isOk());
            verify(mailService, times(1)).send(anyString(), anyString(), anyString());
        }

        @Test
        void 실패_중복된_이메일이면_예외가_발생한다() throws Exception {
            // given
            String email = "test@test.com";
            SendVerificationCodeRequest request = new SendVerificationCodeRequest(email);
            User user = User.builder().email(email).build();
            doReturn(Optional.of(user)).when(userRepository).findUserByEmail(anyString());
            // when
            ResultActions response = mockMvc.perform(post("/auth/verification-code")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
            // then
            response.andExpect(status().isConflict());
            verify(mailService, never()).send(anyString(), anyString(), anyString());
        }
    }
}
