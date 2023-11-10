package kr.or.ddit.mapper;

import kr.or.ddit.vo.MemberVO;

public interface MemberMapper {

	// 로그인 시 필요한 회원정보 검색
	public MemberVO detail(String username);

	
}
