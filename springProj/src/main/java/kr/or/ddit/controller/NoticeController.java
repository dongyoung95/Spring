package kr.or.ddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequestMapping("/notice")
@Controller
public class NoticeController {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/list")
	public String list() {
		String pwd = "java";
		// 암호화(java라는 문자열을 암호화 처리)
		String encodedPwd = passwordEncoder.encode(pwd);
		// $2a$10$vYhd/HepAAz0Asq.6H7POuLgGvkKse88.Hdn.sttGa78KKJAfPGqC
		log.info("encodedPwd : " + encodedPwd);
		
		// forwarding : jsp
		return "notice/list";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/register")
	public String register() {
		return "notice/register";
	}
	
}
