package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.shelter.ShelterService;
import org.gig.withpet.core.domain.shelter.dto.ShelterListDto;
import org.gig.withpet.core.domain.shelter.dto.ShelterSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 2022/06/11
 */
@RestController
@Api(value = "ShelterRestcontroller V1")
@RequestMapping("/v1/shelter")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterService shelterService;

    @ApiOperation(
            value = "보호소 목록 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "sidoCode"
                            , value = "시도 코드"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "6110000"
                    ),
                    @ApiImplicitParam(
                            name = "siggCode"
                            , value = "시군구 코드"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "3220000"
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
            @RequestParam(value = "sidoCode", required = false) String sidoCode,
            @RequestParam(value = "siggCode", required = false) String siggCode,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "size", required = true) int size
    ) {

        ShelterSearchDto reqParam = ShelterSearchDto.builder()
                .sidoCode(sidoCode)
                .siggCode(siggCode)
                .page(page)
                .size(size)
                .build();

        Page<ShelterListDto> pages =  shelterService.getShelterPageDto(reqParam);

        return new ResponseEntity<>(ApiResponse.OK(pages), HttpStatus.OK);
    }
}
