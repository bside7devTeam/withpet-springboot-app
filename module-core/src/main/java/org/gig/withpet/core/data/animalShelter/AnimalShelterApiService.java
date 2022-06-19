package org.gig.withpet.core.data.animalShelter;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.gig.withpet.core.data.animalShelter.dto.AnimalShelterReqDto;
import org.gig.withpet.core.utils.AnimalShelterProperties;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;

/**
 * @author : JAKE
 * @date : 2022/06/15
 */
@Log
@Service
@RequiredArgsConstructor
public class AnimalShelterApiService {

    private final AnimalShelterProperties properties;

    @Transactional
    public Map<String, Object> getAnimalShelterPublicApi(AnimalShelterReqDto reqParam) throws IOException {

        String apiPath = properties.getUrl();
        StringBuilder urlBuilder = new StringBuilder(apiPath); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + properties.getServiceKey()); /*Service Key*/
        urlBuilder.append("&type=" + reqParam.getType());
        urlBuilder.append("&pageNo=" + reqParam.getPageNo());
        urlBuilder.append("&numOfRows=" + reqParam.getNumOfRows());

        if (StringUtils.hasText(reqParam.getAnimalCnterNm())) {
            urlBuilder.append("&animalCnterNm=" + URLEncoder.encode(reqParam.getAnimalCnterNm(), "UTF-8"));
        }
        if (StringUtils.hasText(reqParam.getInstitutionNm())) {
            urlBuilder.append("&institutionNm=" + URLEncoder.encode(reqParam.getInstitutionNm(), "UTF-8"));
        }
        if (StringUtils.hasText(reqParam.getRdnmadr())) {
            urlBuilder.append("&rdnmadr=" + reqParam.getRdnmadr());
        }
        if (StringUtils.hasText(reqParam.getLnmadr())) {
            urlBuilder.append("&lnmadr=" + reqParam.getLnmadr());
        }

        String urlStr = urlBuilder.toString();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        JSONObject convertResult = new JSONObject(sb.toString());

        return convertResult.toMap();
    }
}
