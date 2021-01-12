package com.psg.vo;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackVO {

	private ArrayList<HashMap<String, Object>> messages;
	
	
	@SuppressWarnings("unchecked")
	@JsonProperty("messages")
	private void message(ArrayList<HashMap<String, Object>> messages) {
		this.messages = new ArrayList<HashMap<String, Object>>();

		try {
			this.messages = messages;
		} catch (Exception e) {

		}
	}
	
	
	
	

}
