package org.gig.withpet.core.domain.area.siggArea;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.gig.withpet.core.domain.area.sidoArea.QSidoArea.sidoArea;
import static org.gig.withpet.core.domain.area.siggArea.QSiggArea.siggArea;

/**
 * @author : JAKE
 * @date : 2022/06/25
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SiggAreaQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<SiggArea> getSiggAreaList() {

        BooleanBuilder where = new BooleanBuilder();

        JPAQuery<SiggArea> contentQuery = this.queryFactory
                .selectFrom(siggArea)
                .innerJoin(siggArea.sido).fetchJoin()
                .where(where)
                ;

        return contentQuery.fetch();
    }

    public List<SiggArea> getSiggAreaListByAdmNameAndSido(String sidoName, String siggName) {

        List<SiggArea> fetch = queryFactory.selectFrom(siggArea)
                .innerJoin(siggArea.sido, sidoArea)
                .where(eqAdmName(siggName))
                .where(eqSidoName(sidoName))
                .fetch();

        return fetch;
    }

    public List<SiggArea> getSiggAreaByAdmNameAndSidoParentCode(String siggName, String sidoAdoptAnimalAdmCode) {

        List<SiggArea> fetch = queryFactory.selectFrom(siggArea)
                .innerJoin(sidoArea)
                .on(siggArea.sido.eq(sidoArea).and(sidoArea.adoptAnimalAdmCode.eq(sidoAdoptAnimalAdmCode)))
                .fetchJoin()
                .where(eqAdmName(siggName))
                .fetch();

        return fetch;
    }

    private BooleanExpression eqAdmName(String admName) {

        if (StringUtils.hasText(admName)) {
            return siggArea.admName.eq(admName);
        }

        return null;
    }

    private BooleanExpression eqSidoName(String admName) {

        if (StringUtils.hasText(admName)) {
            return sidoArea.admName.eq(admName);
        }

        return null;
    }
}
