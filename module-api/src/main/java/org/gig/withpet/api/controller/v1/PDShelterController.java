package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.data.animalShelter.AnimalShelterApiService;
import org.gig.withpet.core.data.animalShelter.dto.AnimalShelterReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author : JAKE
 * @date : 2022/06/15
 */
@RestController
@Api(value = "PublicDataShelterRestController V1")
@RequestMapping("/public-data/animal-shelter")
@RequiredArgsConstructor
public class PDShelterController {

    private final AnimalShelterApiService animalShelterApiService;

    @ApiOperation(
            value = "동불보호센터 정보 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "type"
                            , value = "데이터출력형태"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "json"
                    ),
                    @ApiImplicitParam(
                            name = "animalCnterNm"
                            , value = "동물보호센터명"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = ""
                    ),
                    @ApiImplicitParam(
                            name = "institutionNm"
                            , value = "관리기관명"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = ""
                    ),
                    @ApiImplicitParam(
                            name = "rdnmadr"
                            , value = "소재지도로명주소"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = ""
                    ),
                    @ApiImplicitParam(
                            name = "lnmadr"
                            , value = "소재지지번주소"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = ""
                    ),
                    @ApiImplicitParam(
                            name = "pageNo"
                            , value = "페이지번호"
                            , required = false
                            , dataType = "int"
                            , paramType = "query"
                            , defaultValue = "1"
                    ),
                    @ApiImplicitParam(
                            name = "numOfRows"
                            , value = "페이지 사이즈"
                            , required = false
                            , dataType = "int"
                            , paramType = "query"
                            , defaultValue = "10"
                    ),
                    @ApiImplicitParam(
                            name = "saveYn"
                            , value = "공공 데이터 저장여부"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "N"
                    )
            }
    )
    @GetMapping(produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getShelterInfo(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "animalCnterNm", required = false) String animalCnterNm,
            @RequestParam(value = "institutionNm", required = false) String institutionNm,
            @RequestParam(value = "rdnmadr", required = false) String rdnmar,
            @RequestParam(value = "lnmadr", required = false) String lnmadr,
            @RequestParam(value = "pageNo", required = true) int pageNo,
            @RequestParam(value = "numOfRows", required = true) int numOfRows,
            @RequestParam(value = "saveYn", required = true) String saveYn
    ) throws IOException {

        AnimalShelterReqDto reqParam = AnimalShelterReqDto
                .builder()
                .type(type)
                .animalCnterNm(animalCnterNm)
                .institutionNm(institutionNm)
                .rdnmadr(rdnmar)
                .lnmadr(lnmadr)
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = animalShelterApiService.getAnimalShelterPublicApi(reqParam);

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }
}
