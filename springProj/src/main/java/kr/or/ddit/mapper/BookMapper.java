package kr.or.ddit.mapper;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookVO;

public interface BookMapper {
	
	//DAO -> 도서 목록
	//public List<BookVO> list(Map<String,Object> map) {
	
	//Mapper Interface -> 도서 목록
	//<select id="list" parameterType="hashMap" resultType="bookVO">
	public List<BookVO> list(Map<String,Object> map);

	//<select id="getTotal" parameterType="hashMap" resultType="int">
	public int getTotal(Map<String, Object> map);

	//<insert id="createPost" parameterType="bookVO">
	public int createPost(BookVO bookVO);

	//<select id="detail" parameterType="bookVO" resultMap="bookMap">
	public BookVO detail(BookVO bookVO);

	//<update id="updatePost" parameterType="bookVO">
	public int updatePost(BookVO bookVO);

	//<delete id="deletePost" parameterType="bookVO">
	public int deletePost(BookVO bookVO);
	
	//기본키 값 가져오기 -->
	//<select id="getBookId" resultType="String">
	public String getBookId();
}



