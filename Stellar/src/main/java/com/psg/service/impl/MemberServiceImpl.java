package com.psg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psg.mapper.MemberMapper;
import com.psg.service.MemberService;
import com.psg.vo.GitVO;
import com.psg.vo.MemberVO;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service("MemberService")
public class MemberServiceImpl implements MemberService{

	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	
	@Override
	public void memberinsert(MemberVO mvo) throws Exception {
		log.info("MemberService");
		memberMapper.memberinsert(mvo);
		
	}

	@Override
	public List<MemberVO> memberlist() throws Exception {
		
		return memberMapper.memberlist();
	}

	@Override
	public MemberVO memberinfo(MemberVO vo) throws Exception {
		log.info("서비스단 조회 전"+vo.getId());
		
		return memberMapper.memberinfo(vo);
		
	}

	@Override
	public void membermodify(MemberVO vo) throws Exception {
		memberMapper.membermodify(vo);
	}

	@Override
	public void memberdelete(MemberVO vo) throws Exception {
		memberMapper.memberdelete(vo);
		
	}

	@Override
	public List<GitVO> gitnamelist(GitVO gvo)
	throws Exception {
		
		return memberMapper.gitnamelist(gvo);
	}
	
//	@Override
//	public MemberVO idCheck(String userId) throws Exception {
//		return dao
//	}



}
