package org.gig.withpet.core.domain.user.administrator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdministratorService implements UserService<Administrator> {

    @Override
    public Administrator getUser(String username) {
        return null;
    }

    @Override
    public void loginSuccess(String username) {

    }

    @Override
    public void increasePasswordFailureCount(String username) {

    }

    @Override
    public boolean existsUsername(String username) {
        return false;
    }
}
