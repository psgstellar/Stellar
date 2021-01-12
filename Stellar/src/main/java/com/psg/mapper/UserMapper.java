package com.psg.mapper;

import java.util.List;

import com.psg.vo.MemberVO;
import com.psg.vo.UserVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UserMapper")
public interface UserMapper {

	List<UserVO> userlist() throws Exception;
	void commitinsert(UserVO vo) throws Exception;
}
