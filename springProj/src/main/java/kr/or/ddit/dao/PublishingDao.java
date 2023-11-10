package kr.or.ddit.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.PublishingVO;

@Repository
public class PublishingDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	//출판 등록
	//<insert id="insertPub" parameterType="publishingVO">
	public int insertPub(PublishingVO publishingVO) {
		return this.sqlSessionTemplate.insert("publishing.insertPub", publishingVO);
	}
	
}
