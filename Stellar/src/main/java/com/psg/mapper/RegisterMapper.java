package com.psg.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RegisterMapper")
public interface RegisterMapper {
	int DupIdChk(String loginId) throws Exception;

}
