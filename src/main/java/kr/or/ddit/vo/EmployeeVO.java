package kr.or.ddit.vo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/*
애너테이션      |     설명
------------------------------------------
@NotEmpty   CharSequence, Map, Collection, Array가 not null이고 length() 또는 size()가 0이 아닐 것
@NotNull   field의 값이 not null 임
@Null   field의 값이 null일 것
@Size(min="m", max="n")   CharSequence의 length() 또는 Map, Collection, Array의 size()가 m 보다 크고 n 보다 작을 것
@UniqueElements   Collection 내의 요소들이 중복되지 않을 것
@Min(m), @Max(n)   각각 숫자형 field에 대해 최대, 최소 값  설정
@Range(min=i, max=a)   최소 i에서 최대 a 사이의 값일 것
@Digits   숫자 형태의 값으로만 구성될 것
@Email   이메일 형식을 갖출 것
@SafeHtml   전달된 HTML 내용이 안전한지 체크
@CreditCardNumber   문자열이 신용카드 형태인지 점검
@Future[OrPresent],
@Past[OrPresent]   Date, Calendar, time package 타입에 대해 각각 미래 또는 이전 시간이거나  같을 것
@URL   문자열이 URL 형식인지 검증
@Pattern(regexp="")   문자열 field가 정규 표현식에 적합할 것. null은 valid
@AssertTrue, @AssertFalse   각각 값이 true, false인지 검증(주로 메서드의 리턴 값으로 검증할 때)
 */

//PoJo(Plain Oriented Java Object)
public class EmployeeVO {
	@NotBlank(message = "직원번호는 필수입니다.")
	@Pattern(regexp = "^E[0-9]{1,3}", message = "E와 숫자를 조합하여 4자리까지 입력 가능합니다.")
	private String eEmpno;
	@NotBlank(message = "직원명은 필수입니다.")
	private String eName;
	@NotBlank(message = "우편번호는 필수입니다.")
	private String postNum;
	@NotBlank(message = "주소는 필수입니다.")
	private String eAddress;
	private String eAddress2;
	private String eTelno;
	@NotBlank
	private String ePosition;
	@NotBlank
	private String eDept;
	private int    rnum;
	
	private Map<String,String> eDeptMap;
	
	private String[] hobbyArray;
	
	//첨부파일 <input type="file" id="uploadFile" name="uploadFile" multiple />
	private MultipartFile[] uploadFile;
	
	//중첩된 자바빈(즈)
	//EMPLOYEE테이블 : ATCH_FILE_DETAIL테이블 = 1 : N
	@Valid
	private List<AtchFileDetailVO> atchFileDetailVOList;

	//중첩된 자바빈(즈)
	//EMPLOYEE : CARD = 1 : N
	@Valid
	private List<CardVO> cardVOList;
	
	public EmployeeVO() {}

	public String geteEmpno() {
		return eEmpno;
	}

	public void seteEmpno(String eEmpno) {
		this.eEmpno = eEmpno;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String geteAddress() {
		return eAddress;
	}

	public void seteAddress(String eAddress) {
		this.eAddress = eAddress;
	}

	public String geteTelno() {
		return eTelno;
	}

	public void seteTelno(String eTelno) {
		this.eTelno = eTelno;
	}

	public String getePosition() {
		return ePosition;
	}

	public String getPostNum() {
		return postNum;
	}

	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}

	public String geteAddress2() {
		return eAddress2;
	}

	public void seteAddress2(String eAddress2) {
		this.eAddress2 = eAddress2;
	}

	public void setePosition(String ePosition) {
		this.ePosition = ePosition;
	}

	public String geteDept() {
		return eDept;
	}

	public void seteDept(String eDept) {
		this.eDept = eDept;
	}
	
	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public MultipartFile[] getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile[] uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<AtchFileDetailVO> getAtchFileDetailVOList() {
		return atchFileDetailVOList;
	}

	public void setAtchFileDetailVOList(List<AtchFileDetailVO> atchFileDetailVOList) {
		this.atchFileDetailVOList = atchFileDetailVOList;
	}

	public List<CardVO> getCardVOList() {
		return cardVOList;
	}

	public void setCardVOList(List<CardVO> cardVOList) {
		this.cardVOList = cardVOList;
	}

	public Map<String, String> geteDeptMap() {
		return eDeptMap;
	}

	public void seteDeptMap(Map<String, String> eDeptMap) {
		this.eDeptMap = eDeptMap;
	}

	public String[] getHobbyArray() {
		return hobbyArray;
	}

	public void setHobbyArray(String[] hobbyArray) {
		this.hobbyArray = hobbyArray;
	}

	@Override
	public String toString() {
		return "EmployeeVO [eEmpno=" + eEmpno + ", eName=" + eName + ", postNum=" + postNum + ", eAddress=" + eAddress
				+ ", eAddress2=" + eAddress2 + ", eTelno=" + eTelno + ", ePosition=" + ePosition + ", eDept=" + eDept
				+ ", rnum=" + rnum + ", eDeptMap=" + eDeptMap + ", hobbyArray=" + Arrays.toString(hobbyArray)
				+ ", uploadFile=" + Arrays.toString(uploadFile) + ", atchFileDetailVOList=" + atchFileDetailVOList
				+ ", cardVOList=" + cardVOList + "]";
	}

	

	
}
