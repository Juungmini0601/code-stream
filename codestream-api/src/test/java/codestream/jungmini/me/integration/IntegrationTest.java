package codestream.jungmini.me.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // 컨텍스트 정리용
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
    static GenericContainer<?> redis = new GenericContainer<>("redis:7.2-alpine")
            .withExposedPorts(6379)
            .withReuse(true)
            .withCommand("redis-server", "--appendonly", "yes"); // Redis 데이터 지속성 설정

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

        // HikariCP 타임아웃 설정
        registry.add("storage.datasource.core.hikari.connection-timeout", () -> "20000");
        registry.add("storage.datasource.core.hikari.idle-timeout", () -> "300000");
        registry.add("storage.datasource.core.hikari.max-lifetime", () -> "600000");
        registry.add("storage.datasource.core.hikari.maximum-pool-size", () -> "5");
        registry.add("storage.datasource.core.hikari.minimum-idle", () -> "2");
    }
}
