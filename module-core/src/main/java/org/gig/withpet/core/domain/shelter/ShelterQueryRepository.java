package org.gig.withpet.core.domain.shelter;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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

        QueryResults<ShelterListDto> result = this.queryFactory
                .select(Projections.constructor(ShelterListDto.class,
                        shelter))
                .from(shelter)
                .where(
                        notDeleted(),
                        eqSidoCode(searchDto.getSidoCode()),
                        eqSiggCode(searchDto.getSiggCode())
                )
                .orderBy(shelter.createdAt.desc())
                .offset(searchDto.getPageRequest().getOffset())
                .limit(searchDto.getPageRequest().getPageSize())
                .fetchResults()
                ;

        return new PageImpl<>(result.getResults(), searchDto.getPageRequest(), result.getTotal());
    }

    private BooleanExpression notDeleted() {
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
