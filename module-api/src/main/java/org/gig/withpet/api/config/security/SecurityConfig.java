package org.gig.withpet.api.config.security;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : JAKE
 * @date : 2022/05/28
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/public-data/**",
                        "/api/v1/kakao/**",
                        "/api/v1/adopt-animal/**",
                        "/api/v1/shelter/**",
                        "/api/v1/v-world/**",
                        "/api/v1/attachment/**",
                        "/api/v1/member/login",
                        "/api/v1/member",
                        "/api/v1/member/add-info",
                        "/api/v1/health-check")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
