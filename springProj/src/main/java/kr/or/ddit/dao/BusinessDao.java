package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BusinessVO;

@Repository
public class BusinessDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public int createPost(BusinessVO businessVO) {
		return this.sqlSessionTemplate.insert("business.createPost", businessVO);
	}

	//사업장 상세
	public BusinessVO detail(BusinessVO businessVO) {
		return this.sqlSessionTemplate.selectOne("business.detail", businessVO);
	}

	//사업장 수정
	public int updatePost(BusinessVO businessVO) {
		return this.sqlSessionTemplate.update("business.updatePost", businessVO);
	}

	//사업장 삭제
	public int deletePost(BusinessVO businessVO) {
		return this.sqlSessionTemplate.delete("business.deletePost", businessVO);
	}

	//사업장 목록
	public List<BusinessVO> list(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("business.list", map);
	}

	//전체행의 수(검색 포함)
	public int getTotal() {
		return this.sqlSessionTemplate.selectOne("business.getTotal");
	}

}





