package kr.or.ddit.mapper;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BusinessVO;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.WorkVO;

public interface WorkMapper {
	//직원 가져옴
//	<select id="getEmployee" resultType="employeeVO">
	public List<EmployeeVO> getEmployee();
	
	//사업장 가져옴
	//<select id="getBusiness" resultType="businessVO">
	public List<BusinessVO> getBusiness();
	
	//근무테이블 insert
	//<insert id="createPost" parameterType="businessVO">
	public int createPost(WorkVO workVO);
	
	//근무 상세 
	//<select id="detail" parameterType="workVO" resultType="workVO">
	public WorkVO detail(WorkVO workVO);
	
	//수정처리
	//<update id="updatePost" parameterType="workVO">
	public int updatePost(WorkVO workVO);
	
	/*수정 모드에서 첨부파일을 수정하고자 할 때 ATCH_FILE_DETAIL테이블의 데이터를 먼저 삭제함 
	파라미터 : String atchFileId
	*/
	//<delete id="delAtch" parameterType="String">
	public int delAtch(String atchFileId);
	
	//근무테이블 삭제
	//<delete id="deletePost" parameterType="workVO">
	public int deletePost(WorkVO workVO);
	
	//근무 목록
	//<select id="list" parameterType="hashMap" resultMap="workListMap">
	public List<WorkVO> list(Map<String,Object> map);
	
	//전체 행의 수(total) 
	//Map{keyword=김덕배,currentPage=1}
	//<select id="getTotal" parameterType="hashMap">
	public int getTotal(Map<String,Object> map);

	//기본키 가져오기
	public String getWorkId();
}
