package com.psg.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class MemberVO {

	// user_info 테이블
	private String id; //슬랙 고유 ID(PK)
//	private String real_name; //닉네임
	private String slack_name; //실제 이름
//	private String team_id; //팀 ID
	private String git_name; //깃허브 닉네임
//	private String comment; //비고란
	

}
