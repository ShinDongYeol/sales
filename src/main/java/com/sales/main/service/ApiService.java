package com.sales.main.service;

import com.sales.main.utils.HttpUtils;
import com.sales.main.vo.place.PlaceVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    private HttpUtils httpUtils;

    @Value("${kakao.server.rest.api}")
    private String kakaoRestKey;

    @Autowired
    public void setHttpUtils(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    public List<PlaceVO> getPlaceInfoByName(String address) throws  Exception {

        String api = "KakaoAK " + kakaoRestKey;

        String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + URLEncoder.encode(address, "UTF-8");
        String result = httpUtils.get(apiURL, null, api);

        System.out.println(result);

        List<PlaceVO> list = new ArrayList<>();
        if(result !=null) {
            JSONArray arr = new JSONObject(result).getJSONArray("documents");

            for(int i =0; i < arr.length(); i++) {
                PlaceVO vo = new PlaceVO();
                JSONObject obj = (JSONObject)arr.get(i);
                vo.setPlaceName(obj.getString("place_name"));
                vo.setAddr(obj.getString("address_name"));
                vo.setCategory(obj.getString("category_name"));
                vo.setPlaceUrl(obj.getString("place_url"));
                vo.setPhone(obj.getString("phone"));
                vo.setRoadAddr(obj.getString("road_address_name"));
                vo.setX(obj.getString("x"));
                vo.setY(obj.getString("y"));
                list.add(vo);
            }
        }
        return list;
    }
}
