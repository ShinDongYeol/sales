package com.sales.main.service.member;

import com.sales.main.mapper.member.MemberMapper;
import com.sales.main.vo.member.MemberVO;
import com.sales.main.vo.member.TeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    private MemberMapper mapper;

    @Autowired
    public MemberService(MemberMapper mapper) {
        this.mapper = mapper;
    }

    public List<TeamVO> teamInfo() throws Exception {
        return mapper.teamInfo();
    }

    public int searchMember(Map<String, Object> param) throws Exception{
        return mapper.searchMember(param);
    }

    public  int insertMember(MemberVO svo) throws Exception {
        return mapper.insertMember(svo);
    }
}
