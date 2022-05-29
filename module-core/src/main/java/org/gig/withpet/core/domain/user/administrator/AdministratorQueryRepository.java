package org.gig.withpet.core.domain.user.administrator;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.user.UserStatus;
import org.gig.withpet.core.domain.user.administrator.dto.AdminSearchDto;
import org.gig.withpet.core.domain.user.administrator.dto.AdministratorListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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

    public Page<AdministratorListDto> getAdminPageListBySearch(AdminSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(eqUsername(searchDto.getKeyword()));
        where.and(eqUserStatus(searchDto.getUserStatus()));
        where.and(eqName(searchDto.getName()));

        JPAQuery<AdministratorListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdministratorListDto.class,
                        administrator))
                .from(administrator)
                .where(where)
                .limit(searchDto.getPageableWithSort().getPageSize())
                .offset(searchDto.getPageableWithSort().getOffset());

        JPAQuery<Long> countQuery = this.queryFactory.select(administrator.id)
                .from(administrator)
                .where(where);

        long total = countQuery.fetchCount();
        List<AdministratorListDto> content = contentQuery.fetch();

        return new PageImpl<>(content, searchDto.getPageableWithSort(), total);
    }

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

    private BooleanExpression eqAdminId(Long adminId) {
        return adminId != null ? administrator.id.eq(adminId) : null;
    }

    private BooleanExpression eqUsername(String username) {
        return StringUtils.hasText(username) ? administrator.username.eq(username) : null;
    }

    private BooleanExpression eqUserStatus(UserStatus status) {
        return status != null ? administrator.status.eq(status) : null;
    }

    private BooleanExpression eqName(String name) {
        return StringUtils.hasText(name) ? administrator.name.eq(name) : null;
    }
}
