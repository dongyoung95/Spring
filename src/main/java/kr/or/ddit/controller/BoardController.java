package kr.or.ddit.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 자바빈으로 등록해서 관리
@RequestMapping("/board")
@Controller
public class BoardController {

	// 목록
	/*
	 * 요청URI : /board/list
	 * 요청방식 : get
	 */
	@GetMapping("/list")
	public String list() {
		
		// forwarding : jsp
		return "board/list";
		
	}
	
	// 등록
	/*
	 * 요청URI : /board/register
	 * 요청방식 : get
	 * [접근 제한 정책]
	 * Pre : before (메서드 실행 전)
	 * Authorization: 권한(인가)
	 */
	//<security:intercept-url pattern="/board/register" access="hasRole('ROLE_MEMBER')" />
	//와 같은 역할!!
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	@GetMapping("/register")
	public String register() {
		
		// forwarding : jsp
		return "board/register";
		
	}
	
}
