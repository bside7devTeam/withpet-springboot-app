package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.data.animalProtect.AnimalProtectApiService;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author : JAKE
 * @date : 2022/05/20
 */
@RestController
@Api(value = "PublicDataRestController V1")
@RequestMapping("/api/v1/public-data/abandonment-public-srvc")
@RequiredArgsConstructor
public class PublicDataRestController {

    private final AnimalProtectApiService animalProtectApiService;

    @ApiOperation(
            value = "입양 동물 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "bgnde"
                            , value = "유기날짜 시작일"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "20220101"
                    ),
                    @ApiImplicitParam(
                            name = "endde"
                            , value = "유기날짜 종료일"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "20220630"
                    ),
                    @ApiImplicitParam(
                            name = "upkind"
                            , value = "축종코드"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "417000"
                    )
                    ,
                    @ApiImplicitParam(
                            name = "state"
                            , value = "상태"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "notice"
                    ),
                    @ApiImplicitParam(
                            name = "neuter_yn"
                            , value = "중성화여부"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "Y"
                    ),
                    @ApiImplicitParam(
                            name = "pageNo"
                            , value = "페이지 번호"
                            , required = true
                            , dataType = "int"
                            , paramType = "query"
                            , defaultValue = "1"
                    ),
                    @ApiImplicitParam(
                            name = "numOfRows"
                            , value = "페이지 사이즈"
                            , required = true
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
    @GetMapping(value = "/abandonmentPublic", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getAnimalInfo(
            @RequestParam(value = "bgnde", required = false) String bgnde,
            @RequestParam(value = "endde", required = false) String endde,
            @RequestParam(value = "neuter_yn", required = false) String neuterYn,
            @RequestParam(value = "upkind", required = false) String upkind,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "numOfRows", required = false) Integer numOfRows,
            @RequestParam(value = "saveYn", required = true) String saveYn
    ) throws IOException {

        AnimalProtectReqDto reqParam = AnimalProtectReqDto.builder()
                .bgnde(bgnde)
                .endde(endde)
                .neuterYn(neuterYn)
                .upkind(upkind)
                .state(state)
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = animalProtectApiService.getAbandonmentPublicApi(reqParam, "/abandonmentPublic");

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "입양 동물 시도 정보 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "saveYn"
                            , value = "공공 데이터 저장여부"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "N"
                    ),
            }
    )
    @GetMapping(value = "/sido", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getCityInfo(
            @RequestParam(value = "saveYn", required = true) String saveYn) throws IOException {

        AnimalProtectReqDto reqParam = AnimalProtectReqDto.builder()
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = animalProtectApiService.getAbandonmentPublicApi(reqParam, "/sido");

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "입양 동물 시군구 정보 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "upr_cd"
                            , value = "시군구 상위코드(시도 코드)"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "6110000"
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
    @GetMapping(value = "/sigungu", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getTownInfo(
            @RequestParam(value = "upr_cd", required = false) String uprCd,
            @RequestParam(value = "saveYn", required = true) String saveYn
    ) throws IOException {

        AnimalProtectReqDto reqParam = AnimalProtectReqDto.builder()
                .uprCd(uprCd)
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = animalProtectApiService.getAbandonmentPublicApi(reqParam, "/sigungu");

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "입양 동물 보호소 정보 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "upr_cd"
                            , value = "시군구 상위코드(시도 코드)"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "6110000"
                    ),
                    @ApiImplicitParam(
                            name = "org_cd"
                            , value = "시군구 코드"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "3220000"
                    ),
                    @ApiImplicitParam(
                            name = "saveYn"
                            , value = "공공 데이터 저장여부"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "N"
                    ),
            }
    )
    @GetMapping(value = "/shelter", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getShelterInfo(
            @RequestParam(value = "upr_cd", required = false) String uprCd,
            @RequestParam(value = "org_cd", required = false) String orgCd,
            @RequestParam(value = "saveYn", required = true) String saveYn
    ) throws IOException {

        AnimalProtectReqDto reqParam = AnimalProtectReqDto.builder()
                .uprCd(uprCd)
                .orgCd(orgCd)
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = animalProtectApiService.getAbandonmentPublicApi(reqParam, "/shelter");

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "입양 동물 품종 정보 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "up_kind_cd"
                            , value = "축종코드"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "417000"
                    ),
                    @ApiImplicitParam(
                            name = "saveYn"
                            , value = "공공 데이터 저장여부"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "N"
                    ),
            }
    )
    @GetMapping(value = "/kind", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getKindInfo(
            @RequestParam(value = "up_kind_cd", required = false) String upkind,
            @RequestParam(value = "saveYn", required = true) String saveYn
    ) throws IOException {

        AnimalProtectReqDto reqParam = AnimalProtectReqDto.builder()
                .upkind(upkind)
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = animalProtectApiService.getAbandonmentPublicApi(reqParam, "/kind");

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "입양 동물 보호소 정보 저장 API"
    )
    @PostMapping(value = "/shelter", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> saveAllShelterInfo(
    ) throws IOException {

        AnimalProtectReqDto reqParam = AnimalProtectReqDto.builder()
                .saveYn("Y")
                .build();

        animalProtectApiService.saveShelterInfoAll(reqParam, "/shelter");

        return new ResponseEntity<>(ApiResponse.OK("Success"), HttpStatus.OK);
    }
}

