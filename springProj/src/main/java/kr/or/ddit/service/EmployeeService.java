package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.EmployeeVO;

public interface EmployeeService {

	public int createPost(EmployeeVO employeeVO);

	public EmployeeVO detail(EmployeeVO employeeVO);

	public int updatePost(EmployeeVO employeeVO);

	public int deletePost(EmployeeVO employeeVO);

	public List<EmployeeVO> list(Map<String,Object> map);

	public int getTotal(Map<String, Object> map);

}
