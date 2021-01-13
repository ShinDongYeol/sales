package com.sales.main.service.team;

import com.sales.main.mapper.team.TeamMapper;
import com.sales.main.vo.team.TeamInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeamService {

    @Autowired
    TeamMapper mapper;

    public List<TeamInfoVO> getTeamInfo(Map<String, Object> param) throws Exception {
        return mapper.getTeamInfo(param);
    }
}
