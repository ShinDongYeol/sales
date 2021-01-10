package com.sales.main.service.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.main.mapper.todolist.TodoListMapper;
import com.sales.main.vo.place.PlaceVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoListService {


    private TodoListMapper mapper;

    @Autowired
    public TodoListService(TodoListMapper mapper) {
        this.mapper = mapper;
    }


    @Transactional
    public int insertTodoList(String nowDate, String dataList)  throws  Exception {

        JSONArray arr= new JSONArray(dataList);
        List<PlaceVO> list = new ArrayList<>();

        ObjectMapper objMapper = new ObjectMapper();
        for(int i =0; i < arr.length() ; i ++ ){
            JSONObject obj = (JSONObject) arr.get(i);
            PlaceVO vo =  objMapper.readValue(obj.toString(), PlaceVO.class);
            list.add(vo);
        }

        int result = 0;
        for(PlaceVO vo  : list )  {
            vo.setTargetDate(nowDate);
            vo.setEmpId("aa");

            result = mapper.insertTodoList(vo);

            if(result < 0) {
                throw new Exception("insert Error");
            }
        }

        return result;
    }
}
