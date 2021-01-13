package com.sales.main.mapper.member;

import com.sales.main.vo.member.MemberVO;
import com.sales.main.vo.member.TeamVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MemberMapper {
    List<TeamVO> teamInfo() throws Exception;
    int searchMember(Map<String, Object> param) throws Exception;
    int insertMember(MemberVO svo) throws Exception;
    MemberVO getMemberInfo(Map<String, Object> param) throws Exception;
}
