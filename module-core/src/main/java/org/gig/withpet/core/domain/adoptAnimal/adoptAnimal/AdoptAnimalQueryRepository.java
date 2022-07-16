package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.util.StringUtil;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalDetailDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalListDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalSearchDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.response.AnimalKindInfoResponse;
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
import static org.gig.withpet.core.domain.adoptAnimal.animalKind.QAnimalKind.animalKind;


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

        QueryResults<AdoptAnimalListDto> result = this.queryFactory
                .select(Projections.constructor(AdoptAnimalListDto.class,
                        adoptAnimal))
                .from(adoptAnimal)
                .where(
                        notDeleted(),
                        inNoticeDuration(searchDto.getNoticeStartDate(), searchDto.getNoticeEndDate()),
                        eqProcessStatus(searchDto.getProcessStatus()),
                        eqTerminalStatus(searchDto.getTerminalStatus()),
                        eqAnimalKindType(searchDto.getAnimalKindType())
                )
                .orderBy(adoptAnimal.noticeEndDate.desc())
                .limit(searchDto.getPageRequest().getPageSize())
                .offset(searchDto.getPageRequest().getOffset())
                .fetchResults();

        return new PageImpl<>(result.getResults(), searchDto.getPageRequest(), result.getTotal());
    }

    public Page<AdoptAnimalListDto> getSuccessAdoptAnimalPageDto(AdoptAnimalSearchDto searchDto) {


        QueryResults<AdoptAnimalListDto> result = this.queryFactory
                .select(Projections.constructor(AdoptAnimalListDto.class,
                        adoptAnimal))
                .from(adoptAnimal)
                .where(
                        notDeleted(),
                        eqAnimalKindType(searchDto.getAnimalKindType()),
                        adoptAnimal.processStatus.eq(ProcessStatus.TERMINAL),
                        adoptAnimal.terminalState.eq(TerminalStatus.ADOPT),
                        adoptAnimal.adoptSuccessDate.isNotNull(),
                        adoptAnimal.adoptSuccessDate.after(LocalDate.now().minusMonths(1))
                )
                .orderBy(adoptAnimal.adoptSuccessDate.desc())
                .offset(searchDto.getPageRequest().getOffset())
                .limit(searchDto.getPageRequest().getPageSize())
                .fetchResults()
                ;

        return new PageImpl<>(result.getResults(), searchDto.getPageRequest(), result.getTotal());
    }

    public List<AnimalKindInfoResponse> getAdoptAnimalKindInfo(AdoptAnimalSearchDto searchDto) {

        List<AnimalKindInfoResponse> fetch = this.queryFactory
                .select(Projections.constructor(AnimalKindInfoResponse.class, animalKind))
                .from(animalKind)
                .where(
                        eqUpKindCode(searchDto.getAnimalKindType().getKey()),
                        likeAnimalKindName(searchDto.getKindName())
                )
                .fetch();

        return fetch;
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

    private BooleanExpression notDeleted() {
        return adoptAnimal.deleteYn.eq(YnType.N);
    }

    private BooleanExpression likeAnimalKindName(String kindName) {

        if (!StringUtils.hasText(kindName)) {
            return null;
        }

        return animalKind.KNm.like(kindName);
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

    private BooleanExpression eqUpKindCode(String upKindCode) {

        if (!StringUtils.hasText(upKindCode)) {
            return null;
        }

        return animalKind.upKindCd.eq(upKindCode);
    }

    private BooleanExpression eqAdoptAnimalId(Long adoptAnimalId) {
        if (adoptAnimalId == null) {
            return null;
        }

        return adoptAnimal.id.eq(adoptAnimalId);
    }


}
