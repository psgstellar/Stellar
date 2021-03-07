package com.psg.mapper;

import com.psg.vo.BoardVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BoardMapper")
public interface BoardMapper {
	void write(BoardVO boardVO);
	
}
