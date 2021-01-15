package com.psg.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.psg.service.MemberService;
import com.psg.vo.GitVO;
import com.psg.vo.MemberVO;

import lombok.extern.log4j.Log4j2;



@Controller @Log4j2
public class MainController {
	
	@Resource(name="MemberService")
	private MemberService memberService;
	
	
	
	@RequestMapping(value="/Main.do", method=RequestMethod.GET)
	public String Main() throws Exception{
		log.info("메인");
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
	
	@RequestMapping(value="/Register.do", method=RequestMethod.GET)
	public String getRegister() {
		log.info("register");
		return "psg/register";
	}
	
	@RequestMapping(value="/Register.do", method=RequestMethod.POST)
	public String postRegister(MemberVO vo) throws Exception {
		
		memberService.register(vo);
		
		return "redirect:/Login.do";
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
	
	@RequestMapping(value="/DupIdChk.do", produces="application/json; charset=utf8")
	@ResponseBody
	public int DupIdChk(@RequestParam(required=true) String loginId) throws Exception {
		int dupIdCnt = memberService.DupIdChk(loginId);
		
//		return String.valueOf(dupIdCnt);
		return dupIdCnt;
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
