package kr.or.ddit.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.velocity.runtime.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.service.BusinessService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.BusinessVO;
import lombok.extern.slf4j.Slf4j;

//프링아, 이 클래스를 컨트롤러로 자바빈으로 등록해줘
//클래스 레벨에서 공통 요청매핑 가능
@Slf4j
@RequestMapping(value="/business")
@Controller
public class BusinessController {
	
	@Autowired
	BusinessService businessService;
	
	/*
	 요청URI : 클래스레벨 + 메소드레벨 = /business/create
	 리턴 타입이 void인 경우 요청URI가 jsp의 경로가 됨
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public void create(ModelAndView mav) {
		
		//forwarding
//		mav.setViewName("business/create");
		
//		return mav;
	}
	
	/*
	 요청URI : 클래스레벨 + 메소드레벨 = /business/createForm
	 리턴 타입이 void인 경우 요청URI가 jsp의 경로가 됨
	 
	 <form:form modelAttribute="businessVO"
	 */
	@GetMapping("/createForm")
	public void createForm(@ModelAttribute BusinessVO businessVO) {
		
		//다음 기본키 값 가져오기
		String bSiteno = this.businessService.getBSiteno();
		log.info("createForm->bSiteno: " + bSiteno);
		
		businessVO.setbSiteno(bSiteno);
		
		//forwarding
//		mav.setViewName("business/create");
		
//		return "business/createForm";
	}
	
	/*
	 요청URI : /business/create
	요청파라미터 : {bName=대전사업장,bAddress=대전,bTelno=042-111-2222,bAmount=12200000,
				uploadFile=파일객체}
	요청방식 : post
	
	<input type="file" class="form-control form-control-user"
								id="uploadFile" name="uploadFile" 
	 */
	@PostMapping("/createForm")
	public String createPost(@ModelAttribute @Validated BusinessVO businessVO,
			BindingResult bindingResult) {
		//businessVO{bSiteno=null,bName=대전사업장,bAddress=대전,bTelno=042-111-2222
		// ,bAmount=12200000,uploadFile=파일객체}
		log.info("businessVO : " + businessVO);
		
		log.info("bindingResult.hasErrors() : " + bindingResult.hasErrors());
		
		if(bindingResult.hasErrors()) {
			// forwarding : jsp
			return "business/createForm";
			
		}
		
		//사업장 등록
		int result = this.businessService.createPost(businessVO);
		//businessVO{bSiteno=BS0012,bName=대전사업장,bAddress=대전,bTelno=042-111-2222
		// ,bAmount=12200000,uploadFile=파일객체}
		log.info("createPost->result : " + result);
		
		//상세로 이동(URI재요청)
		return "redirect:/business/detail?bSiteno="+businessVO.getbSiteno();
		
	}
	
	

	/**
	요청URI : /business/createAjax
		JSON 타입의 텍스트로 요청
	요청파라미터 : {"bName":"대전사업장","bAddress":"대전","bTelno":"042-111-2222"
				,"bAmount":"12200000"}
	요청방식 : post
	*/
	@ResponseBody
	@PostMapping("/createAjax")
	public BusinessVO createAjax(@RequestBody BusinessVO businessVO) {
		//businessVO{bSiteno=null,bName=대전사업장,bAddress=대전,bTelno=042-111-2222
		// ,bAmount=12200000...}
		log.info("createAjax->businessVO : " + businessVO);
		
		//사업장 등록
		int result = this.businessService.createPost(businessVO);
		//businessVO{bSiteno=BS0012,bName=대전사업장,bAddress=대전,bTelno=042-111-2222
		// ,bAmount=12200000...}
		log.info("createPost->result : " + result);
		
		return businessVO;
	}
	/**
	요청URI : /business/createFormAjax
		JSON 타입의 텍스트로 요청
	요청파라미터 : {"bName":"대전사업장","bAddress":"대전","bTelno":"042-111-2222"
				,"bAmount":"12200000","uploadFile":파일객체들}
	요청방식 : post
	 */
	@ResponseBody
	@PostMapping("/createFormAjax")
	public Map<String,Object> createFormAjax(BusinessVO businessVO) {
		//businessVO{bSiteno=null,bName=대전사업장,bAddress=대전,bTelno=042-111-2222
		// ,bAmount=12200000...}
		log.info("createAjax->businessVO : " + businessVO);
		
		//사업장 등록
		int result = this.businessService.createPost(businessVO);
		//businessVO{bSiteno=BS0012,bName=대전사업장,bAddress=대전,bTelno=042-111-2222
		// ,bAmount=12200000...}
		log.info("createPost->result : " + result);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("bSiteno", businessVO.getbSiteno());
		
		return map;
	}
	
	/*
	 요청URI : /business/detail
	 요청파라미터 : bSiteno=BS0112
	 요청방식 : get
	 */
//	골뱅이RequestMapping(value="/detail",method=RequestMethod.GET)
	//속성이 하나인 경우 value를 생략할 수 있음
	@RequestMapping("/detail")
	public ModelAndView detail(ModelAndView mav,
			BusinessVO businessVO) {
		//BusinessVO(bSiteno=BS0041, bName=null, bAddress=null, bTelno=null
		//, bAmount=0, bManqty=0, bStrdate=null, bPredate=null, bEnddate=null
		//, bRemark=null)
		log.info("detail->businessVO : " + businessVO);
		
		//사업장 상세
		BusinessVO data = this.businessService.detail(businessVO);
		log.info("detail->data : " + data);
		
		mav.addObject("data", data);
		//forwarding
		mav.setViewName("business/detail");
		
		return mav;
	}
	
	/*
	 요청URI : /business/detail2/BS0112
	 경로변수 : bSiteno
	 요청방식 : get
	 */
//	골뱅이RequestMapping(value="/detail",method=RequestMethod.GET)
	//속성이 하나인 경우 value를 생략할 수 있음
	@RequestMapping("/detail2/{bSiteno}")
	public ModelAndView detail(ModelAndView mav,
			@PathVariable("bSiteno") String bSiteno,
			BusinessVO businessVO) {
		//BusinessVO(bSiteno=BS0041, bName=null, bAddress=null, bTelno=null
		//, bAmount=0, bManqty=0, bStrdate=null, bPredate=null, bEnddate=null
		//, bRemark=null)
		log.info("detail2->bSiteno : " + bSiteno);
		log.info("detail2->businessVO : " + businessVO);
		
		//사업장 상세
		BusinessVO data = this.businessService.detail(businessVO);
		log.info("detail->data : " + data);
		
		mav.addObject("data", data);
		//forwarding
		mav.setViewName("business/detail");
		
		return mav;
	}
	
	/*
	요청URI : /business/updatePost
	요청파라미터 : {bSiteno=BS0012,bName=대전사업장,bAddress=대전
			   , bTelno=042-111-2222,bAmount=12200000}
	요청방식 : post
	 */
	@RequestMapping(value="/updatePost",method=RequestMethod.POST)
	public ModelAndView updatePost(ModelAndView mav,
			BusinessVO businessVO) {
		log.info("updatePost->businessVO : " + businessVO);
		
		//업데이트 수행
		int result = this.businessService.updatePost(businessVO);
		log.info("updatePost->result : " + result);
		
		mav.setViewName("redirect:/business/detail?bSiteno="+businessVO.getbSiteno());
		
		return mav;
	}
	
	/*
	요청URI : /business/deletePost
	요청파라미터 : {bSiteno=BS0012,bName=대전사업장,bAddress=대전
			   , bTelno=042-111-2222,bAmount=12200000}
	요청방식 : post
	 */
	@RequestMapping(value="/deletePost",method=RequestMethod.POST)
	public ModelAndView deletePost(ModelAndView mav,
			BusinessVO businessVO) {
		log.info("deletePost->businessVO : " + businessVO);
		
		//삭제 처리
		int result = this.businessService.deletePost(businessVO);
		log.info("deletePost->result : " + result);
		
		mav.setViewName("redirect:/business/list");
		
		return mav;
	}
	
	//사업장 목록
	//요청URI : /business/list  또는 /business/list?keyword=개똥이&currentPage=1
	//요청파라미터(HTTP파라미터,QueryString) : keyword=개똥이 또는 keyword= 또는 
	//요청방식 : get
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(ModelAndView mav,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage) {
		log.info("list->keyword : " + keyword);
		log.info("list->currentPage : " + currentPage);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);//검색어
		map.put("currentPage", currentPage);//현재 페이지번호
		log.info("list->map : " + map);
		
		List<BusinessVO> data = this.businessService.list(map);
		log.info("list->data : " + data);
		
		//전체행의 수(검색 포함)
		int total = this.businessService.getTotal();
		log.info("list->total : " + total);
		
		//페이징객체
		ArticlePage<BusinessVO> articlePage 
			= new ArticlePage<BusinessVO>(total, currentPage, 10, data);
		
		//페이징의 번호를 클릭 시 요청될 URL
		articlePage.setUrl("/business/list");
		
		mav.addObject("data", articlePage);
		//forwarding
		mav.setViewName("business/list");
		
		return mav;
	}
}












