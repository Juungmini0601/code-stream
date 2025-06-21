package codestream.jungmini.me.db.repository;

import java.util.Optional;

import codestream.jungmini.me.model.User;

public interface UserRepositroy {
    void save(User user);

    Optional<User> findByEmail(String email);
}
