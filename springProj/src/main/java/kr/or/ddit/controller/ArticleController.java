package kr.or.ddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.service.ArticleService;
import kr.or.ddit.vo.ArticleVO;
import kr.or.ddit.vo.BusinessVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	@GetMapping("/create")
	public String create(@ModelAttribute ArticleVO articleVO) {

		String articleNo = this.articleService.getArticleNo();
		log.info("articleNo : " + articleNo);
		articleVO.setArticleNo(articleNo);
		
		return "article/create";
		
	}
	
	@PostMapping("/createForm")
	public String createForm(@ModelAttribute ArticleVO articleVO) {
		
		log.info("createForm -> articleVO : " + articleVO);
		log.info("createForm -> articleVO.getUploadFile() : " + articleVO.getUploadFile());
		
		int result = articleService.createPost(articleVO);
		
		return "redirect:/article/detail?articleNo=" + articleVO.getArticleNo();
	}
	
	@GetMapping("detail")
	public String detail(@RequestParam String articleNo, Model model) {
		
		log.info("detail -> articleNo : " + articleNo);

		ArticleVO data = articleService.detail(articleNo);
		log.info("detail -> data : " + data);
		
		model.addAttribute("data", data);
		
		return "article/detail";
	}
	
	
	
}
