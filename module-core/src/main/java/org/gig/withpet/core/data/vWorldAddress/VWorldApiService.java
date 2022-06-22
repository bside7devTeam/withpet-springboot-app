package org.gig.withpet.core.data.vWorldAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectDto;
import org.gig.withpet.core.data.vWorldAddress.dto.AddressResDto;
import org.gig.withpet.core.data.vWorldAddress.dto.VWorldAddressReqDto;
import org.gig.withpet.core.domain.Area.emdArea.EmdArea;
import org.gig.withpet.core.domain.Area.emdArea.EmdAreaRepository;
import org.gig.withpet.core.domain.Area.siggArea.SiggArea;
import org.gig.withpet.core.domain.Area.siggArea.SiggAreaRepository;
import org.gig.withpet.core.utils.VWorldAddressProperties;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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

    private final SiggAreaRepository siggAreaRepository;

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
        urlBuilder.append("&category=L4");
        urlBuilder.append("&format=json");
        urlBuilder.append("&errorformat=json");
        urlBuilder.append("&query=" + reqParam.getAddress());

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

        if (StringUtils.hasText(reqParam.getSaveYn()) && reqParam.getSaveYn().equals("Y")) {
            parseJsonData(convertResult);
        }

        return convertResult.toMap();
    }

    private void parseJsonData(JSONObject data) throws JsonProcessingException {
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

        for (AddressResDto address : addressDataList) {
            String[] strArray = address.getTitle().split(" ");

            Optional<SiggArea> findSiggArea = siggAreaRepository.findSiggAreaByAdmName(strArray[1]);

            if (findSiggArea.isEmpty()) {
                log.info("sigg error " + strArray[1]);
                continue;
            }

            EmdArea emdArea = EmdArea.insertPublicData(address, strArray[2]);
            emdArea.addParent(findSiggArea.get());
            emdAreaRepository.save(emdArea);

        }

    }

    public Map<String, Object> saveAddressVWorldApi() throws IOException {

        List<SiggArea> siggAreas = siggAreaRepository.findAll();

        for (SiggArea siggArea : siggAreas) {
            VWorldAddressReqDto reqParam = VWorldAddressReqDto
                    .builder()
                    .address(siggArea.getSido().getAdmName() + " " + siggArea.getAdmName())
                    .saveYn("Y")
                    .build();

            getAddressVWorldApi(reqParam);
        }

        return null;
    }
}
