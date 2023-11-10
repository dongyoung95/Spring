package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.EmployeeVO;

@Repository
public class EmployeeDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public int createPost(EmployeeVO employeeVO) {
		return this.sqlSessionTemplate.insert("employee.createPost",employeeVO);
	}

	public EmployeeVO detail(EmployeeVO employeeVO) {
		return this.sqlSessionTemplate.selectOne("employee.detail", employeeVO);
	}

	public int updatePost(EmployeeVO employeeVO) {
		return this.sqlSessionTemplate.update("employee.updatePost", employeeVO);
	}

	public int deletePost(EmployeeVO employeeVO) {
		return this.sqlSessionTemplate.delete("employee.deletePost", employeeVO);
	}

	public List<EmployeeVO> list(Map<String,Object> map) {
		return this.sqlSessionTemplate.selectList("employee.list", map);
	}

	public int getTotal(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("employee.getTotal", map);
	}
	
}
