package com.sales.main.service.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.main.vo.place.PlaceVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoListService {



    public int insertTodoList(String nowDate, String dataList)  throws  Exception {

        JSONArray arr= new JSONArray(dataList);
        List<PlaceVO> list = new ArrayList<>();

        ObjectMapper objMapper = new ObjectMapper();
        for(int i =0; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            PlaceVO vo =  objMapper.readValue(obj.toString(), PlaceVO.class);
            list.add(vo);
        }

        for(PlaceVO vo  : list )  {

        }

        return 1;
    }
}
