package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalListDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalSearchDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.AnimalKindType;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        where.and(inNoticeDuration(searchDto.getNoticeStartDate(), searchDto.getNoticeEndDate()));
        where.and(eqProcessStatus(searchDto.getProcessStatus()));
        where.and(eqTerminalStatus(searchDto.getTerminalStatus()));
        where.and(eqAnimalKindType(searchDto.getAnimalKindType()));

        JPAQuery<AdoptAnimalListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdoptAnimalListDto.class,
                        adoptAnimal))
                .from(adoptAnimal)
                .where(where)
                .orderBy(adoptAnimal.noticeEndDate.desc())
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

    private BooleanExpression inNoticeDuration(LocalDate noticeStartDate, LocalDate noticeEndDate) {

        if (noticeStartDate == null || noticeEndDate == null) {
            return null;
        }

        return adoptAnimal.noticeStartDate.after(noticeStartDate)
                .and(adoptAnimal.noticeEndDate.before(noticeEndDate));
    }

    private BooleanExpression eqProcessStatus(ProcessStatus processStatus) {
        if (processStatus == null) {
            return null;
        }

        return adoptAnimal.processStatus.eq(processStatus);
    }

    private BooleanExpression eqTerminalStatus(TerminalStatus terminalStatus) {
        if (terminalStatus == null) {
            return null;
        }

        return adoptAnimal.terminalState.eq(terminalStatus);
    }

    private BooleanExpression eqAnimalKindType(AnimalKindType animalKindType) {

        if (animalKindType == null) {
            return null;
        }

        return adoptAnimal.animalKindType.eq(animalKindType);
    }
}