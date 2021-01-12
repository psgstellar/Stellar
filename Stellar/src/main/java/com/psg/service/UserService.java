package com.psg.service;

import java.util.List;

import com.psg.vo.MemberVO;
import com.psg.vo.UserVO;

public interface UserService {

	List<UserVO> userlist() throws Exception;
	void commitinsert(UserVO vo) throws Exception;
}
