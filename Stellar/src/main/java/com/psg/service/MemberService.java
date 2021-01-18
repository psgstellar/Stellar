package com.psg.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;

import com.psg.vo.MemberVO;

public interface MemberService {

	public boolean register(MemberVO vo) throws Exception;
	
	
//	List<MemberVO> memberlist() throws Exception;
//	MemberVO memberinfo(MemberVO vo) throws Exception;
//	void membermodify(MemberVO vo) throws Exception;
//	void memberdelete(MemberVO vo) throws Exception;
//	List<GitVO> gitnamelist(GitVO gvo) throws Exception;
	public int DupIdChk(String loginId) throws Exception;
	
	public UserDetails loadUserByUsername(String inputUserId) throws Exception;
	
	public ArrayList<MemberVO> memberList() throws Exception;
	
	public void updateRole(String username, String auth) throws Exception;
	
	
	
}
