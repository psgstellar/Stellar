package com.psg.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.psg.mapper.MemberMapper;
//import com.psg.security.CustomUserDetails;
import com.psg.service.MemberService;
import com.psg.vo.MemberDetailsVO;
import com.psg.vo.MemberVO;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service("MemberService")
public class MemberServiceImpl implements MemberService, UserDetailsService{
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
	public void register(MemberVO vo) throws Exception {
		
		String encodedPassword = bcryptPasswordEncoder.encode(vo.getPassword()); 
		
		vo.setPassword(encodedPassword);
		
		memberMapper.register(vo);
	}
	
	@Override
	public int DupIdChk(String loginId) throws Exception {
		return memberMapper.DupIdChk(loginId);
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
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		MemberVO users = null;
//		try {
//			users = memberMapper.getUserById(username);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(users == null) {
//			throw new UsernameNotFoundException("username" + username + " not found.");
//		}
//		
//		return users;
//	}

	
	
}
