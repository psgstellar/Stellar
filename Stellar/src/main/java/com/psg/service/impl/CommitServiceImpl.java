package com.psg.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psg.mapper.CommitMapper;
import com.psg.service.CommitService;
import com.psg.vo.CommitVO;
import com.psg.vo.GithubVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("CommitService")
public class CommitServiceImpl implements CommitService {
	
	private static final String PY_URL = "http://psg.or.kr:2253/Git/gitcommithistory";
	
	@Resource(name="CommitMapper")
	private CommitMapper commitMapper;
	
	@Override
	public void request_commit_list() throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayList<GithubVO> github_data = commitMapper.get_all_github_info();
		
		int number_of_github_data = commitMapper.Count_Github_Info();
		
		for(int i=0; i < number_of_github_data; i++) {
			
			String owner = github_data.get(i).getGithub_name();
			String repo = github_data.get(i).getGithub_repo();
			String token = github_data.get(i).getGithub_token();
			String response = restTemplate.getForObject(PY_URL + "?owner=" + owner + "&repo=" + repo + "&token=" + token, String.class);
			
			CommitVO[] commit_array = mapper.readValue(response, CommitVO[].class);
			
			for(CommitVO commit : commit_array)
				log.info("Username: " + commit.getUsername() + " " + "Message: " + commit.getMessage() + " " + "Date: " + commit.getDate() + " " + "Url: " + commit.getUrl());
		}
	}
}
