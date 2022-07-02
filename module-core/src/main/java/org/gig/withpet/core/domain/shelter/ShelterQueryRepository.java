package org.gig.withpet.core.domain.shelter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.shelter.dto.ShelterListDto;
import org.gig.withpet.core.domain.shelter.dto.ShelterSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.gig.withpet.core.domain.Area.sidoArea.QSidoArea.sidoArea;
import static org.gig.withpet.core.domain.Area.siggArea.QSiggArea.siggArea;
import static org.gig.withpet.core.domain.shelter.QShelter.shelter;

/**
 * @author : JAKE
 * @date : 2022/06/11
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShelterQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ShelterListDto> getShelterPageDto(ShelterSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(eqSidoCode(searchDto.getSidoCode()));
        where.and(eqSiggCode(searchDto.getSiggCode()));

        JPAQuery<ShelterListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(ShelterListDto.class,
                        shelter))
                .from(shelter)
                .where(where)
                .orderBy(shelter.createdAt.desc())
                .limit(searchDto.getPageRequest().getPageSize())
                .offset(searchDto.getPageRequest().getPageNumber());

        JPAQuery<Long> countQuery = this.queryFactory.select(shelter.id)
                .from(shelter)
                .where(where);

        long total = countQuery.fetchCount();
        List<ShelterListDto> content = contentQuery.fetch();

        return new PageImpl<>(content, searchDto.getPageRequest(), total);
    }

    private BooleanExpression defaultCondition() {
        return shelter.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqSidoCode(String sidoCode) {
        if (!StringUtils.hasText(sidoCode)) {
            return null;
        }

        return shelter.sidoCode.eq(sidoCode);
    }

    private BooleanExpression eqSiggCode(String siggCode) {
        if (!StringUtils.hasText(siggCode)) {
            return null;
        }

        return shelter.siggCode.eq(siggCode);
    }
}
