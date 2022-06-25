package org.gig.withpet.core.data.vWorldAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.gig.withpet.core.data.vWorldAddress.dto.AddressResDto;
import org.gig.withpet.core.data.vWorldAddress.dto.VWorldAddressReqDto;
import org.gig.withpet.core.domain.Area.emdArea.EmdArea;
import org.gig.withpet.core.domain.Area.emdArea.EmdAreaRepository;
import org.gig.withpet.core.domain.Area.sidoArea.SidoArea;
import org.gig.withpet.core.domain.Area.sidoArea.SidoAreaRepository;
import org.gig.withpet.core.domain.Area.siggArea.SiggArea;
import org.gig.withpet.core.domain.Area.siggArea.SiggAreaQueryRepository;
import org.gig.withpet.core.domain.Area.siggArea.SiggAreaRepository;
import org.gig.withpet.core.utils.CommonUtils;
import org.gig.withpet.core.utils.VWorldAddressProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/06/19
 */
@Log
@Service
@RequiredArgsConstructor
public class VWorldApiService {

    private final VWorldAddressProperties properties;
    private final SidoAreaRepository sidoAreaRepository;
    private final SiggAreaRepository siggAreaRepository;
    private final SiggAreaQueryRepository siggAreaQueryRepository;
    private final EmdAreaRepository emdAreaRepository;

    @Transactional
    public Map<String, Object> getAddressVWorldApi(VWorldAddressReqDto reqParam) throws IOException {
        String apiPath = properties.getUrl();
        StringBuilder urlBuilder = new StringBuilder(apiPath); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("key","UTF-8") + "=" + properties.getServiceKey()); /*Service Key*/
        urlBuilder.append("&service=search");
        urlBuilder.append("&request=search");
        urlBuilder.append("&version=2.0");
        urlBuilder.append("&page=1");
        urlBuilder.append("&size=1000");
        urlBuilder.append("&type=district");
        urlBuilder.append("&category=" + reqParam.getCategory());
        urlBuilder.append("&format=json");
        urlBuilder.append("&errorformat=json");
        urlBuilder.append("&query=" + URLEncoder.encode(reqParam.getAddress(), "UTF-8"));

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

        if (!CommonUtils.isJSONValid(sb.toString())) {
            return null;
        }

        JSONObject convertResult = new JSONObject(sb.toString());

        if (StringUtils.hasText(reqParam.getSaveYn()) && reqParam.getSaveYn().equals("Y")) {
            parseJsonData(convertResult, reqParam.getCategory());
        }

        return convertResult.toMap();
    }

    private void parseJsonData(JSONObject data, String category) throws JsonProcessingException {
        JSONObject response = data.getJSONObject("response");
        String status = response.getString("status");

        if (!StringUtils.hasText(status) || !status.equals("OK")) {
            return;
        }

        JSONObject result = response.getJSONObject("result");

        JSONArray items = result.getJSONArray("items");

        ObjectMapper objectMapper = new ObjectMapper();
        List<AddressResDto> addressDataList = objectMapper.readValue(items.toString(), new TypeReference<List<AddressResDto>>() {
        });

        if (CollectionUtils.isEmpty(addressDataList)) {
            return;
        }

        switch(category) {
            case "L1":
                saveSidoArea(addressDataList);
                break;
            case "L2":
                saveSiggArea(addressDataList);
                break;
            case "L3":
                break;
            case "L4":
                saveEmdArea(addressDataList);
                break;
            default:
                break;
        }



    }

    public Map<String, Object> saveSidoAddressVWorldApi() throws IOException {

        String sidoAddress = "서울특별시,부산광역시,대구광역시,인천광역시,광주광역시,세종특자치시,대전광역시,"
                + "울산광역시,경기도,강원도,충청북도,충청남도,전라북도,전라남도,경상북도,경상남도,제주특별자치도";

        String[] areas = sidoAddress.split(",");

        for (String sidoName : areas) {
            VWorldAddressReqDto reqParam = VWorldAddressReqDto
                    .builder()
                    .address(sidoName)
                    .category("L1")
                    .saveYn("Y")
                    .build();

            getAddressVWorldApi(reqParam);
        }

        return null;
    }

    public Map<String, Object> saveSiggAddressVWorldApi() throws IOException {

        List<SidoArea> sidoAreas = sidoAreaRepository.findAll();

        for (SidoArea sidoArea : sidoAreas) {
            VWorldAddressReqDto reqParam = VWorldAddressReqDto
                    .builder()
                    .address(sidoArea.getAdmName())
                    .category("L2")
                    .saveYn("Y")
                    .build();

            getAddressVWorldApi(reqParam);
        }

        return null;
    }

    public Map<String, Object> saveEmdAddressVWorldApi() throws IOException {

        List<SiggArea> siggAreas = siggAreaQueryRepository.getSiggAreaList();

        for (SiggArea siggArea : siggAreas) {
            VWorldAddressReqDto reqParam = VWorldAddressReqDto
                    .builder()
                    .address(siggArea.getSido().getAdmName() + " " + siggArea.getAdmName())
                    .category("L4")
                    .saveYn("Y")
                    .build();

            getAddressVWorldApi(reqParam);
        }

        return null;
    }

    private void saveSidoArea(List<AddressResDto> addressDataList) {
        for (AddressResDto address : addressDataList) {
            SidoArea sidoArea = SidoArea.insertVWorldData(address);
            sidoAreaRepository.save(sidoArea);
        }
    }

    private void saveSiggArea(List<AddressResDto> addressDataList) {
        for (AddressResDto address : addressDataList) {

            String[] strArray = address.getTitle().split(" ");

            Optional<SidoArea> findSidoArea = sidoAreaRepository.findSidoAreaByAdmName(strArray[0]);

            if (findSidoArea.isEmpty()) {
                log.info( MessageFormat.format("sigg Error {0}", address.getTitle()));
                continue;
            }

            StringBuilder admName = new StringBuilder();

            for (int i=0; i< strArray.length; i++) {
                if (i > 0) {
                    admName.append(strArray[i]);
                    if (i < strArray.length-1) {
                        admName.append(" ");
                    }
                }
            }

            SiggArea siggArea = SiggArea.insertVWorldData(address, admName.toString());
            siggArea.addParent(findSidoArea.get());
            siggAreaRepository.save(siggArea);
        }
    }

    private void saveEmdArea(List<AddressResDto> addressDataList) {
        for (AddressResDto address : addressDataList) {
            String[] strArray = address.getTitle().split(" ");

            StringBuilder admName = new StringBuilder();

            for (int i=0; i< strArray.length; i++) {
                if (i > 0 && i < strArray.length-1) {
                    admName.append(strArray[i]);
                    if (i < strArray.length-2) {
                        admName.append(" ");
                    }
                }
            }

            List<SiggArea> findSiggArea = siggAreaQueryRepository.getSiggAreaByAdmNameAndSido(strArray[0], admName.toString());

            if (findSiggArea.isEmpty()) {
                log.info( MessageFormat.format("sigg Error {0} {1} {2}", strArray[0], strArray[1], strArray[2]));
                continue;
            }

            if (findSiggArea.size() > 1) {
                log.info( MessageFormat.format("sigg Error {0} {1} {2}", strArray[0], strArray[1], strArray[2]));
            }

            for (SiggArea siggArea : findSiggArea) {
                EmdArea emdArea = EmdArea.insertPublicData(address, strArray[strArray.length-1]);
                emdArea.addParent(siggArea);
                emdAreaRepository.save(emdArea);
            }
        }
    }
}
