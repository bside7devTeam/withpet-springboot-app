package org.gig.withpet.core.utils;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : JAKE
 * @date : 2022/05/20
 */
@Component
@RequiredArgsConstructor
public class CommonUtils {

    public static JSONObject convertXmlToJson(String xmlStr) {
        return XML.toJSONObject(xmlStr);
    }

    public static LocalDate convertStringToLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
