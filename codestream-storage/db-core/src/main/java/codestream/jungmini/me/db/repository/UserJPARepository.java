package codestream.jungmini.me.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import codestream.jungmini.me.db.model.UserEntity;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
