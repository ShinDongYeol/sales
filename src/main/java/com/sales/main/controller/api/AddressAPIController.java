package com.sales.main.controller.api;

import com.sales.main.service.ApiService;
import com.sales.main.utils.HttpUtils;
import com.sales.main.vo.place.PlaceVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/map/api")
public class AddressAPIController {


    private ApiService service;

    @Autowired
    public void setApiService(ApiService service) {
        this.service = service;
    }

    @RequestMapping("/addr")
    public Map<String, Object> test(@RequestParam(value = "type") String type,
                                    @RequestParam(value = "query") String query) {

        Map<String, Object> resultMap = new HashMap<>();
        List<PlaceVO> placeList = null;
        try {

            if(type.equals("place")) {
                placeList = service.getPlaceInfoByName(query);
                resultMap.put("list", placeList);
            }

            resultMap.put("resultCode", 200);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resultCode", 500);

        }

        return resultMap;
    }
}
