package com.psg.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psg.mapper.CommitMapper;
import com.psg.mapper.MemberMapper;
import com.psg.service.CommitService;
import com.psg.vo.CommitVO;
import com.psg.vo.GithubVO;
import com.psg.vo.MemberVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("CommitService")
public class CommitServiceImpl implements CommitService {
	
	private static final String PY_URL = "http://psg.or.kr:2253/Git/gitcommithistory";
	
	@Resource(name="CommitMapper")
	private CommitMapper commitMapper;
	
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	
	@Override
	public ArrayList<CommitVO> request_commit_list() throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		Timestamp local_today_end = Timestamp.valueOf(LocalDate.now(ZoneId.of("UTC")).atTime(23, 59, 59));
		long utc_today_end = (local_today_end.getTime() / 1000);
		long utc_today_start = utc_today_end - 24*60*60 + 1;
		
		Instant today_end = Instant.ofEpochSecond(utc_today_end);
		Instant today_start = Instant.ofEpochSecond(utc_today_start);
		
		log.info(today_end.toString());
		log.info(today_start.toString());
		
		String until = today_end.toString();
		String since = today_start.toString();
	
		ArrayList<GithubVO> github_data = commitMapper.get_all_github_info();
		ArrayList<CommitVO> commitList = null;
		
		int number_of_github_data = commitMapper.Count_Github_Info();
		
		for(int i=0; i < number_of_github_data; i++) {
			
			String owner = github_data.get(i).getGithub_name();
			String repo = github_data.get(i).getGithub_repo();
			String token = github_data.get(i).getGithub_token();
			String response = restTemplate.getForObject(PY_URL + "?owner=" + owner + "&repo=" + repo + "&token=" + token + "&since=" + since + "&until=" + until, String.class);
			
			commitList = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, CommitVO.class));
			
			for(int j=0; j<commitList.size(); j++)
				log.info("Username: " + commitList.get(j).getUsername() + " " + "Message: " + commitList.get(j).getMessage() + " " + "Date: " + commitList.get(j).getDate() + " " + "Url: " + commitList.get(j).getUrl());
			
		}
		
		return commitList;
	}
	
	@Override
	public ArrayList<MemberVO> commit_check(ArrayList<CommitVO> commitList) throws Exception {
		ArrayList<MemberVO> userList = memberMapper.memberNotRestList();
		
		if(commitList != null && userList != null) {
			for(int i=0; i<commitList.size(); i++) {
				int index = userList.indexOf(commitList.get(i).getUsername());
				
				if(index != -1) 
					userList.remove(index);
			}
		}
		
		return userList;
	}
}
