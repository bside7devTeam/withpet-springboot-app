package org.gig.withpet.core.domain.user.administrator;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.gig.withpet.core.domain.user.administrator.QAdministrator.administrator;
import static org.gig.withpet.core.domain.user.administrator.QAdministratorRole.administratorRole;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministratorQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Administrator findByUsernameAndRoles(String username) {
        return queryFactory
                .select(administrator)
                .from(administrator)
                .join(administrator.administratorRoles, administratorRole).fetchJoin()
                .join(administratorRole.role)
                .where(administrator.username.eq(username))
                .limit(1)
                .fetchFirst();
    }

    public Boolean existsByUsername(String username) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(administrator)
                .where(administrator.username.eq(username))
                .fetchFirst();
        return fetchOne != null;
    }
}
