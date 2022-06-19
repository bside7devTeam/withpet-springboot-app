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
            @RequestParam(value = "query", required = false) String address,
            @RequestParam(value = "saveYn", required = true) String saveYn
    ) throws IOException {

        VWorldAddressReqDto reqParam = VWorldAddressReqDto
                .builder()
                .address(address)
                .saveYn(saveYn)
                .build();

        Map<String, Object> resDto = vWorldApiService.getAddressVWorldApi(reqParam);

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }

    @ApiOperation(
            value = "행정구역 정보 저장 API"
    )
    @PostMapping()
    public ResponseEntity<ApiResponse> saveAddressInfo() throws IOException {

        Map<String, Object> resDto = vWorldApiService.saveAddressVWorldApi();

        return new ResponseEntity<>(ApiResponse.OK(resDto), HttpStatus.OK);
    }
}
