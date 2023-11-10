package kr.or.ddit.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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

import kr.or.ddit.service.BookService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

/*
Controller 어노테이션
 스프링 프레임워크에게 "이 클래스는 웹 브라우저의 요청(request)를
 받아들이는 컨트롤러야" 라고 알려주는 것임.
 스프링은 servlet-context.xml의 context:component-scan의 설정에 의해
 이 클래스를 자바빈 객체로 등록(메모리에 바인딩).
 */
// 로그인 한 사용자만 접근 가능
// (Authorization에 관계 없이 Authentication만 하면 접근 가능)
@PreAuthorize("hasRole(isAuthenticated())")
@Slf4j
@Controller
public class BookController {
	//도서관리프로그램
	//서비스를 호출하기 위해 의존성 주입(Dependency Injection - DI)
	//IoC(Inversion of Control - 제어의 역전)
	@Autowired
	BookService bookService;
	
	@Autowired
	String uploadFolder;
	
	/*
	 요청URI : localhost/create
	 요청방식 : get
	 */
	//요청매핑(요청값="/create",방식=요청방식.GET)
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create() {
		/*
		 ModelAndView
		 1) Model : Controller가 반환할 데이터(String, int, List, Map, VO..)를 담당
		 2) View : 화면을 담당(뷰(View : JSP)의 경로)
		 */
		ModelAndView mav = new ModelAndView();
		
//		<beans:property name="prefix" value="/WEB-INF/views/" />
//		<beans:property name="suffix" value=".jsp" />
		// prefix(접두어) : /WEB-INF/views/
		// viewName(경로): book/create
		// suffix(접미어) : .jsp
		mav.setViewName("book/create");
		
		return mav;		
	}
	
	/*
	 요청URI : localhost/createForm
	 요청방식 : get
	 */
	//요청매핑(요청값="/createForm",방식=요청방식.GET)
	//<form:form modelAttribute="bookVO"
	@GetMapping("/createForm")
	public String createForm(@ModelAttribute BookVO bookVO) {
		
		String bookId = this.bookService.getBookId();
		log.info("createPost->bookId : " + bookId);
		bookVO.setBookId(bookId);
		
//		<beans:property name="prefix" value="/WEB-INF/views/" />
//		<beans:property name="suffix" value=".jsp" />
		// prefix(접두어) : /WEB-INF/views/
		// viewName(경로): book/createForm
		// suffix(접미어) : .jsp
		//forwarding
		return "book/createForm";		
	}
	
	//<form action="/create" method="post">
	//HTTP 파라미터(요청 파라미터) 
	//=> {"title":"총알탄 개똥이","category":"소설","price":10000,"uploadFile":"파일객체"}
	//bookVO => {"bookId":0,"title":"개똥이의 모험","category":"소설"
	//			,"price":10000,"insertDate":""}
	//<input type="file" id="uploadFile" name="uploadFile" multiple />
	//입력값 검증을 할 도메인 클래스에 골뱅이Validated를 지정함
	//BindingResult : 입력값 검증 결과를 담고있는 객체
	@PostMapping("/createForm")
	public String createPost(@Validated BookVO bookVO, 
			BindingResult bindingResult) {
		//bookVO : BookVO [bookId=0, title=개똥이, category=소설, price=10000
		//				 , insertDate=null,uploadFile=파일객체
		//writerVOList=[
		//WriterVO(wrId=WR0001, wrName=개똥이, wrHp=010-123-1234), 
		//WriterVO(wrId=WR0002, wrName=수현, wrHp=010-222-3333), 
		//WriterVO(wrId=WR0003, wrName=찬혁, wrHp=010-333-5555)]
		//]
		log.info("bookVO : " + bookVO);
		log.info("uploadFile : " + bookVO.getUploadFile());
		
		//입력값 검증 결과(true : 오류 발생 / false : 오류 없음)
		//bindingResult.hasErrors() : false
		log.info("bindingResult.hasErrors() : " + bindingResult.hasErrors());
		
		if(bindingResult.hasErrors()) { // true(오류 발생)
			// forwarding : jsp *****
			// redirect를 하면 오류 정보를 담지 못한다
			return "book/createForm";
		}
		
		//도서 등록
		int result = this.bookService.createPost(bookVO);
		//BookVO [bookId=3, title=개똥이, category=소설, price=10000
		//				 , insertDate=null]
		log.info("createPost->result : " + result);
		
		if(result<1) {//등록 실패
			return "redirect:/create";
		}else {//등록 성공
			return "redirect:/detail?bookId="+bookVO.getBookId();
		}
		
	}
	
	

	//요청URI : /createAjax
	// json타입의 텍스트
	//요청파라미터 : {"title":"개똥이의 모험","category":"소설","price":"10000"}
	//요청방식 : post
	//골뱅이PostMapping(value="/createAjax" => 속성이 하나일 때 속성명을 생략가능
	@ResponseBody
	@PostMapping("/createAjax")
	public BookVO createAjax(@RequestBody BookVO bookVO) {
		log.info("createAjax->bookVO : " + bookVO);
		
		//도서 등록
		int result = this.bookService.createPost(bookVO);
		//BookVO [bookId=3, title=개똥이, category=소설, price=10000
		//				 , insertDate=null]
		log.info("createPost->result : " + result);
		
		//비동기 : asynchronous로 요청되었고
		//비동기로 응답(json타입의 텍스트로)
		//{"bookId":"3","title":"개똥이의 모험","category":"소설"
		//	,"price":"10000","insertDate":"23/10/30","content":""}
		return bookVO;
	}
	
	/*
	요청URI : /createFormAjax
	요청파라미터 : {title=개똥이의 모험,category=소설,price=12000
				,uploadFile=파일객체들}
	요청방식 : post
	비동기요청일때 리턴 시 ResponseBody를 사용하기. json데이터를 응답
	*/
	@ResponseBody
	@PostMapping("/createFormAjax")
	public Map<String,Object> createFormAjax(BookVO bookVO) {
		//BookVO(bookId=null, rnum=0, title=개똥이의 모험, category=소설
		//, price=12000, insertDate=null, content=null, 
		//uploadFile=[org.springframework.web.multipart.su..], 
		//atchFileDetailVOList=null)
		log.info("createFormAjax->bookVO : " + bookVO);
		
		//도서 등록
		int result = this.bookService.createPost(bookVO);
		//BookVO [bookId=BK0003, title=개똥이, category=소설, price=10000
		//				 , insertDate=null]
		log.info("createPost->result : " + result);
		
		Map<String,Object> map = new HashMap<String, Object>();
		/*json 
		  [
			  {"result":"success"},
			  {"bookId":"BK0005"}
		  ]
		*/
		map.put("result", "success");
		map.put("bookId",bookVO.getBookId());
		
		return map;
	}
	
	/*
	 요청URI : localhost/detail?bookId=15
	 요청파라미터 : bookId=15
	 요청방식 : get
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView detail(BookVO bookVO) {
		//bookVO{bookId:15,title=null,category=null,
		// price:0,insertDate=null}
		log.info("bookVO : " + bookVO);
		
		bookVO = this.bookService.detail(bookVO);
		//detail->bookVO(select후) : BookVO [bookId=15, title=제목15
		//, category=카테고리15, price=15000, insertDate=Tue Oct 24 09:50:55 KST 2023]
		//, content=, uploadFile=null, atchFileDetailVOList=첨부파일데이터
		log.info("detail->bookVO(select후) : " + bookVO);
		
		ModelAndView mav = new ModelAndView();
		
		//model : 데이터를 jsp로 넘겨줌
		//session.setAttribute("속성명",데이터)
		mav.addObject("data", bookVO);
		// prefix(접두어) : /WEB-INF/views/
		// viewName(경로): book/detail
		// suffix(접미어) : .jsp
		mav.setViewName("book/detail");
		
		return mav;		
	}
	
	/*
	 * 15 : 숫자형문자
	 요청URI : localhost/detail2/15
	 경로변수 : bookId
	 요청방식 : get
	 */
	@RequestMapping(value="/detail2/{bookId}",method=RequestMethod.GET)
	public ModelAndView detail2(
			@PathVariable("bookId") String bookId,
			BookVO bookVO) {
		//bookVO{bookId:15,title=null,category=null,
		// price:0,insertDate=null}
		log.info("detail2->bookId : " + bookId);
		log.info("detail2->bookVO : " + bookVO);
		
		bookVO = this.bookService.detail(bookVO);
		//detail->bookVO(select후) : BookVO [bookId=15, title=제목15
		//, category=카테고리15, price=15000, insertDate=Tue Oct 24 09:50:55 KST 2023]
		log.info("detail->bookVO(select후) : " + bookVO);
		
		ModelAndView mav = new ModelAndView();
		
		//model : 데이터를 jsp로 넘겨줌
		//session.setAttribute("속성명",데이터)
		mav.addObject("data", bookVO);
		// prefix(접두어) : /WEB-INF/views/
		// viewName(경로): book/detail
		// suffix(접미어) : .jsp
		mav.setViewName("book/detail");
		
		return mav;		
	}
	
	/*
	 요청URI : /updatePost
	 요청파라미터 : {bookId=15, title=제목15, category=카테고리15, price=15000,
	  				content=내용글, insertDate=null}
	 요청방식 : post
	 */
	@RequestMapping(value="/updatePost",method=RequestMethod.POST)
	public ModelAndView updatePost(BookVO bookVO, ModelAndView mav) {
		//updatePost->bookVO : BookVO(bookId=15, title=개똥이15, 
		// category=소설15, price=1500, insertDate=null, content=<p>ㅁㄴㅇㄹ</p>)
		log.info("updatePost->bookVO : " + bookVO);
		
		int result = this.bookService.updatePost(bookVO);
		log.info("result : " + result);//result : 1
		
		//뷰의 경로 세팅
		mav.setViewName("redirect:/detail?bookId="+bookVO.getBookId());
		
		return mav;
	}
	/*
	 요청URI : /deletePost
	 요청파라미터 : {bookId=15, title=제목15, category=카테고리15, price=15000,
	  				content=내용글, insertDate=null}
	 요청방식 : post
	 */
	@RequestMapping(value="/deletePost",method=RequestMethod.POST)
	public ModelAndView deletePost(BookVO bookVO, ModelAndView mav) {
		//deletePost->bookVO : BookVO(bookId=14, title=제목14, 
		//	category=카테고리14, price=14000, insertDate=null, content=)
		log.info("deletePost->bookVO : " + bookVO);
		
		int result = this.bookService.deletePost(bookVO);
		log.info("deletePost->result : " + result);//1
		
		//목록으로 요청 이동
		mav.setViewName("redirect:/list");
		
		return mav;
	}
	
	//도서 목록
	/*
	 요청URI : /list
	 요청파라미터(HTTP파라미터, QueryString) : /list?keyword=개똥이&currentPage=1
	 요청방식 : get
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value="keyword",required=false) String keyword,	
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			ModelAndView mav) {
		//list->keyword : null
		log.info("list->keyword : " + keyword);
		log.info("list->currentPage : " + currentPage);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyword",keyword);
		map.put("currentPage",currentPage);
		
		List<BookVO> bookVOList = this.bookService.list(map);
		log.info("list->bookVOList : " + bookVOList);
		
		int total = this.bookService.getTotal(map);
		log.info("list->total : " + total);
		
		ArticlePage<BookVO> data 
			= new ArticlePage<BookVO>(total, currentPage, 10, bookVOList);
		
		data.setUrl("/list");
		
		mav.addObject("keyword", keyword);
		mav.addObject("data", data);
		//prefix : /WEB-INF/views/
		//			book/list
		//suffix : .jsp
		mav.setViewName("book/list");
		
		//forwarding
		return mav;
	}
}









