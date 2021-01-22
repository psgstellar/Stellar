package com.psg.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.psg.vo.GithubVO;
import com.psg.vo.RestVO;
import com.psg.vo.SlackVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CommitMapper")
public interface CommitMapper {
	
	int DupSlackChk(String slack_name) throws Exception;
	
	int DupGithubChk(String github_name) throws Exception;
	
	int DupRestChk(@Param("username") String username, @Param("start_date") Date start_date, @Param("end_date") Date end_date) throws Exception;
	
	ArrayList<RestVO> get_rest() throws Exception;
	
	ArrayList<RestVO> get_member_rest(String username) throws Exception;
	
	ArrayList<GithubVO> get_github_info(String username) throws Exception;
	
	ArrayList<SlackVO> get_slack_info(String username) throws Exception;
	
	void delete_rest(@Param("username") String username, @Param("start_date") Date start_date, @Param("end_date") Date end_date) throws Exception;
	
	void put_rest(@Param("username") String username, @Param("start_date") Date start_date, @Param("end_date") Date end_date) throws Exception;
	
	void delete_slack_info(@Param("username") String username, @Param("slack_name") String slack_name) throws Exception;
	
	void put_slack_info(@Param("username") String username, @Param("slack_name") String slack_name) throws Exception;	
	
	void delete_github_info(@Param("username") String username, @Param("github_name") String github_name) throws Exception;
	
	void put_github_info(@Param("username") String username, @Param("github_name") String github_name) throws Exception;
}
