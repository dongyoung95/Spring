package kr.or.ddit.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.or.ddit.vo.MemberVO;

// CustomUser : principal
public class CustomUser extends User{

	/*
	private String password;
	private final String username;
	private final Set<GrantedAuthority> authorities;
	private final boolean accountNonExpired;(x)
	private final boolean accountNonLocked;(x)
	private final boolean credentialsNonExpired;(x)
	private final boolean enabled;(x)
	 */
	private MemberVO memberVO;

	public CustomUser(String username, String password, 
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	// return memberVO==null?null:new CustomUser(memberVO);
	public CustomUser(MemberVO memberVO) {
		//사용자가 정의한 (select한) MemberVO 타입의 객체 memberVO를
	    //스프링 시큐리티에서 제공해주고 있는 UsersDetails 타입으로 변환
	    //회원정보를 보내줄테니 이제부터 스프링이 관리해라
		//      a001				$2a$10$vYhd/HepAAz0Asq.6H7POuLgGvkKse88.Hdn.sttGa78KKJAfPGqC
		super(memberVO.getUserId(), memberVO.getUserPw(), 
				// 권한들
				memberVO.getMemberAuthVOList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
				.collect(Collectors.toList())
				);
		this.memberVO = memberVO;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	

}
