package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//자바빈 클래스(멤버변수, 기본생성자, getter/setter메소드)
//PoJo(Plain oriented Java Object) : 본연의 자바를 사용하자
//lombok을 쓰면 PoJo가 침해받음
/*
Bean Validation이 제공하는 제약 애너테이션
 - NotNull : 빈 값 체크
 - NotBlank : null 체크, trim후 길이가 0인지 체크
 - Size : 글자 수 체크
 - Email : 이메일 주소 형식 체크
 - Past : 오늘보다 과거 날짜(ex. 생일)
 - Future : 미래 날짜 체크(ex. 예약일)
*/
@Data
public class BookVO {
	//{"title":"총알탄 개똥이","category":"소설","price":10000,"uploadFile":"파일객체"}
	//멤버변수, 프로퍼티
	@NotBlank(message = "도서 아이디는 필수입니다.") // 기본 에러메시지 커스텀
	private String bookId;//NUMBER -> VARCHAR2(20)
	private int rnum;
	@NotBlank
	private String title;
	@NotBlank
	private String category;
	private int price;
	private Date insertDate;
	private String content;
	//<input type="date"... => 2023-11-01(문자) -> 날짜로 변환
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pubDt;	//출판일
	
	//파일객체들
	private MultipartFile[] uploadFile;
	
	//BOOK테이블 : ATCH_FILE_DETAIL테이블 = 1 : N
	private List<AtchFileDetailVO> atchFileDetailVOList;
	
	//여러명의 저자를 받기
	//BOOK : WRITER = 1 : N
	private List<WriterVO> writerVOList;
}


