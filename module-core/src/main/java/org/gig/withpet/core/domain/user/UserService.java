package org.gig.withpet.core.domain.user;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
public interface UserService<T> {

    T getUser(String username);

    void loginSuccess(String username);

    void increasePasswordFailureCount(String username);

    boolean existsUsername(String username);
}
