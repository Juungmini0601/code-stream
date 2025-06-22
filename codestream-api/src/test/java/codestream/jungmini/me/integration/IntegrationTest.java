package codestream.jungmini.me.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import codestream.jungmini.me.mail.service.MailService;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public abstract class IntegrationTest {

    @MockitoBean
    protected MailService mailService;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("codestream")
            .withUsername("codestream")
            .withPassword("codestream")
            .withReuse(true);

    @Container
    static GenericContainer<?> redis =
            new GenericContainer<>("redis:7.2-alpine").withExposedPorts(6379).withReuse(true); // 테스트간 컨테이너 재사용

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // MySQL 설정
        registry.add("storage.datasource.core.jdbc-url", mysql::getJdbcUrl);
        registry.add("storage.datasource.core.username", mysql::getUsername);
        registry.add("storage.datasource.core.password", mysql::getPassword);
        registry.add("storage.datasource.core.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");

        // Redis 설정
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
    }
}
