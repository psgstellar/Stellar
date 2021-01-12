package com.psg.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class MemberDao {
	
	private JdbcTemplate jdbcTemplate;
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int saveMember(Member m) {
		String query = "insert into user_info values('"+m.getName()+"','"+m.getId()+"','"+m.getUName()+"')";
		
		return jdbcTemplate.update(query);
	}
	
	public int updateMember(Member m) {
		String query = "update user_info set name='"+m.getName()+"', slack_username='"+m.getUName()+"' where id='" + m.getId() +"' ";
		
		return jdbcTemplate.update(query);
	}
	
	public int deleteMember(Member m) {
		String query = "delete from member where id='" + m.getId()+"' ";
		
		return jdbcTemplate.update(query);
	}
	

}
