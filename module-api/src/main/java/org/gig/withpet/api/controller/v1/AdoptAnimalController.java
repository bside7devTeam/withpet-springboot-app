package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimalService;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalListDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalSearchDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.AnimalKindType;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.gig.withpet.core.utils.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@RestController
@Api(value = "AdoptAnimalRestController V1")
@RequestMapping("/v1/adopt-animal")
@RequiredArgsConstructor
public class AdoptAnimalController {

    private final AdoptAnimalService adoptAnimalService;

    @ApiOperation(
            value = "입양동물공고 목록 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "animalKindType"
                            , value = "축종"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = ""
                    ),
                    @ApiImplicitParam(
                            name = "noticeSdt"
                            , value = "공고시작날짜"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "20220601"
                    ),
                    @ApiImplicitParam(
                            name = "noticeEdt"
                            , value = "공고종료날짜"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "20220630"
                    ),
                    @ApiImplicitParam(
                            name = "processStatus"
                            , value = "공고진행상태"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "NOTICE"
                    ),
                    @ApiImplicitParam(
                            name = "terminalStatus"
                            , value = "종료상태"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = ""
                    ),
                    @ApiImplicitParam(
                            name = "page"
                            , value = "페이지 번호"
                            , required = false
                            , dataType = "int"
                            , paramType = "query"
                            , defaultValue = "1"
                    ),
                    @ApiImplicitParam(
                            name = "size"
                            , value = "페이지 사이즈"
                            , required = true
                            , dataType = "int"
                            , paramType = "query"
                            , defaultValue = "3"
                    )
            }
    )
    @GetMapping(value = "", produces ="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getAdoptAnimalPage(
            @RequestParam(value = "animalKindType", required = true) AnimalKindType animalKindType,
            @RequestParam(value = "noticeSdt", required = true) String noticeSdt,
            @RequestParam(value = "noticeEdt", required = true) String noticeEdt,
            @RequestParam(value = "processStatus", required = true) ProcessStatus processStatus,
            @RequestParam(value = "terminalStatus", required = false) TerminalStatus terminalStatus,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "size", required = true) int size
    ) {

        AdoptAnimalSearchDto reqParam = AdoptAnimalSearchDto.builder()
                .animalKindType(animalKindType)
                .noticeStartDate(CommonUtils.convertStringToLocalDate(noticeSdt))
                .noticeEndDate(CommonUtils.convertStringToLocalDate(noticeEdt))
                .processStatus(processStatus)
                .terminalStatus(terminalStatus)
                .page(page)
                .size(size)
                .build();

        Page<AdoptAnimalListDto> pages =  adoptAnimalService.getAdoptAnimalPageDto(reqParam);

        return new ResponseEntity<>(ApiResponse.OK(pages), HttpStatus.OK);
    }
}
