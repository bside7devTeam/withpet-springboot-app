package org.gig.withpet.core.data.animalProtect;

import lombok.Getter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/06/01
 */
@Getter
public class AnimalProtectListDto {

    private int pageNo;

    private int numOfRows;

    private int totalCount;

    private List<JSONObject> items = new ArrayList<>();
}
