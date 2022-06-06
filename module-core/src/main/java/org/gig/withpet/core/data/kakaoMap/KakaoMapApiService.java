package org.gig.withpet.core.data.kakaoMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.tomcat.util.json.JSONParser;
import org.gig.withpet.core.utils.KakaoApiProperties;
import org.json.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
