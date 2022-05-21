package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.data.animalProtect.AnimalProtectReqDto;
import org.gig.withpet.core.data.kakaoMap.KakaoMapApiService;
import org.gig.withpet.core.data.kakaoMap.KakaoMapReqDto;
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
 * @date : 2022/05/21
 */
@RestController
@Api(value = "KakaoApiRestController V1")
@RequestMapping("/api/v1/kakao/map")
@RequiredArgsConstructor
public class KakaoApiRestController {

    private final KakaoMapApiService kakaoMapApiService;

    @ApiOperation(
            value = "주소 검색 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "query"
                            , value = "검색을 원하는 질의어"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "삼성동"
                    ),
                    @ApiImplicitParam(
                            name = "analyze_type"
                            , value = "검색 결과 제공 바식"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "similar"
                    ),
                    @ApiImplicitParam(
                            name = "page"
                            , value = "페이지 번호"
                            , required = true
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
                            , defaultValue = "10"
                    )
            }
    )
    @GetMapping(value = "/address", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getAnimalInfo(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "analyze_type", required = false) String analyzeType,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) throws IOException {

        KakaoMapReqDto reqParam = KakaoMapReqDto.builder()
                .query(query)
                .analyzeType(analyzeType)
                .page(page)
                .size(size)
                .build();

        Map<String, Object> resDto = kakaoMapApiService.getKakaoLocalApi(reqParam, "/v2/local/search/address.json");

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }
}
