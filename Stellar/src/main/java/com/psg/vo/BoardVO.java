package com.psg.vo;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Data
public class BoardVO {
	
	private String title;
	private String content;
	private String writer;
	private Date regdate;

}
