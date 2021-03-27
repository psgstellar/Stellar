package com.psg.service;

import java.util.ArrayList;

import com.psg.vo.CommitVO;
import com.psg.vo.MemberVO;

public interface CommitService {
	public ArrayList<CommitVO> request_commit_list() throws Exception;
	public ArrayList<MemberVO> commit_check(ArrayList<CommitVO> commitList) throws Exception;
}
