package org.gig.withpet.core.domain.community.community;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.community.dto.CommunityDto;
import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.gig.withpet.core.domain.activityAreas.QActivityAreas.activityAreas;
import static org.gig.withpet.core.domain.community.community.QCommunity.community;
import static org.gig.withpet.core.domain.user.member.QMember.member;

/**
 * @author : JAKE
 * @date : 2022/07/20
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Community> getCommunityPage(CategoryType categoryType, Pageable pageable) {

        QueryResults<Community> result = this.queryFactory
                .selectFrom(community)
                .where(
                        notDeleted(),
                        eqCategoryType(categoryType)
                )
                .orderBy(community.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public Page<Community> getCommunityMyTownPage(CategoryType categoryType, List<Long> emdIds, Pageable pageable) {
        QueryResults<Community> result = this.queryFactory
                .selectFrom(community)
                .join(community.writer, member)
                .join(member.activityAreas, activityAreas)
                .on(activityAreas.emdArea.id.in(emdIds))
                .where(
                        notDeleted(),
                        eqCategoryType(categoryType)
                )
                .orderBy(community.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public Optional<Community> getDetail(Long communityId) {

        Optional<Community> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(community)
                .where(
                        notDeleted(),
                        eqCommunityId(communityId)
                )
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    private BooleanExpression eqCommunityId(Long communityId) {
        if (communityId == null) {
            return null;
        }

        return community.id.eq(communityId);
    }

    private BooleanExpression eqCategoryType(CategoryType categoryType) {
        if (categoryType == null) {
            return null;
        }

        return community.categoryType.eq(categoryType);
    }

    private BooleanExpression notDeleted() {
        return community.deleteYn.eq(YnType.N);
    }


}
