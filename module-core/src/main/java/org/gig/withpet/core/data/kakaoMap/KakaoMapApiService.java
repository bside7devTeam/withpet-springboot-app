package org.gig.withpet.core.data.kakaoMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.gig.withpet.core.data.vWorldAddress.dto.AddressResDto;
import org.gig.withpet.core.domain.common.dto.response.AddressResponse;
import org.gig.withpet.core.utils.properties.KakaoApiProperties;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : JAKE
 * @date : 2022/05/21
 */
@Service
@Transactional(readOnly = true)
@Log
@RequiredArgsConstructor
public class KakaoMapApiService {

    private final KakaoApiProperties properties;

    public Map<String, Object> getKakaoLocalApi(KakaoMapReqDto reqParam, String suffixUrl) {

        String apiPath = properties.getUrl() + suffixUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", properties.getRestApiKey());

        UriComponents uriComponents = setKakaoApiParam(reqParam, apiPath, suffixUrl); // 인코딩 하지않음
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // api 호출 타임아웃
        factory.setReadTimeout(5000);   // api 읽기 타임아웃

        RestTemplate template = new RestTemplate(factory);

        ResponseEntity<String> response = template.exchange(uriComponents.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        if (response.getStatusCodeValue() == 200) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            return jsonObject.toMap();
        }

        return null;
    }

    @Transactional(readOnly = true)
    public AddressResponse getAddressByCoord(String latitude, String longitude) {

        KakaoMapReqDto reqParam = KakaoMapReqDto.builder()
                .x(latitude)
                .y(longitude)
                .inputCoord("WGS84")
                .build();

        Map<String, Object> data = getKakaoLocalApi(reqParam, "/v2/local/geo/coord2address.json");

        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) data.get("documents");

        if (CollectionUtils.isEmpty(list)) {
            throw new InvalidParameterException("해당 좌표에 대한 주소가 없습니다.");
        }

        if (list.size() > 1) {
            throw new InvalidParameterException("해당 좌표에 대한 주소가 2개 이상 조회됩니다.");
        }

        HashMap<String, String> map = (HashMap<String, String>) list.get(0).get("address");
        return new AddressResponse(map.get("region_1depth_name"), map.get("region_2depth_name"), map.get("region_3depth_name"));
    }

    private UriComponents setKakaoApiParam(KakaoMapReqDto reqParam, String apiPath, String suffixUrl) {

        switch (suffixUrl) {
            case "/v2/local/search/address.json":
                return UriComponentsBuilder.fromHttpUrl(apiPath)
                        .queryParam("query", reqParam.getQuery())
                        .queryParam("analyze_type", reqParam.getAnalyzeType())
                        .queryParam("page", reqParam.getPage())
                        .queryParam("size", reqParam.getSize())
                        .build(false);
            case "/v2/local/geo/coord2regioncode.json" :
                return UriComponentsBuilder.fromHttpUrl(apiPath)
                        .queryParam("x", reqParam.getX())
                        .queryParam("y", reqParam.getY())
                        .queryParam("input_coord", reqParam.getInputCoord())
                        .queryParam("output_coord", reqParam.getOutputCoord())
                        .build(false);
            case "/v2/local/geo/coord2address.json" :
                return UriComponentsBuilder.fromHttpUrl(apiPath)
                        .queryParam("x", reqParam.getX())
                        .queryParam("y", reqParam.getY())
                        .queryParam("input_coord", reqParam.getInputCoord())
                        .build(false);
            default:
                break;
        }

        return null;
    }

}
