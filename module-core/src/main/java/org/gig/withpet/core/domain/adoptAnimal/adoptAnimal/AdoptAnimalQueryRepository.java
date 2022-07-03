package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalDetailDto;
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
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.QAdoptAnimal.adoptAnimal;
import static org.gig.withpet.core.domain.shelter.QShelter.shelter;


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

    public Page<AdoptAnimalListDto> getSuccessAdoptAnimalPageDto(AdoptAnimalSearchDto searchDto) {

        BooleanBuilder where = new BooleanBuilder();
        where.and(defaultCondition());
        where.and(eqAnimalKindType(searchDto.getAnimalKindType()));
        where.and(adoptAnimal.processStatus.eq(ProcessStatus.TERMINAL));
        where.and(adoptAnimal.terminalState.eq(TerminalStatus.ADOPT));
        where.and(adoptAnimal.adoptSuccessDate.isNotNull());
        where.and(adoptAnimal.adoptSuccessDate.after(LocalDate.now().minusMonths(1)));

        JPAQuery<AdoptAnimalListDto> contentQuery = this.queryFactory
                .select(Projections.constructor(AdoptAnimalListDto.class,
                        adoptAnimal))
                .from(adoptAnimal)
                .where(where)
                .orderBy(adoptAnimal.adoptSuccessDate.desc());

        JPAQuery<Long> countQuery = this.queryFactory.select(adoptAnimal.id)
                .from(adoptAnimal)
                .where(where);

        long total = countQuery.fetchCount();
        List<AdoptAnimalListDto> content = contentQuery.fetch();

        return new PageImpl<>(content, searchDto.getPageRequest(), total);
    }

    public Optional<AdoptAnimalDetailDto> getDetail(Long adoptAnimalId) {

        Optional<AdoptAnimalDetailDto> fetch = Optional.ofNullable(this.queryFactory
                .select(Projections.constructor(AdoptAnimalDetailDto.class,
                        adoptAnimal))
                .from(adoptAnimal)
                .where(eqAdoptAnimalId(adoptAnimalId))
                .limit(1)
                .fetchFirst());

        return fetch;
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

    private BooleanExpression eqAdoptAnimalId(Long adoptAnimalId) {
        if (adoptAnimalId == null) {
            return null;
        }

        return adoptAnimal.id.eq(adoptAnimalId);
    }
}
