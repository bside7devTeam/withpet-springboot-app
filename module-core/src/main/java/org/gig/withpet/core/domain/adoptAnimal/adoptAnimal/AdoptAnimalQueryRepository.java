package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalListDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalSearchDto;
import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.QAdoptAnimal.adoptAnimal;


/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdoptAnimalQueryRepository {

    private final JPAQueryFactory queryFactory;


    public Page<AdoptAnimalListDto> getAdoptAnimalPageDto(AdoptAnimalSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());

        JPAQuery<AdoptAnimalListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdoptAnimalListDto.class,
                        adoptAnimal))
                .from(adoptAnimal)
                .where(where)
                .orderBy(adoptAnimal.noticeEdt.desc())
                .limit(searchDto.getPageRequest().getPageSize())
                .offset(searchDto.getPageRequest().getOffset());

        JPAQuery<Long> countQuery = this.queryFactory.select(adoptAnimal.id)
                .from(adoptAnimal)
                .where(where);

        long total = countQuery.fetchCount();
        List<AdoptAnimalListDto> content = contentQuery.fetch();

        return new PageImpl<>(content, searchDto.getPageRequest(), total);
    }

    private BooleanExpression defaultCondition() {
        return adoptAnimal.deleteYn.eq(YnType.N);
    }
}
