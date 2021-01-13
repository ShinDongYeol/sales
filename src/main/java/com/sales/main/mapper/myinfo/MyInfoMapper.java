package com.sales.main.mapper.myinfo;

import com.sales.main.vo.myinfo.MyWorkInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MyInfoMapper {
    List<MyWorkInfoVO> getMyWorkInfo(Map<String, Object> param) throws Exception;
}
