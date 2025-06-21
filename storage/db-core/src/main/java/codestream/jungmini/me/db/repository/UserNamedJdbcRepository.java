package codestream.jungmini.me.db.repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import codestream.jungmini.me.model.User;
import codestream.jungmini.me.model.UserRole;

@Repository
@RequiredArgsConstructor
public class UserNamedJdbcRepository implements UserRepositroy {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql =
                """
            INSERT INTO users (email, password, nickname, introduction, profile_image_url, role, created_at, updated_at)
            VALUES (:email, :password, :nickname, :introduction, :profileImageUrl, :role, :createdAt, :updatedAt)
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("nickname", user.getNickname())
                .addValue("introduction", user.getIntroduction())
                .addValue("profileImageUrl", user.getProfileImageUrl())
                .addValue("role", user.getRole().name())
                .addValue("createdAt", LocalDateTime.now())
                .addValue("updatedAt", LocalDateTime.now());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql =
                """
            SELECT user_id, email, password, nickname, introduction, profile_image_url, role, created_at, updated_at
            FROM users
            WHERE email = :email
            """;

        try {
            User user = jdbcTemplate.queryForObject(sql, Map.of("email", email), (rs, rowNum) -> User.builder()
                    .userId(rs.getInt("user_id"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .nickname(rs.getString("nickname"))
                    .introduction(rs.getString("introduction"))
                    .profileImageUrl(rs.getString("profile_image_url"))
                    .role(UserRole.valueOf(rs.getString("role")))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                    .build());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
