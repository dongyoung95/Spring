package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BusinessVO;

public interface BusinessService {
	//메소드 시그니처 : 인터페이스의 메소드랑 클래스의 메소드는 동일
	//사업장 등록
	public int createPost(BusinessVO businessVO);

	//사업장 상세
	public BusinessVO detail(BusinessVO businessVO);

	//사업장 수정
	public int updatePost(BusinessVO businessVO);

	//사업장 삭제
	public int deletePost(BusinessVO businessVO);

	//사업장 목록
	public List<BusinessVO> list(Map<String, Object> map);

	//전체행의 수(검색 포함)
	public int getTotal();

	//다음 기본키 값 가져오기
	public String getBSiteno();

}
