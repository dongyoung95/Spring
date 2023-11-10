package kr.or.ddit.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

import kr.or.ddit.service.EmployeeService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class EmployeeController {
	
	//IoC(Inversion of Control) : 제어의 역전
	//DI(Dependency Injection):의존성 주입
	@Autowired
	EmployeeService employeeService;
	
	// 부서 자동 처리
	@ModelAttribute
	public void setEDEpt(Model model) {
		Map<String, String> eDeptMap = new HashMap<String, String>();
		eDeptMap.put("개발부", "개발부");
		eDeptMap.put("영업부", "영업부");
		eDeptMap.put("총무부", "총무부");
		model.addAttribute("eDeptMap", eDeptMap);	
	}
	
	/*
	요청URI : /employee/create
	요청파라미터 : 
	요청방식 : get
	create.jsp를 forwarding해줘야 함
	
	Any : or 연산 => 해당 ROLE중 하나만 있어도 접근 가능
	 */
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
	@RequestMapping(value="/employee/create",method=RequestMethod.GET)
	public ModelAndView create(ModelAndView mav) {
		//prefix : /WEB-INF/views/
			//		employee/create
		//suffix : 					.jsp
		mav.setViewName("employee/create");
		
		return mav;
	}
	
	/*
	요청URI : /employee/createForm
	요청파라미터 : 
	요청방식 : get
	createForm.jsp를 forwarding해줘야 함
	
	<form:form modelAttribute="employeeVO"
	 */
	@PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/employee/createForm",method=RequestMethod.GET)
	public String createForm(Model model,
			@ModelAttribute EmployeeVO employeeVO) {
		
		employeeVO.seteName("개똥이");
		employeeVO.setePosition("사원");
		employeeVO.seteAddress("대전");
		employeeVO.seteDept("영업부");
		
		//미리 체크해놓기
		String[] hobbyArray = {"sports","Music"};
		employeeVO.setHobbyArray(hobbyArray);
		
//		model.addAttribute("employeeVO", employeeVO);
		
		//prefix : /WEB-INF/views/
		//		employee/create
		//suffix : 					.jsp
		return "employee/createForm";
	}
	/*
	요청URI : /employee/create
	요청파라미터 : {eName=개똥이,eAddress=대전,eTelno=010-123-1234,
				ePosition=과장,eDept=개발부,uploadFile=파일객체들}
	요청방식 : post
	EmployeeVO 타입의 employeeVO 매개변수로 파라미터를 받아서 log 출력
	<input type="file" id="uploadFile" name="uploadFile" multiple />
	 */
	@PostMapping("/employee/createForm")
	public String createPost(
			@ModelAttribute @Validated EmployeeVO employeeVO,
			BindingResult bindingResult) {
		//EmployeeVO(eEmpno=null, eName=1, eAddress=2, eTelno=3
		//, ePosition=사원, eDept=개발부)
		//cardVOList=[
		//CardVO(eEmpno=E024, cardNum=11111-22-333333, cardYm=202501), 
		//CardVO(eEmpno=E024, cardNum=11111-33-666666, cardYm=202503), 
		//CardVO(eEmpno=E024, cardNum=11111-55-777777, cardYm=202507)
		//]
		
		log.info("createPost->employeeVO : " + employeeVO);
		
		// true : 오류 / false : 정상
		log.info("bindingResult.hasErrors() : " + bindingResult.hasErrors());
		
		if(bindingResult.hasErrors()) { // 오류 발생
			// forwarding : jsp
			return "employee/createForm";
		}
		
		int result = this.employeeService.createPost(employeeVO);
		//EmployeeVO(eEmpno=E001..
		log.info("createPost->result : " + result);
		
		return "redirect:/employee/detail?eEmpno="+employeeVO.geteEmpno();
		
	}
	
	/*
	요청URI : /employee/createAjax
			JSON 타입의 텍스트로 넘어옴
	요청파라미터 : {"eName":"개똥이","eAddress":"대전","eTelno":"010-123-1234",
				"ePosition":"과장","eDept":"개발부"}
	요청방식 : post
	*/
	@ResponseBody
	@PostMapping("/employee/createAjax")
	public EmployeeVO createAjax(@RequestBody EmployeeVO employeeVO) {
		log.info("createAjax->employeeVO : " + employeeVO);
		
		int result = this.employeeService.createPost(employeeVO);
		//EmployeeVO(eEmpno=E001..
		log.info("createPost->result : " + result);
		
		//JSON 타입의 텍스트로 넘겨줌
//		EmployeeVO(eEmpno=E001, eName=1, eAddress=2, eTelno=3
//				, ePosition=사원, eDept=개발부)
		return employeeVO;
	}
	
	/*
	요청URI : /employee/createFormAjax
			가상의 Form으로 넘어옴
	요청파라미터 : {"eName":"개똥이","eAddress":"대전","eTelno":"010-123-1234",
				"ePosition":"과장","eDept":"개발부","uploadFile":파일객체들}
	요청방식 : post
	*/
	@ResponseBody
	@PostMapping("/employee/createFormAjax")
	public Map<String,Object> createFormAjax(EmployeeVO employeeVO) {
		log.info("createFormAjax->employeeVO : " + employeeVO);
		
		//EMPLOYEE테이블 insert + 파일업로드 + ATCH_FILE_DETAIL테이블 insert
		int result = this.employeeService.createPost(employeeVO);
		//EmployeeVO(eEmpno=E001..
		log.info("createPost->result : " + result);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result","success");
		map.put("eEmpno",employeeVO.geteEmpno());
		
		return map;
	}
	
	/*
	 요청URI : /employee/detail
	 요청파라미터 : eEmpno=E001
	 요청방식 : GET
	 
	 hasAnyRole, or와 같은 의미
	 */
	@Secured({"ROLE_MEMBER", "ROLE_ADMIN"})
	@RequestMapping(value="/employee/detail",method=RequestMethod.GET)
	public ModelAndView detail(EmployeeVO employeeVO, ModelAndView mav) {
		//employeeVO[eEmpno=E001,eName=null,eAddress=null,eTelno=null,
		//ePosition=null,eDept=null]
		log.info("employeeVO : " + employeeVO);
		
		//select
		employeeVO = this.employeeService.detail(employeeVO);
		//employeeVO[eEmpno=E001,eName=1, eAddress=2, eTelno=3
		//, ePosition=사원, eDept=개발부)
		mav.addObject("data", employeeVO);
		
		//forwarding
		mav.setViewName("employee/detail");
		
		return mav;
	}
	
	/*
	 요청URI : /employee/detail2/E001
	 경로변수: eEmpno
	 요청방식 : GET
	 */
	@RequestMapping(value="/employee/detail2/{eEmpno}",method=RequestMethod.GET)
	public ModelAndView detail2(
			@PathVariable("eEmpno") String eEmpno
			, EmployeeVO employeeVO
			, ModelAndView mav) {
		log.info("detail2->employeeVO : " + employeeVO);
		log.info("detail2->eEmpno : " + eEmpno);
		
		//employeeVO[eEmpno=E001,eName=null,eAddress=null,eTelno=null,
		//ePosition=null,eDept=null]
		log.info("employeeVO : " + employeeVO);
		
		//select
		employeeVO = this.employeeService.detail(employeeVO);
		//employeeVO[eEmpno=E001,eName=1, eAddress=2, eTelno=3
		//, ePosition=사원, eDept=개발부)
		mav.addObject("data", employeeVO);
		
		//forwarding
		mav.setViewName("employee/detail");
		
		return mav;
	}
	
	/*
	요청URI : /employee/updatePost
	요청파라미터 : {eEmpno=E011,eName=개똥이2,eAddress=대전2,eTelno=010-123-1232,
				ePosition=부장,eDept=영업부}
	요청방식 : post
	 */
	@RequestMapping(value="/employee/updatePost",method=RequestMethod.POST)
	public ModelAndView updatePost(EmployeeVO employeeVO,
			ModelAndView mav) {
		log.info("updatePost->employeeVO : " + employeeVO);
		
		//데이터를 업데이트해보자
		int result = this.employeeService.updatePost(employeeVO);
		log.info("updatePost->result : " + result);
		
		mav.setViewName("redirect:/employee/detail?eEmpno="+
					employeeVO.geteEmpno());
		
		return mav;
	}
	
	/*
	 요청URI : /employee/deletePost
	 요청파라미터 : {eEmpno=E011,eName=개똥이2,eAddress=대전2,eTelno=010-123-1232,
				ePosition=부장,eDept=영업부}
	요청방식 : post
	 */
	@RequestMapping(value="/employee/deletePost",method=RequestMethod.POST)
	public ModelAndView deletePost(EmployeeVO employeeVO,
			ModelAndView mav) {
		log.info("deletePost->employeeVO : " + employeeVO);
		
		int result = this.employeeService.deletePost(employeeVO);
		log.info("deletePost->result : " + result);
		
		mav.setViewName("redirect:/employee/list");
		
		return mav;
	}
	
	/*
	 요청URI : /employee/list
	 요청파라미터 : keyword=개똥이 또는 keyword= 또는 &currentPage=1
	 요청방식 : get
	 */
	@RequestMapping(value="/employee/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView mav,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage
			) {
		log.info("list->keyword : " + keyword);
		log.info("list->currentPage : " + currentPage);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("currentPage", currentPage);
		
		List<EmployeeVO> data = this.employeeService.list(map);
		log.info("list->data : " + data);
		
		int total = this.employeeService.getTotal(map);
		
		//페이징 처리
		ArticlePage<EmployeeVO> articlePage 
			= new ArticlePage<EmployeeVO>(total, currentPage, 10, data);
		
		//페이징의 번호를 클릭 시 요청될 URL
		articlePage.setUrl("/employee/list");
		
		mav.addObject("data", articlePage);
		//forwarding
		mav.setViewName("employee/list");
		
		return mav;
	}
}










