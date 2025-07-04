package codestream.jungmini.me.db.model;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import codestream.jungmini.me.domain.User;
import codestream.jungmini.me.domain.UserRole;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String introduction;

    @Column(nullable = true)
    private String profileImageUrl;

    @Enumerated(value = STRING)
    private UserRole role;

    public User toDomain() {
        return User.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .nickname(nickname)
                .introduction(introduction)
                .profileImageUrl(profileImageUrl)
                .role(role)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
