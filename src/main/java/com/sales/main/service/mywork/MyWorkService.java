package com.sales.main.service.mywork;

import com.sales.main.mapper.mywork.MyWorkMapper;
import com.sales.main.vo.StatusCodeVO;
import com.sales.main.vo.place.PlaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyWorkService {

    private MyWorkMapper mapper;

    @Autowired
    public void setMyWorkMapper(MyWorkMapper mapper) {
        this.mapper = mapper;
    }

    public List<StatusCodeVO> getStatus() throws Exception {
        return mapper.getStatus();
    }


    public  List<PlaceVO> selectTodoList(Map<String, Object> param) throws Exception{
        return mapper.selectTodoList(param);
    }

    public int updateWork(Map<String, Object> param) throws Exception {
        return mapper.updateWork(param);
    }

    public int delWork(Map<String, Object> param) throws Exception {
        return mapper.delWork(param);
    }


}
