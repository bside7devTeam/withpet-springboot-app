package org.gig.withpet.admin.config.security;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.config.security.AbstractSecurityConfiguration;
import org.gig.withpet.core.config.security.UserDetailsServiceImpl;
import org.gig.withpet.core.domain.user.administrator.AdministratorService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AdminSecurityConfig extends AbstractSecurityConfiguration {

    private final AdministratorService administratorService;
    private final AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    private final AuthenticationFailureHandlerImpl authenticationFailureHandler;


    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(administratorService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/init-data").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }
}
