package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookVO;

//서비스 interface : 비즈니스 로직(기능)
//휴대폰 수리
public interface BookService {
	
	//도서 등록
	public int createPost(BookVO bookVO);

	//도서 상세 보기
	public BookVO detail(BookVO bookVO);

	//도서 변경
	public int updatePost(BookVO bookVO);

	//도서 삭제
	public int deletePost(BookVO bookVO);

	//도서 목록
	public List<BookVO> list(Map<String,Object> map);

	//전체 건수
	public int getTotal(Map<String, Object> map);

	//기본키 값 가져오기
	public String getBookId();
	
	
}
