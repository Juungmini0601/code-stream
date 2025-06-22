package codestream.jungmini.me.db.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import codestream.jungmini.me.db.model.UserEntity;
import codestream.jungmini.me.domain.User;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJPARepository userJPARepository;

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userJPARepository.findByEmail(email).map(UserEntity::toDomain);
    }
}
