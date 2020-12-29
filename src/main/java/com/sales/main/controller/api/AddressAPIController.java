package com.sales.main.controller.api;

import com.sales.main.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class AddressAPIController {

    private HttpUtils httpUtils;

    @Autowired
    public void setHttpUtils(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @RequestMapping("/addr")
    public Map<String, Object> test() {

        Map<String, Object> resultMap = new HashMap<>();

        String api = "KakaoAK 53f4cfea53e9308630e07a7a63e9e368";
        try {

            HttpUtils utils = new HttpUtils();
            String address = "판교역로 235";
            String apiURL = "https://dapi.kakao.com/v2/local/search/address.json?query=" + URLEncoder.encode(address, "UTF-8");
            String result = utils.get(apiURL, null, api);

            System.out.println(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }
}
