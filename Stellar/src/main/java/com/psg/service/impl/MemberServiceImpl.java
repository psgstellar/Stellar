package com.psg.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import com.psg.mapper.MemberMapper;
//import com.psg.security.CustomUserDetails;
import com.psg.service.MemberService;
import com.psg.vo.MemberDetailsVO;
import com.psg.vo.MemberVO;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service("MemberService")
public class MemberServiceImpl implements MemberService, UserDetailsService {
	// , UserDetailsService
	
	
//	private String username;
//	private String password;
//	private String auth;
//	private int enabled;

	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Override
	public boolean register(MemberVO vo) throws Exception {
		
		Integer memberCount = memberMapper.DupIdChk(vo.getUsername());
		
		if(memberCount > 0) {
			return false;
		}else {
			vo.setPassword(bcryptPasswordEncoder.encode(vo.getPassword()));
			
			memberMapper.register(vo);
			
			return true;
		}
	}
	
	@Override
	public int DupIdChk(String loginId) throws Exception {
		return memberMapper.DupIdChk(loginId);
	}
	
	@Override
	public void updateRole(String username, String auth) throws Exception {
		memberMapper.updateRole(username, auth);
	}
	
	@Override
	public ArrayList<MemberVO> memberList() throws Exception {
		return memberMapper.memberList();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		MemberDetailsVO memberDetails = new MemberDetailsVO();
		
		MemberVO memberInfo=null;
		try {
			memberInfo = memberMapper.selectUserInfoOne(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(memberInfo == null) {
			return null;
		}else {
			memberDetails.setUsername(memberInfo.getUsername());
			memberDetails.setPassword(memberInfo.getPassword());
			memberDetails.setEmail(memberInfo.getEmail());
			
			try {
				memberDetails.setAuthorities(memberMapper.selectUserAuthOne(username));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return memberDetails;
		
	}
	
	
	
}
