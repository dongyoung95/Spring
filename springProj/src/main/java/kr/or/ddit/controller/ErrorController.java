package kr.or.ddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/error")
// 스프링이 빈 객체로 등록해서 메모리에 올려줌
@Controller
public class ErrorController {

	// 요청URI : /error/error400
	@GetMapping("/error400")
	public String error400() {
		// forwarding : jsp
		return "error/error400";
	}
	
	// 요청URI : /error/error404
	@GetMapping("/error404")
	public String error404() {
		// forwarding : jsp
		return "error/error404";
	}
	
	// 요청URI : /error/error500
	@GetMapping("/error500")
	public String error500() {
		// forwarding : jsp
		return "error/error500";
	}
	
	// 요청URI : /error/errorException
	@GetMapping("/errorException")
	public String errorException() {
		// forwarding : jsp
		return "error/errorException";
	}
	
}

