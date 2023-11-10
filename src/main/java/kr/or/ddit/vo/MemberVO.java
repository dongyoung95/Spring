package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

// POJO(순수 자바 코드를 쓰기로 약속)를 거스름
@Data
public class MemberVO {

	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private int coin;
	private Date regDate;
	private Date updDate;
	private String enabled;
	
	// MEMBER : MEMBER_AUTH = 1 : N
	private List<MemberAuthVO> memberAuthVOList;
	
	
}
