package com.psg.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.psg.mapper.BoardMapper;
import com.psg.service.BoardService;
import com.psg.vo.BoardVO; 

@Service("BoardService")
public class BoardServiceImpl implements BoardService {
	@Resource(name="BoardMapper")
	private BoardMapper boardMapper;
	
	@Override
	public void write(BoardVO boardVO) throws Exception {
		boardMapper.write(boardVO);
	}
}
