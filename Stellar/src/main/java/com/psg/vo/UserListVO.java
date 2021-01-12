package com.psg.vo;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListVO {
	private ArrayList<HashMap<String, Object>> members;
	
	@SuppressWarnings("unchecked")
	@JsonProperty("members")
	private void member(ArrayList<HashMap<String, Object>> members) {
		this.members = new ArrayList<HashMap<String, Object>>();
		
		try {
			this.members = members;
		}catch (Exception e) {

		}
	}
}
