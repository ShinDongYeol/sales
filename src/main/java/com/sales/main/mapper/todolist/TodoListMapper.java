package com.sales.main.mapper.todolist;

import com.sales.main.vo.StatusCodeVO;
import com.sales.main.vo.place.PlaceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface TodoListMapper {

    int insertTodoList(PlaceVO svo) throws Exception;

}
