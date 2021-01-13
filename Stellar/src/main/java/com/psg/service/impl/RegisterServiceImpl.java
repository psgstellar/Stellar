package com.psg.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psg.mapper.RegisterMapper;
import com.psg.service.RegisterService;

@Service("RegisterService")
public class RegisterServiceImpl implements RegisterService {
	
	@Resource(name = "RegisterMapper")
	private RegisterMapper registerMapper;
	
	@Override
	public int DupIdChk(String loginId) throws Exception {
		return registerMapper.DupIdChk(loginId);
	}
	

}
