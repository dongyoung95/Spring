package kr.or.ddit.mapper;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BusinessVO;

public interface BusinessMapper {
	//<insert id="createPost" parameterType="businessVO">
	public int createPost(BusinessVO businessVO);

	//사업장 상세
	//<select id="detail" parameterType="businessVO" resultMap="businessMap">
	public BusinessVO detail(BusinessVO businessVO);

	//사업장 수정
	//<update id="updatePost" parameterType="businessVO">
	public int updatePost(BusinessVO businessVO);

	//사업장 삭제
	//<delete id="deletePost" parameterType="businessVO">
	public int deletePost(BusinessVO businessVO);

	//사업장 목록
	//<select id="list" parameterType="hashMap" resultType="businessVO">
	public List<BusinessVO> list(Map<String, Object> map);

	//전체행의 수(검색 포함)
	//<select id="getTotal" resultType="int">
	public int getTotal();

	//다음 기본키 값 가져오기
	public String getBSiteno();
}
