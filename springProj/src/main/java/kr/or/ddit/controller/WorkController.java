package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.service.WorkService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.BookVO;
import kr.or.ddit.vo.BusinessVO;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.WorkVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/work")
@Controller
public class WorkController {
	
	@Autowired
	WorkService workService;
	
	// /work/create
	@GetMapping("/create")
	public String create(Model model) {
		
		//직원 가져옴
		List<EmployeeVO> employeeVOList = this.workService.getEmployee();
		log.info("employeeVOList : " + employeeVOList);
		
		//사업장 가져옴
		List<BusinessVO> businessVOList = this.workService.getBusiness();
		log.info("businessVOList : " + businessVOList);
		
		model.addAttribute("employeeVOList", employeeVOList);
		model.addAttribute("businessVOList", businessVOList);
		
		//forwarding : jsp
		return "work/create";
	}
	
	// /work/createForm
	@GetMapping("/createForm")
	public String createForm(Model model, 
			@ModelAttribute WorkVO workVO) {
		
		//기본키 가져오기
		String workId = this.workService.getWorkId();
		log.info("createForm->workId : " + workId);
		workVO.setWorkId(workId);
		
		//직원 가져옴
		List<EmployeeVO> employeeVOList = this.workService.getEmployee();
		log.info("employeeVOList : " + employeeVOList);
		
		//사업장 가져옴
		List<BusinessVO> businessVOList = this.workService.getBusiness();
		log.info("businessVOList : " + businessVOList);
		
		model.addAttribute("employeeVOList", employeeVOList);
		model.addAttribute("businessVOList", businessVOList);
		
		//forwarding : jsp
		return "work/createForm";
	}
	// /work/create
	@PostMapping("/create")
	public String createPost(WorkVO workVO) {
		//WorkVO [eEmpno=1003, bSiteno=BS0005, wInpdate=Wed Nov 01 00:00:00 KST 2023
		//, wEnddate=Thu Nov 30 00:00:00 KST 2023, wScore=100, workId=, 
		//uploadFile=[org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@892ef23, org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@6362273e, org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@2f5efbc4]
		//, atchFileDetailVOList=null]
		log.info("createPost->workVO : " + workVO);
		
		int result = this.workService.createPost(workVO);
		//, wEnddate=Thu Nov 30 00:00:00 KST 2023, wScore=100, workId=WK0001,
		log.info("createPost->result : " + result);
		
		//URL을 재요청
		return "redirect:/work/detail?workId="+workVO.getWorkId();
	}
	
	// /work/detail?workId=WK0001
	@GetMapping("/detail")
	public String detail(WorkVO workVO, Model model) {
		//..., wEnddate=null, wScore=0, workId=WK0001,...
		log.info("detail->workVO : " + workVO);
		
		//직원 가져옴
		List<EmployeeVO> employeeVOList = this.workService.getEmployee();
		log.info("employeeVOList : " + employeeVOList);
		
		//사업장 가져옴
		List<BusinessVO> businessVOList = this.workService.getBusiness();
		log.info("businessVOList : " + businessVOList);
		
		//..., wEnddate=23/11/02, wScore=100, workId=WK0001,...
		workVO = this.workService.detail(workVO);
		
		model.addAttribute("data", workVO);
		model.addAttribute("employeeVOList", employeeVOList);
		model.addAttribute("businessVOList", businessVOList);
		
		//forwarding : jsp
		return "work/detail";
	}
	
	//수정처리
	// /work/updatePost
	@PostMapping("/updatePost")
	public String updatePost(WorkVO workVO) {
		log.info("updatePost->workVO : " + workVO);
		
		int result = this.workService.updatePost(workVO);
		log.info("updatePost->result : " + result);
		
		//redirect : URI재요청
		return "redirect:/work/detail?workId="+workVO.getWorkId();
	}
	
	//삭제처리
	// /work/deletePost
	/*
	//WorkVO [eEmpno=1003, bSiteno=BS0005, wInpdate=Wed Nov 01 00:00:00 KST 2023
	//, wEnddate=Thu Nov 30 00:00:00 KST 2023, wScore=100, workId=, 
	//uploadFile=[org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@892ef23, org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@6362273e, org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@2f5efbc4]
	//, atchFileDetailVOList=null]
	 */
	@PostMapping("/deletePost")
	public String deletePost(WorkVO workVO) {
		log.info("deletePost->workVO : " + workVO);
		
		//첨부파일테이블 삭제 후 근무테이블 삭제
		int result = this.workService.deletePost(workVO);
		log.info("deletePost->result : " + result);
		
		//redirect : URI재요청
		return "redirect:/work/list";
	}
	
	//근무 목록
	/*
	 요청URI : /work/list
	 요청파라미터(HTTP파라미터, QueryString) : /work/list?keyword=개똥이&currentPage=1
	 요청방식 : get
	 */
	@GetMapping("/list")
	public String list(
			@RequestParam(value="keyword",required=false) String keyword,	
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			Model model) {
		//list->keyword : null
		log.info("list->keyword : " + keyword);
		log.info("list->currentPage : " + currentPage);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyword",keyword);
		map.put("currentPage",currentPage);
		
		List<WorkVO> workVOList = this.workService.list(map);
		log.info("list->bookVOList : " + workVOList);
		
		int total = this.workService.getTotal(map);
		log.info("list->total : " + total);
		
		ArticlePage<WorkVO> data 
			= new ArticlePage<WorkVO>(total, currentPage, 10, workVOList);
		
		data.setUrl("/list");
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("data", data);
		
		
		//prefix : /WEB-INF/views/
		//			book/list
		//suffix : .jsp
		//forwarding
		return "work/list";
	}
	
}






