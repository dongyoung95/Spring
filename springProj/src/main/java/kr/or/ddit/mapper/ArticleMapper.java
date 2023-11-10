package kr.or.ddit.mapper;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.ArticleVO;
import kr.or.ddit.vo.BookVO;

public interface ArticleMapper {
	

	//Mapper Interface -> 도서 목록
	//<select id="list" parameterType="hashMap" resultType="bookVO">
	public List<ArticleVO> list(Map<String,Object> map);

	//<select id="getTotal" parameterType="hashMap" resultType="int">
	public int getTotal(Map<String, Object> map);




	//<delete id="deletePost" parameterType="bookVO">
	public int deletePost(ArticleVO articleVO);
	

	//<update id="updatePost" parameterType="bookVO">
	public int updatePost(ArticleVO articleVO);
	
	
	
	
	//<insert id="createPost" parameterType="bookVO">
	public int createPost(ArticleVO articleVO);
	
	//기본키 값 가져오기 -->
	//<select id="getArticleNo" resultType="String">
	public String getArticleNo();
	
	// articleNo를 이용하여 해당 article 정보 가져오기
	//<select id="detail" parameterType="bookVO" resultMap="bookMap">
	public ArticleVO detail(String articleNo);
	
}



