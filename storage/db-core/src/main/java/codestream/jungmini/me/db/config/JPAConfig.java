package codestream.jungmini.me.db.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan("codestream.jungmini.me.db.model")
@EnableJpaRepositories("codestream.jungmini.me.db")
public class JPAConfig {}
