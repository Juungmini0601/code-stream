package codestream.jungmini.me.db.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserNamedJdbcRepository implements UserRepositroy {
	private final NamedParameterJdbcTemplate jdbcTemplate;
}
