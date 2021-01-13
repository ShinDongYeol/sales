package com.sales.main.service.myinfo;

import com.sales.main.mapper.member.MemberMapper;
import com.sales.main.mapper.myinfo.MyInfoMapper;
import com.sales.main.vo.member.MemberVO;
import com.sales.main.vo.member.TeamVO;
import com.sales.main.vo.myinfo.MyWorkInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyInfoService {

    @Autowired
    private MyInfoMapper infoMapper;

    @Autowired
    private MemberMapper memMapper;

    public List<MyWorkInfoVO> getMyWorkInfo(Map<String, Object> param) throws Exception {
        return infoMapper.getMyWorkInfo(param);
    }

    public  List<TeamVO> teamInfo() throws Exception {
        return memMapper.teamInfo();
    }

    public MemberVO getMemberInfo(Map<String, Object> param) throws Exception {
        return memMapper.getMemberInfo(param);
    }
}
