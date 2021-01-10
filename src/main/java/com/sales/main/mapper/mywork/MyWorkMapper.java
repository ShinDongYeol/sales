package com.sales.main.mapper.mywork;

import com.sales.main.vo.StatusCodeVO;
import com.sales.main.vo.place.PlaceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MyWorkMapper {

    List<StatusCodeVO> getStatus() throws Exception;
    List<PlaceVO> selectTodoList(Map<String, Object> param) throws Exception;
    int updateWork(Map<String, Object> param) throws Exception;
    int delWork(Map<String, Object> param) throws Exception;
}
