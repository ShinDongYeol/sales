package com.sales.main.mapper.todolist;

import com.sales.main.vo.place.PlaceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TodoListMapper {

    int insertTodoList(PlaceVO svo) throws Exception;
}
