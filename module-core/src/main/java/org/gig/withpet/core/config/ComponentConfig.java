package org.gig.withpet.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : JAKE
 * @date : 2022/05/20
 */
@Configuration
@EntityScan(basePackages = "org.gig.withpet.core")
@ComponentScan(basePackages ="org.gig.withpet.core")
@EnableJpaRepositories(basePackages = "org.gig.withpet.core")
public class ComponentConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
