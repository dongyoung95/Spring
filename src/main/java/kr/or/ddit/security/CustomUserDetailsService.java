package kr.or.ddit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 1) 사용자 정보를 검색
		// username : 로그인 시 입력받은 회원의 아이디
		MemberVO memberVO = this.memberMapper.detail(username);
		log.info("loadUserByUsername -> memberVO : " + memberVO);
		
		/*
		 * memberVO(우리) -> user(시큐리티)
		 * -------------------
		 * userId         -> username
		 * userPw         -> password
		 * enabled        -> enabled
		 * auth들         -> authorities
		 */
		//MVC에서는 Controller로 리턴하지 않고, CustomUser로 리턴함
		//CustomUser : 사용자 정의 유저 정보. extends User를 상속받고 있음
		//2) 스프링 시큐리티의 User 객체의 정보로 넣어줌 => 프링이가 이제부터 해당 유저를 관리
		//User : 스프링 시큐리에서 제공해주는 사용자 정보 클래스
		return memberVO==null?null:new CustomUser(memberVO);
	}

}
