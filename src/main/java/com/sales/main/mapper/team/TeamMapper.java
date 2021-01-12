package com.sales.main.mapper.team;

import com.sales.main.vo.team.TeamInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface TeamMapper {
    List<TeamInfoVO> getTeamInfo(Map<String, Object> param) throws Exception;
}
