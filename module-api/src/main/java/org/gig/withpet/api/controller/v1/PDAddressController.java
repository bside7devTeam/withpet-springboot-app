package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.data.vWorldAddress.VWorldApiService;
import org.gig.withpet.core.data.vWorldAddress.dto.VWorldAddressReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author : JAKE
 * @date : 2022/06/19
 */
@RestController
@Api(value = "PDAddressController V1")
@RequestMapping("/v-world/address")
@RequiredArgsConstructor
public class PDAddressController {

    private final VWorldApiService vWorldApiService;

    @ApiOperation(
            value = "행정구역 정보 조회 API"
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "query"
                            , value = "키워드"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "서울"
                    ),
                    @ApiImplicitParam(
                            name = "category"
                            , value = "카테고리"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "L4"
                    ),
                    @ApiImplicitParam(
                            name = "saveYn"
                            , value = "데이터 저장여부"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "N"
                    )
            }
    )
    @GetMapping(produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getAddressInfo(
            @RequestParam(value = "category", required = true) String category,
            @RequestParam(value = "query", required = true) String address,
            @RequestParam(value = "saveYn", required = true) String saveYn
    ) throws IOException {

        VWorldAddressReqDto reqParam = VWorldAddressReqDto
                .builder()
                .category(category)
                .address(address)
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = vWorldApiService.getAddressVWorldApi(reqParam);

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "행정구역 정보 시도 데이터 저장 API"
    )
    @PostMapping("/sido")
    public ResponseEntity<ApiResponse> saveAddressSidoInfo() throws IOException {

        Map<String, Object> resDto = vWorldApiService.saveSidoAddressVWorldApi();

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "행정구역 정보 시군구 데이터 저장 API"
    )
    @PostMapping("/sigg")
    public ResponseEntity<ApiResponse> saveAddressSiggInfo() throws IOException {

        Map<String, Object> resDto = vWorldApiService.saveSiggAddressVWorldApi();

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "행정구역 정보 동데이터 API"
    )
    @PostMapping("/emd")
    public ResponseEntity<ApiResponse> saveAddressEmdInfo() throws IOException {

        Map<String, Object> resDto = vWorldApiService.saveEmdAddressVWorldApi();

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }
}
