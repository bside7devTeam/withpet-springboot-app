package org.gig.withpet.admin.config.security;

import org.gig.withpet.core.domain.user.LoginUser;
import org.gig.withpet.core.domain.user.SecurityService;
import org.gig.withpet.core.domain.user.administrator.Administrator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Service
public class AdminSecurityService implements SecurityService<Administrator> {

    @Override
    public Administrator getLoginUser() {
        if (SecurityContextHolder.getContext() != null) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) return null;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof LoginUser) {
                return (Administrator) ((LoginUser) principal).getLoginUser();
            } else {
                return null;
            }
        }
        return null;
    }

}
