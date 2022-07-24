package org.gig.withpet.core.config.security;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.user.AbstractUser;
import org.gig.withpet.core.domain.user.LoginUser;
import org.gig.withpet.core.domain.user.UserService;
import org.gig.withpet.core.domain.user.UserType;
import org.gig.withpet.core.domain.user.administrator.AdministratorService;
import org.gig.withpet.core.domain.user.member.AuthService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Slf4j
@Service
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;
    private UserType userType;

    public UserDetailsServiceImpl(AdministratorService administratorService) {
        userService = administratorService;
        userType = UserType.ADMIN;
    }

//    public UserDetailsServiceImpl(AuthService authService) {
//        userService = authService;
//        userType = UserType.USER;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AbstractUser user;

        user = (AbstractUser) userService.getUser(username);

        // Role
        Set<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());

        boolean loginEnabled = true;
        boolean accountNonExpired = true;
        boolean credentialNonExpired = true;
        boolean accountNonLocked = true;

        if (user.getPasswordFailureCount() >= 5) {
            accountNonLocked = false;
        }

        if (!user.isNormal()) {
            loginEnabled = false;
        }

        return new LoginUser(user.getUsername(), user.getPassword(), loginEnabled, accountNonExpired, credentialNonExpired, accountNonLocked, authorities, user);
    }
}
