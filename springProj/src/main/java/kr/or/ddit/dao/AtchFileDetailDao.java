package kr.or.ddit.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.AtchFileDetailVO;

@Repository
public class AtchFileDetailDao {
	//DI, IoC
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	//ATCH_FILE_DETAIL 테이블에 insert
	public int insertAtchFileDetail(AtchFileDetailVO atchFileDetailVO) {
		//namespace.id
		return this.sqlSessionTemplate.insert("atchFileDetail.insertAtchFileDetail"
							,atchFileDetailVO);
	}
	
}
