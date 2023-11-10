package kr.or.ddit.vo;

import lombok.Data;

// POJO(순수 자바 코드를 사용하자)를 역행
@Data
public class MemberAuthVO {

	private int userNo;
	private String auth;
	
}
