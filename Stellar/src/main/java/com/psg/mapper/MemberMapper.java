package com.psg.mapper;

import java.util.List;
import com.psg.vo.MemberVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MemberMapper")
public interface MemberMapper {

	void register(MemberVO vo) throws Exception;
	
	int DupIdChk(String loginId) throws Exception;
	
	MemberVO selectUserInfoOne(String username) throws Exception;
	
	List<String> selectUserAuthOne(String username) throws Exception;
}
