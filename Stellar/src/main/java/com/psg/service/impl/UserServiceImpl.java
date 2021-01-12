package com.psg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psg.mapper.UserMapper;
import com.psg.service.UserService;
import com.psg.vo.MemberVO;
import com.psg.vo.UserVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("UserService")
public class UserServiceImpl implements UserService{

	@Resource(name="UserMapper")
	public UserMapper userMapper;
	
	@Override
	public List<UserVO> userlist() throws Exception {
		log.info("UserServiceImpl");
		return userMapper.userlist();
	}

	@Override
	public void commitinsert(UserVO vo) throws Exception {
		log.info(vo.getAuthor_name()+vo.getText());
		userMapper.commitinsert(vo);
	}

}
