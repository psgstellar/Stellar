package com.psg.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

import com.psg.mapper.CommitMapper;
import com.psg.mapper.MemberMapper;
//import com.psg.security.CustomUserDetails;
import com.psg.service.MemberService;
import com.psg.vo.GithubVO;
import com.psg.vo.MemberDetailsVO;
import com.psg.vo.MemberVO;
import com.psg.vo.RestVO;
import com.psg.vo.SlackVO;

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
	
	@Resource(name="CommitMapper")
	private CommitMapper commitMapper;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Override
	public boolean register(MemberVO vo) throws Exception {
		
		Integer memberCount = memberMapper.DupIdChk(vo.getUsername());
//		Integer slackCount = commitMapper.DupSlackChk(vo.getSlack_name());
//		Integer githubCount = commitMapper.DupGithubChk(vo.getGithub_name());
		
		// && slackCount > 0 && githubCount > 0
		
		
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
	public int DupSlackChk(String slack_name) throws Exception {
		return commitMapper.DupSlackChk(slack_name);
	}
	
	@Override
	public int DupGithubChk(String github_name) throws Exception {
		return commitMapper.DupGithubChk(github_name);
	}
	
	@Override
	public int DupRestChk(String username, Date start_date, Date end_date) throws Exception {
		return commitMapper.DupRestChk(username, start_date, end_date);
	}
	
	@Override
	public void updateRole(String username, String auth) throws Exception {
		memberMapper.updateRole(username, auth);
	}
	
	@Override
	public void delete_rest(String username, Date start_date, Date end_date) throws Exception {
		commitMapper.delete_rest(username, start_date, end_date);
	}
	
	@Override
	public void put_rest(String username, Date start_date, Date end_date) throws Exception {
		commitMapper.put_rest(username, start_date, end_date);
	}
	
	@Override
	public void delete_slack_info(String username, String slack_name) throws Exception {
		commitMapper.delete_slack_info(username, slack_name);
	}
	
	@Override
	public void put_slack_info(String username, String slack_name) throws Exception {
		commitMapper.put_slack_info(username, slack_name);
	}
	
	@Override
	public void delete_github_info(String username, String github_name) throws Exception {
		commitMapper.delete_github_info(username, github_name);
	}
	
	@Override
	public void put_github_info(String username, String github_name) throws Exception {
		commitMapper.put_github_info(username, github_name);
	}
	
	@Override
	public ArrayList<MemberVO> memberList() throws Exception {
		return memberMapper.memberList();
	}
	
	@Override
	public ArrayList<RestVO> get_rest() throws Exception {
		return commitMapper.get_rest();
	}
	
	@Override
	public ArrayList<RestVO> get_member_rest(String username) throws Exception {
		return commitMapper.get_member_rest(username);
	}
	
	@Override
	public ArrayList<GithubVO> get_github_info(String username) throws Exception {
		return commitMapper.get_github_info(username);
	}
	
	@Override
	public ArrayList<SlackVO> get_slack_info(String username) throws Exception {
		return commitMapper.get_slack_info(username);
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
