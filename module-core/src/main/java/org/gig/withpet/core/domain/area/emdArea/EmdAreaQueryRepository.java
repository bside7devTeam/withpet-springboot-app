package org.gig.withpet.core.domain.area.emdArea;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.gig.withpet.core.domain.area.emdArea.QEmdArea.emdArea;
import static org.gig.withpet.core.domain.area.sidoArea.QSidoArea.sidoArea;
import static org.gig.withpet.core.domain.area.siggArea.QSiggArea.siggArea;

/**
 * @author : JAKE
 * @date : 2022/07/11
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmdAreaQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<EmdArea> getEmdAreaByRegionNames(String region1Name, String region2Name, String region3Name) {

        Optional<EmdArea> fetch = Optional.ofNullable(this.queryFactory
                .selectFrom(emdArea)
                        .innerJoin(emdArea.sigg).on(emdArea.sigg.admName.contains(region2Name))
                        .innerJoin(sidoArea).on(emdArea.sigg.sido.eq(sidoArea).and(sidoArea.admName.contains(region1Name)))
                .where(likeAdmName(region3Name))
                .limit(1)
                .fetchFirst());

        return fetch;
    }

    private BooleanExpression likeAdmName(String region3Name) {
        if (region3Name == null) {
            return null;
        }

        return emdArea.admName.like(region3Name);
    }

}
