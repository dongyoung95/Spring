package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BusinessVO;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.WorkVO;

public interface WorkService {
	//메소드 시그니터
	
	//직원 가져옴
	public List<EmployeeVO> getEmployee();

	//사업장 가져옴
	public List<BusinessVO> getBusiness();

	//근무테이블 insert
	public int createPost(WorkVO workVO);

	//근무 상세 
	public WorkVO detail(WorkVO workVO);

	//근무 수정
	public int updatePost(WorkVO workVO);

	//근무 삭제
	public int deletePost(WorkVO workVO);

	//근무 목록
	public List<WorkVO> list(Map<String, Object> map);

	//전체 행의 수
	public int getTotal(Map<String, Object> map);

	//기본키 생성
	public String getWorkId();
	
}
