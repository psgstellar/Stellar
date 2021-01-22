package com.psg.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.psg.service.MemberService;
import com.psg.vo.GithubVO;
import com.psg.vo.GitVO;
import com.psg.vo.MemberDetailsVO;
import com.psg.vo.MemberVO;
import com.psg.vo.RestVO;
import com.psg.vo.SlackVO;

import lombok.extern.log4j.Log4j2;



@Controller @Log4j2
public class MainController {
	
	@Resource(name="MemberService")
	private MemberService memberService;
	
	
	
	@GetMapping(value="/Main.do")
	public String Main(Authentication authentication) throws Exception{
		if(authentication != null) {
			System.out.println("타입 정보: " + authentication.getClass());
			
			WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
			System.out.println("세션ID: " + web.getSessionId());
			System.out.println("접속IP : " + web.getRemoteAddress());
			
			MemberDetailsVO memberDetails = (MemberDetailsVO)authentication.getPrincipal();
			System.out.println("ID정보 : " + memberDetails.getUsername());
		}
		return "psg/Main";
	}
	
	@GetMapping(value="/About.do")
	public String About() {
		return "psg/about";
	}
	
	@RequestMapping(value="/Login.do")
	public String Login(HttpServletRequest request) {
		String uri = request.getHeader("Referer");
		
		log.info("Hello Param");
		
		if(uri != null) {
			if(!uri.contains("/Login.do")) {
				request.getSession().setAttribute("prevPage", request.getHeader("Referer"));
			}
		}
		
		return "psg/login";
	}
	
//	@GetMapping(value="/Login.do")
//	public String Login() {
//		
//		log.info("Hello Login.do");
//		
//		return "psg/login";
//	}
////	
//	@PostMapping(value="/Login.do")
//	public String postLogin() {
//		
//		return "psg/login";
//	}
//	
	
//	@RequestMapping(value="/Login.do")
//	public String Login(HttpServletRequest request) {
//		String uri = request.getHeader("Referer");
//		if (!uri.contains("/Login.do")) {
//			request.getSession().setAttribute("prevPage",
//					request.getHeader("Referer"));
//		}
//		
//		return "psg/login";
//	}
//	
	@GetMapping(value="/Register.do")
	public String getRegister() {
		log.info("register");
		return "psg/register";
	}
	
	@PostMapping(value="/Register.do")
	public String postRegister(MemberVO vo) throws Exception {
		
		memberService.register(vo);
		
		return "redirect:/Login.do";
	}
	
	@GetMapping(value="/Logout.do")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/Login.do";
	}
	
	@GetMapping@PostMapping(value="/Access_Denied")
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		
		return "accessDenied";
	}
	
	@GetMapping(value="/Contact.do")
	public String Contact() {
		log.info("contact");
		return "psg/contact";
	}
	
	@GetMapping(value="/Commit.do")
	public String Commit() {
		log.info("commit");
		return "psg/commit";
	}
	
	@GetMapping(value="/Admin/Management.do")
	public String AdminManagement() {
		return "admin/management";
	}
	
	@GetMapping(value="/Admin/Settings.do")
	public String AdminSettings(Model model) {
		ArrayList<GithubVO> get_github_info = null;
		ArrayList<SlackVO> get_slack_info = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		
		try {
			get_github_info = memberService.get_github_info(username);
			get_slack_info = memberService.get_slack_info(username);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("slack_info", get_slack_info);
		model.addAttribute("github_info", get_github_info);
		
		return "admin/settings";
	}
	
	@GetMapping(value="/Admin/Rest.do")
	public String Admin_Member_Rest(Model model) {
		ArrayList<RestVO> memberRest = null;
		ArrayList<MemberVO> memberList = null;
		try {
			memberRest = memberService.get_rest();
			memberList = memberService.memberList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("memberRest", memberRest);
		model.addAttribute("memberList", memberList);
		
		return "admin/rest";
	}
	// @DateTimeFormat(pattern="dd/MM/yyyy h:mm tt") 
	
	
	@GetMapping(value="/Admin/Append_Rest.do", produces="application/json; charset=utf8")
	@ResponseBody
	public int Append_Rest(String username, String start_date, String end_date) {
		Date start=null;
		Date end=null;
		
		if((start_date == null || start_date.equals("")) || (end_date == null || end_date.equals(""))) {
			return 0;
		}else {
			SimpleDateFormat transFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			try {
				start = transFormat.parse(start_date);
				end = transFormat.parse(end_date);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(memberService.DupRestChk(username, start, end) > 0)
					return 1;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				memberService.put_rest(username, start, end);
			} catch (Exception e) {
						// TODO Auto-generated catch block	
			}
				return 2;
		}
		
		
		
			
	}
	
	@GetMapping(value="/Admin/Delete_Rest.do", produces="application/json; charset=utf8")
	@ResponseBody
	public void Delete_Rest(String username, String start_date, String end_date) {
		Date start=null;
		Date end=null;
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			start = transFormat.parse(start_date);
			end = transFormat.parse(end_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			memberService.delete_rest(username, start, end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@GetMapping(value="/Admin/Role.do")
	public String MemberInfo(Model model) {
		ArrayList<MemberVO> memberList=null;
		try {
			memberList = memberService.memberList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("memberList", memberList);
		
		return "admin/role";
	}
	
	@GetMapping(value="/Admin/getRole.do", produces="application/json; charset=utf8")
	@ResponseBody
	public void MemberRole(String username, String auth) throws Exception {
		
		memberService.updateRole(username, auth);
	}
	
	@GetMapping(value="/Admin/Slack_Delete.do", produces="application/json; charset=utf8")
	@ResponseBody
	public void Admin_Slack_Delete(String slack_name) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		memberService.delete_slack_info(username, slack_name);
	}
	
	@GetMapping(value="/Admin/Slack_Append.do", produces="application/json; charset=utf8")
	@ResponseBody
	public boolean Admin_Slack_Append(String slack_name) throws Exception {		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		if(memberService.DupSlackChk(slack_name) > 0) {
			
			log.info(memberService.DupSlackChk(slack_name));
			return false;
		} else {
			memberService.put_slack_info(username, slack_name);
			return true;
		}
		
		
		
	}
	
	@GetMapping(value="/Admin/Github_Delete.do", produces="application/json; charset=utf8")
	@ResponseBody
	public void Admin_Github_Delete(String github_name) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		memberService.delete_github_info(username, github_name);
	}
	
	@GetMapping(value="/Admin/Github_Append.do", produces="application/json; charset=utf8")
	@ResponseBody
	public boolean Admin_Github_Append(String github_name) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		if(memberService.DupGithubChk(github_name) > 0) {
			log.info(memberService.DupGithubChk(github_name));
			
			return false;
		} else {
			memberService.put_github_info(username, github_name);
			return true;
		}		
	}
	
	@GetMapping(value="/Member/Management.do")
	public String MemberManagement() {
		return "member/management";
	}
	
	@GetMapping(value="/Member/Settings.do")
	public String MemberSettings() {
		return "member/settings";
	}
	
	@GetMapping(value="/Member/Rest.do")
	public String Member_Rest(Model model) {
		ArrayList<RestVO> memberRest = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		try {
			memberRest = memberService.get_member_rest(username);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("memberRest", memberRest);
		
		return "member/rest";
	}
	
	
	
	@RequestMapping(value="/AccessDenied.do")
	public String Denied(Model model, Authentication auth, HttpServletRequest req) {
		AccessDeniedException ade = (AccessDeniedException) req.getAttribute(WebAttributes.ACCESS_DENIED_403);
		
		log.info("ex : {}", ade);
		model.addAttribute("auth", auth);
		model.addAttribute("errMsg", ade);
		return "psg/accessdenied";
	}
	
	@GetMapping(value="/DupIdChk.do", produces="application/json; charset=utf8")
	@ResponseBody
	public int DupIdChk(@RequestParam(required=true) String loginId) throws Exception {
		int dupIdCnt = memberService.DupIdChk(loginId);
		
		return dupIdCnt;
	}
	
	@GetMapping(value="/DupSlackChk.do", produces="application/json; charset=utf8")
	@ResponseBody
	public int DupSlackChk(@RequestParam(required=true) String slack_name) throws Exception {
		int dupSlackCnt = memberService.DupSlackChk(slack_name);
		
		return dupSlackCnt;
	}
	
	@GetMapping(value="/DupGithubChk.do", produces="application/json; charset=utf8")
	@ResponseBody
	public int DupGithubChk(@RequestParam(required=true) String github_name) throws Exception {
		int dupGithubCnt = memberService.DupGithubChk(github_name);
		
//		return String.valueOf(dupIdCnt);
		return dupGithubCnt;
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
