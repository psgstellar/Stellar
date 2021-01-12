package com.psg.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psg.service.MemberService;
import com.psg.service.RegisterService;
import com.psg.service.UserService;
import com.psg.vo.GitVO;
import com.psg.vo.MemberVO;

import lombok.extern.log4j.Log4j2;



@Controller @Log4j2
public class MainController {

	@Resource(name="UserService")
	public UserService userService;
	
	@Resource(name="MemberService")
	private MemberService memberService;
	
	
	@RequestMapping(value="/Main.do", method=RequestMethod.GET)
	public String Main() throws Exception{
		log.info("메인");
		
	//s	List<UserVO> vo = userService.userlist();
		// log.info(vo.toString());
		return "psg/Main";
	}
	
	@RequestMapping(value="/About.do", method=RequestMethod.GET)
	public String About() {
		return "psg/about";
	}
	
	@RequestMapping(value="/Login.do", method=RequestMethod.GET)
	public String Login() {
		return "psg/login";
	}
	
	@RequestMapping(value="/Register.do")
	public String Register() {
		log.info("register");
		return "psg/register";
	}
	
	
	@RequestMapping(value="/Logout.do", method=RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/Login.do";
	}
	
	@RequestMapping(value="/Access_Denied", method={RequestMethod.GET, RequestMethod.POST})
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		
		return "accessDenied";
	}
	
	@RequestMapping(value="/Contact.do", method=RequestMethod.GET)
	public String Contact() {
		log.info("contact");
		return "psg/contact";
	}
	
	@RequestMapping(value="/Commit.do", method=RequestMethod.GET)
	public String Commit() {
		log.info("commit");
		return "psg/commit";
	}
	
	@RequestMapping(value="/accessDenied.do", method=RequestMethod.GET)
	public String Denied() {
		log.info("Denied");
		return "psg/error";
	}
	

	
	
	@GetMapping("/UserList.do")
	public String userlist(Model model) throws Exception{
		List<MemberVO> mvo = new ArrayList<MemberVO>();
		mvo = memberService.memberlist();
		model.addAttribute("mvo", mvo);
		return "psg/user";
	}
	
	@GetMapping("/modifyMember.do")
	public String modifyMember(Model model) throws Exception {
		List<MemberVO> mvo = new ArrayList<MemberVO>();
		mvo = memberService.memberlist();
		model.addAttribute("mvo", mvo);
		return "member/modifyMember";
	}
	
	@GetMapping("/ModifyMember.do")
	public String ModifyMember(Model model, MemberVO vo) throws Exception{
		log.info("Modify함수:"+vo.toString());
		memberService.membermodify(vo);
		return "redirect:/UserList.do";
	}
	
	@GetMapping("/infoMember.do")
	public String InfoMember(Model model, MemberVO vo, GitVO gvo) throws Exception{
		
		MemberVO mvo = memberService.memberinfo(vo);
		List<GitVO> gitlist = memberService.gitnamelist(gvo); 
		
		model.addAttribute("mvo",mvo);
		model.addAttribute("gvo", gitlist);
		return "member/modifyMember";
	}
	
	@GetMapping("/deleteMember.do")
	public String DeleteMember(Model mode, MemberVO vo) throws Exception{
		
		memberService.memberdelete(vo);
		log.info(vo.getReal_name()+"삭제완료");
		return "redirect:/UserList.do";
	}
	
	
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		return userName;
	}

}
