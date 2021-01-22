package com.psg.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import com.psg.vo.GithubVO;
import com.psg.vo.MemberVO;
import com.psg.vo.RestVO;
import com.psg.vo.SlackVO;

public interface MemberService {

	public boolean register(MemberVO vo) throws Exception;
	
	
//	List<MemberVO> memberlist() throws Exception;
//	MemberVO memberinfo(MemberVO vo) throws Exception;
//	void membermodify(MemberVO vo) throws Exception;
//	void memberdelete(MemberVO vo) throws Exception;
//	List<GitVO> gitnamelist(GitVO gvo) throws Exception;
	public int DupIdChk(String loginId) throws Exception;
	
	public int DupSlackChk(String slack_name) throws Exception;
	
	public int DupGithubChk(String github_name) throws Exception;
	
	public int DupRestChk(String username, Date start_date, Date end_date) throws Exception;
	
	public UserDetails loadUserByUsername(String inputUserId) throws Exception;
	
	public ArrayList<MemberVO> memberList() throws Exception;
	
	public ArrayList<RestVO> get_rest() throws Exception;
	
	public ArrayList<RestVO> get_member_rest(String username) throws Exception;
	
	public ArrayList<GithubVO> get_github_info(String username) throws Exception;
	
	public ArrayList<SlackVO> get_slack_info(String username) throws Exception;
	
 	public void updateRole(String username, String auth) throws Exception;
 	
 	public void delete_rest(String username, Date start_date, Date end_date) throws Exception;
 	
 	public void put_rest(String username, Date start_date, Date end_date) throws Exception;
 	
	public void delete_slack_info(String username, String slack_name) throws Exception;
	
	public void put_slack_info(String username, String slack_name) throws Exception;
	
	public void delete_github_info(String username, String github_name) throws Exception;
	
	public void put_github_info(String username, String github_name) throws Exception;
	
}
