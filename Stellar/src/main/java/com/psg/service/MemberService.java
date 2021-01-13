package com.psg.service;

import java.util.List;

import com.psg.vo.GitVO;
import com.psg.vo.MemberVO;

public interface MemberService {

	void memberinsert(MemberVO mvo) throws Exception;
	List<MemberVO> memberlist() throws Exception;
	MemberVO memberinfo(MemberVO vo) throws Exception;
	void membermodify(MemberVO vo) throws Exception;
	void memberdelete(MemberVO vo) throws Exception;
	List<GitVO> gitnamelist(GitVO gvo) throws Exception;
}
