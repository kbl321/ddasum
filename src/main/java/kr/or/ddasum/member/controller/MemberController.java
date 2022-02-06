package kr.or.ddasum.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.ddasum.member.model.service.MemberService;
import kr.or.ddasum.member.model.vo.Member;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	
	
	//정두식 추가 코드(시작)
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public String memberLogin(Member member, HttpServletRequest request ) {
		
		Member m = mService.memberLogin(member);
		
		if(m != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", m);
			
			return "main"; 
		}else {
			return "member/loginPage";
		}
	}
	
	@RequestMapping(value = "/member/logout.do")
	public String memberLogout(HttpSession session) {
		
		session.invalidate();
		
		return "main";
	}
	
	@RequestMapping(value = "/member/myPage.do")
	public String myPageIn(Member member) {
		
		
		
		return "member/myPage";
	}
	
	@RequestMapping(value = "/member/PasswordCheckPage.do")
	public String memberPasswordCheck() {
		return "member/passwordCheckPage";
	}
	
	@RequestMapping(value = "/member/myPageUpdate.do", method = RequestMethod.POST)
	public String myPagePasswordCheck (HttpServletRequest request, @SessionAttribute Member member, HttpSession session) {
		String userPwd = request.getParameter("userPwd");
		
		if(userPwd == null) {
			
			return "member/passwordCheck";
		}else {
			String userId = request.getParameter("userId");
			
			member.setUserPwd(userPwd);
			
			Member m = mService.memberLogin(member);
			
			if(m != null) {
				session.setAttribute("member", m);
				return "member/myPageUpdate";
			}else {
				return "member/passwordCheckPage";
			}
			
		}
	}
	
	@RequestMapping(value = "/member/memberInfoUpdate.do", method = RequestMethod.POST)
	public void memberInfoUpdate(@RequestParam String userPwd,
								@RequestParam String userName,
								@RequestParam String nick,
								@RequestParam String email,
								@RequestParam String address,
								@RequestParam String phone,
								@SessionAttribute Member member,
								HttpServletResponse response,
								HttpSession session) throws IOException{
		
		String userId = member.getUserId();
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setUserName(userName);
		m.setNick(nick);
		m.setEmail(email);
		m.setAddress(address);
		m.setPhone(phone);
		
		int result = mService.memberInfoUpdate(m);
		
		if(result >0) {
			session.setAttribute("member", m);
			response.getWriter().print(true);
		}else {
			response.getWriter().print(false);
		}
		
	}
	
	@RequestMapping(value = "/member/reservationPage.do")
	public String reservationPage() {
		
		return "member/reservationCheckPage";
	}
	
	//정두식 추가 코드(끝)
	
	
	@RequestMapping(value="/member/loginPage.do", method = RequestMethod.GET)
	public String loginPage() {
		
		return "/member/loginPage";
		
	}
	
	@RequestMapping(value="/member/joinPage.do", method = RequestMethod.GET)
	public String joinPage() {
		
		return "/member/joinPage";
		
	}
	
	@RequestMapping(value="/member/findIdPage.do", method = RequestMethod.GET)
	public String findIdPage() {
		
		return "/member/findIdPage";
		
	}
	
	@RequestMapping(value="/member/findPwdPage.do", method = RequestMethod.GET)
	public String findPwdPage() {
		
		return "/member/findPwdPage";
		
	}
	
	@RequestMapping(value="/member/saleRestaurantPage.do", method = RequestMethod.GET)
	public String saleRestaurantPage() {
		
		return "/member/saleRestaurantPage";
		
	}
	
	@RequestMapping(value="/member/memberJoinPage.do", method = RequestMethod.GET)
	public String memberJoinPage() {
		
		return "/member/memberJoinPage";
		
	}
	
	@RequestMapping(value="/member/bizMemberJoinPage.do", method = RequestMethod.GET)
	public String bizMemberJoinPage() {
		
		return "/member/bizMemberJoinPage";
		
	}
	
	@RequestMapping(value="/member/successJoinPage.do", method = RequestMethod.POST)
	public String successJoinPage() {
		
		return "/member/successJoinPage";
		
	}
	
	
	//나중에 삭제할 코드
	@RequestMapping(value = "/member/mainPage.do")
	public String mainPage() {
		return "/main";
	}
	
}
