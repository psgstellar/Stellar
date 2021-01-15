package com.psg.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class UserVO {
	// 로그인 및 회원가입
	String id;
	String pwd;
	String email;
}
