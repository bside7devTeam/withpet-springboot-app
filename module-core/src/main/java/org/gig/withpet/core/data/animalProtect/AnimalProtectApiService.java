package org.gig.withpet.core.data.animalProtect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.gig.withpet.core.data.animalProtect.dto.*;
import org.gig.withpet.core.domain.Area.SiggArea;
import org.gig.withpet.core.domain.Area.SiggAreaRepository;
import org.gig.withpet.core.domain.adoptAnimal.AdoptAnimal;
import org.gig.withpet.core.domain.adoptAnimal.AdoptAnimalRepository;
import org.gig.withpet.core.domain.adoptAnimal.AnimalKind;
import org.gig.withpet.core.domain.adoptAnimal.AnimalKindRepository;
import org.gig.withpet.core.domain.Area.SidoArea;
import org.gig.withpet.core.domain.Area.SidoAreaRepository;
import org.gig.withpet.core.utils.AnimalProtectProperties;
import org.gig.withpet.core.utils.CommonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/05/20
 */
@Service
@Log
@RequiredArgsConstructor
public class AnimalProtectApiService {

    private final AnimalProtectProperties properties;
    private final AdoptAnimalRepository adoptAnimalRepository;
    private final AnimalKindRepository animalKindRepository;
    private final SidoAreaRepository sidoAreaRepository;
    private final SiggAreaRepository siggAreaRepository;

    @Transactional
    public Map<String, Object> getAbandonmentPublicApi(AnimalProtectReqDto reqParam, String suffixUrl) throws IOException {

        String apiPath = properties.getUrl() + suffixUrl;
        StringBuilder urlBuilder = new StringBuilder(apiPath); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + properties.getServiceKey()); /*Service Key*/
        setAbandonmentParam(urlBuilder, reqParam, suffixUrl);

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

        JSONObject convertResult = CommonUtils.convertXmlToJson(sb.toString());

        if (StringUtils.hasText(reqParam.getSaveYn()) && reqParam.getSaveYn().equals("Y")) {
            parseJsonData(suffixUrl, convertResult, reqParam);
        }

        return convertResult.toMap();
    }

    private void parseJsonData(String suffixUrl, JSONObject data, AnimalProtectReqDto reqParam) {
        JSONObject response = data.getJSONObject("response");
        JSONObject header = response.getJSONObject("header");
        if (!header.getString("resultCode").equals("00")) {
            return;
        }

        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray jsonArray = items.getJSONArray("item");

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            switch (suffixUrl) {
                case "/abandonmentPublic" :
                    List<AnimalProtectDto> animalProtectList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectDto>>(){});
                    saveAdoptAnimal(animalProtectList);
                    break;
                case "/sido" :
                    List<AnimalProtectSidoDto> sidoDtoList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectSidoDto>>(){});
                    saveSido(sidoDtoList);
                    break;
                case "/sigungu" :
                    List<AnimalProtectSiggDto> siggDtoList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectSiggDto>>() {});
                    saveSigg(siggDtoList);
                    break;
                case "/shelter" :
                    break;
                case "/kind" :
                    List<AnimalProtectKindDto> animalKindList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectKindDto>>(){});
                    saveAnimalKind(animalKindList, reqParam.getUpkind());
                    break;
                default:
                    break;
            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveAdoptAnimal(List<AnimalProtectDto> animalProtectDtoList) {

        if (CollectionUtils.isEmpty(animalProtectDtoList)) {
            return;
        }

        for (AnimalProtectDto dto : animalProtectDtoList) {
            AdoptAnimal adoptAnimal = AdoptAnimal.insertPublicData(dto);
            adoptAnimalRepository.save(adoptAnimal);
        }

    }

    private void saveAnimalKind(List<AnimalProtectKindDto> animalKindList, String upKindCd) {

        if (CollectionUtils.isEmpty(animalKindList)) {
            return;
        }

        for (AnimalProtectKindDto dto : animalKindList) {
            AnimalKind animalKind = AnimalKind.insertPublicData(dto, upKindCd);
            animalKindRepository.save(animalKind);
        }

    }

    private void saveSido(List<AnimalProtectSidoDto> sidoList) {

        if (CollectionUtils.isEmpty(sidoList)) {
            return;
        }

        for (AnimalProtectSidoDto dto : sidoList) {
            SidoArea sidoArea = SidoArea.insertPublicData(dto);
            sidoAreaRepository.save(sidoArea);
        }

    }

    private void saveSigg(List<AnimalProtectSiggDto> siggList) {

        if (CollectionUtils.isEmpty(siggList)) {
            return;
        }

        for (AnimalProtectSiggDto dto : siggList) {
            Optional<SidoArea> findSido = sidoAreaRepository.findSidoAreaByAdmCode(dto.getParentAdmCode());
            if (findSido.isEmpty()) {
                return;
            }

            SiggArea siggArea = SiggArea.insertPublicData(dto);
            siggArea.addParent(findSido.get());
            siggAreaRepository.save(siggArea);

        }

    }

    private void setAbandonmentParam(StringBuilder urlBuilder, AnimalProtectReqDto reqParam, String suffixUrl) throws UnsupportedEncodingException {

        switch (suffixUrl) {
            case "/abandonmentPublic" :
                if (reqParam.getPageNo() != null) {urlBuilder.append("&pageNo=" + reqParam.getPageNo());}
                if (reqParam.getNumOfRows() != null) {urlBuilder.append("&numOfRows=" + reqParam.getNumOfRows());}
                if (reqParam.getUpkind() != null) {urlBuilder.append("&upkind=" + reqParam.getUpkind());}
                if (reqParam.getState() != null) {urlBuilder.append("&state=" + reqParam.getState());}
                if (reqParam.getBgnde() != null) {urlBuilder.append("&bgnde=" + reqParam.getBgnde());}
                if (reqParam.getEndde() != null) {urlBuilder.append("&endde=" + reqParam.getEndde());}
                if (reqParam.getNeuterYn() != null) {urlBuilder.append("&neuter_yn=" + reqParam.getNeuterYn());}
                break;
            case "/sido":
                break;
            case "/sigungu" :
                if (reqParam.getUprCd() != null) {urlBuilder.append("&upr_cd=" + reqParam.getUprCd());}
                break;
            case "/shelter" :
                if (reqParam.getUprCd() != null) {urlBuilder.append("&upr_cd=" + reqParam.getUprCd());}
                if (reqParam.getOrgCd() != null) {urlBuilder.append("&org_cd=" + reqParam.getOrgCd());}
                break;
            case "/kind" :
                if (reqParam.getUpkind() != null) {urlBuilder.append("&up_kind_cd=" + reqParam.getUpkind());}
                break;
            default:
                break;
        }
    }
}
