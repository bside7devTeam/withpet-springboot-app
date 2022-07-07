package org.gig.withpet.core.data.animalProtect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.gig.withpet.core.data.animalProtect.dto.*;
import org.gig.withpet.core.domain.Area.siggArea.SiggArea;
import org.gig.withpet.core.domain.Area.siggArea.SiggAreaQueryRepository;
import org.gig.withpet.core.domain.Area.siggArea.SiggAreaRepository;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimalRepository;
import org.gig.withpet.core.data.animalProtect.adoptAnimalData.AdoptAnimalData;
import org.gig.withpet.core.data.animalProtect.adoptAnimalData.AdoptAnimalDataRepository;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.vo.AdoptAnimalVo;
import org.gig.withpet.core.domain.adoptAnimal.animalKind.AnimalKind;
import org.gig.withpet.core.domain.adoptAnimal.animalKind.AnimalKindRepository;
import org.gig.withpet.core.domain.Area.sidoArea.SidoArea;
import org.gig.withpet.core.domain.Area.sidoArea.SidoAreaRepository;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.shelter.Shelter;
import org.gig.withpet.core.domain.shelter.ShelterRepository;
import org.gig.withpet.core.utils.properties.AnimalProtectProperties;
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
import java.text.MessageFormat;
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
    private final AdoptAnimalDataRepository adoptAnimalDataRepository;
    private final AnimalKindRepository animalKindRepository;
    private final SidoAreaRepository sidoAreaRepository;
    private final SiggAreaRepository siggAreaRepository;
    private final SiggAreaQueryRepository siggAreaQueryRepository;
    private final ShelterRepository shelterRepository;

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

        if (StringUtils.hasText(reqParam.getSaveYn()) && reqParam.getSaveYn().equals("Y")
        || StringUtils.hasText(reqParam.getMappingYn()) && reqParam.getMappingYn().equals("Y")) {
            parseJsonData(suffixUrl, convertResult, reqParam, reqParam.getSaveYn(), reqParam.getMappingYn());
        }

        return convertResult.toMap();
    }

    private void parseJsonData(String suffixUrl, JSONObject data, AnimalProtectReqDto reqParam, String saveYn, String mappingYn) {
        JSONObject response = data.getJSONObject("response");
        JSONObject header = response.getJSONObject("header");
        if (!header.getString("resultCode").equals("00")) {
            return;
        }

        JSONObject body = response.getJSONObject("body");

        Object itemsObject = body.get("items");
        if (itemsObject instanceof String && !StringUtils.hasText(itemsObject.toString())) {
            return;
        }

        JSONObject items = body.getJSONObject("items");

        JSONArray jsonArray = new JSONArray();
        Object itemObject = items.get("item");
        if (itemObject instanceof JSONObject) {
            jsonArray = new JSONArray();
            jsonArray.put(itemObject);
        } else if (itemObject instanceof JSONArray) {
            jsonArray = items.getJSONArray("item");
        } else {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            switch (suffixUrl) {
                case "/abandonmentPublic":
                    List<AnimalProtectDto> animalProtectList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectDto>>() {
                    });
                    if (saveYn.equals("Y")) {
                        saveAdoptAnimalData(animalProtectList, reqParam.getUpkind(), reqParam.getCareRegNo());
                    }
                    break;
                case "/sido":
                    List<AnimalProtectSidoDto> sidoDtoList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectSidoDto>>() {
                    });
                    if (saveYn.equals("Y")) {
                        saveSido(sidoDtoList);
                    }
                    if (mappingYn.equals("Y")) {
                        mappingSido(sidoDtoList);
                    }
                    break;
                case "/sigungu":
                    List<AnimalProtectSiggDto> siggDtoList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectSiggDto>>() {
                    });
                    if (saveYn.equals("Y")) {
                        saveSigg(siggDtoList);
                    }
                    if (mappingYn.equals("Y")) {
                        mappingSigg(siggDtoList);
                    }
                    break;
                case "/shelter":
                    List<AnimalProtectShelterDto> shelterList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectShelterDto>>() {
                    });
                    if (saveYn.equals("Y")) {
                        saveShelter(shelterList, reqParam.getUprCd(), reqParam.getOrgCd());
                    }

                    break;
                case "/kind":
                    List<AnimalProtectKindDto> animalKindList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<AnimalProtectKindDto>>() {
                    });
                    if (saveYn.equals("Y")) {
                        saveAnimalKind(animalKindList, reqParam.getUpkind());
                    }

                    break;
                default:
                    break;
            }


        } catch (JsonProcessingException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void mappingSido(List<AnimalProtectSidoDto> sidoDtoList) {

        if (CollectionUtils.isEmpty(sidoDtoList)) {
            return;
        }

        for (AnimalProtectSidoDto dto : sidoDtoList) {
            Optional<SidoArea> findSido = sidoAreaRepository.findSidoAreaByAdmName(dto.getAdmName());
            if (findSido.isEmpty()) {
                log.info(MessageFormat.format("sido area {0}", dto.getAdmName()));
                continue;
            }

            SidoArea sidoArea = findSido.get();
            sidoArea.setAdoptAnimalAdmCode(dto.getAdmCode());
            sidoAreaRepository.save(sidoArea);
        }
    }

    private void mappingSigg(List<AnimalProtectSiggDto> siggDtoList) {

        if (CollectionUtils.isEmpty(siggDtoList)) {
            return;
        }

        for (AnimalProtectSiggDto dto : siggDtoList) {
            List<SiggArea> findSigg = siggAreaQueryRepository.getSiggAreaByAdmNameAndSidoParentCode(dto.getAdmName(), dto.getParentAdmCode());
            if (findSigg.isEmpty()) {
                log.info(MessageFormat.format("empty sigg Area {0} {1} {2}", dto.getAdmCode(), dto.getAdmName(), dto.getParentAdmCode()));
                continue;
            }

            if (findSigg.size() > 1) {
                log.info(MessageFormat.format("size 1 bigger sigg Area {0} {1} {2}", dto.getAdmCode(), dto.getAdmName(), dto.getParentAdmCode()));
            }

            for (SiggArea siggArea : findSigg) {
                siggArea.setAdoptAnimalAdmCode(dto.getAdmCode());
                siggAreaRepository.save(siggArea);
            }
        }
    }

    public void saveShelterInfoAll(AnimalProtectReqDto reqParam, String suffixUrl) throws IOException {

        List<SidoArea> sidoAreas = sidoAreaRepository.findAll();

        if (CollectionUtils.isEmpty(sidoAreas)) {
            return;
        }

        for (SidoArea sidoArea : sidoAreas) {
            List<SiggArea> siggAreas = siggAreaRepository.findAllBySido(sidoArea);
            if (CollectionUtils.isEmpty(siggAreas)) {
                continue;
            }

            for (SiggArea siggArea : siggAreas) {
                reqParam.setUprCd(sidoArea.getAdmCode());
                reqParam.setOrgCd(siggArea.getAdmCode());
                getAbandonmentPublicApi(reqParam, suffixUrl);
            }
        }
    }

    public void saveShelterAdoptAnimalInfo(AnimalProtectReqDto reqParam, String suffixUrl) throws IOException {

        List<Shelter> shelters = shelterRepository.findAllByDeleteYn(YnType.N);

        if (CollectionUtils.isEmpty(shelters)) {
            return;
        }

        for (Shelter shelter : shelters) {
            reqParam.setCareRegNo(shelter.getAdoptAnimalRegNo());
            getAbandonmentPublicApi(reqParam, suffixUrl);
        }
    }

    private void saveAdoptAnimalData(List<AnimalProtectDto> animalProtectDtoList, String upKindCd, String careRegNo) throws NotFoundException {

        if (CollectionUtils.isEmpty(animalProtectDtoList)) {
            return;
        }

        for (AnimalProtectDto dto : animalProtectDtoList) {

            Optional<AdoptAnimalData> findAdoptAnimalData = adoptAnimalDataRepository.findAdoptAnimalDataByNoticeNoAndDeleteYn(dto.getNoticeNo(), YnType.N);
            if (findAdoptAnimalData.isPresent() && findAdoptAnimalData.get().isNotNeedUpdate(dto)) {
                continue;
            }

            Long adoptAnimalDataId = findAdoptAnimalData.map(AdoptAnimalData::getId).orElse(null);
            AdoptAnimalData adoptAnimalData = AdoptAnimalData.insertPublicData(dto, adoptAnimalDataId);
            adoptAnimalDataRepository.save(adoptAnimalData);

            AdoptAnimalVo vo = new AdoptAnimalVo(dto, upKindCd);

            Optional<AdoptAnimal> findAdoptAnimal = adoptAnimalRepository.findAdoptAnimalByNoticeNoAndDeleteYn(vo.getNoticeNo(), YnType.N);
            if (findAdoptAnimal.isPresent()) {
                if (findAdoptAnimal.get().isNotNeedUpdate(vo)) {
                    continue;
                }
            }

            Long adoptAnimalId = findAdoptAnimal.map(AdoptAnimal::getId).orElse(null);
            AdoptAnimal adoptAnimal = AdoptAnimal.insertPublicData(vo, adoptAnimalId);
            adoptAnimalRepository.save(adoptAnimal);

            if (StringUtils.hasText(careRegNo)) {
                Optional<Shelter> findShelter = shelterRepository.findByAdoptAnimalRegNoAndDeleteYn(careRegNo, YnType.N);
                findShelter.ifPresent(adoptAnimal::setShelter);
            }
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

    private void saveShelter(List<AnimalProtectShelterDto> shelterList, String sidoCode, String siggCode) {

        if (CollectionUtils.isEmpty(shelterList)) {
            return;
        }

        for (AnimalProtectShelterDto dto : shelterList) {
            Shelter shelter = Shelter.insertPublicData(dto, sidoCode, siggCode);
            shelterRepository.save(shelter);
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
                if (reqParam.getPageNo() != null) {urlBuilder.append("&pageNo=" + reqParam.getPageNo());}
                if (reqParam.getNumOfRows() != null) {urlBuilder.append("&numOfRows=" + reqParam.getNumOfRows());}
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
