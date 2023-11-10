package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BookVO;

//매퍼xml(book_SQL.xml)을 실행시키는 
//DAO(Data Access Object) 클래스
//Repository 어노테이션 : 데이터에 접근하는 클래스
//스프링이 데이터를 관리하는 클래스라고 인지하여 자바빈으로 등록하여 관리함
@Repository
public class BookDao {
	//DI(Dependency Injection) : 의존성 주입
	//new 키워드를 통해 직접 생성하지 않고!!!
	//스프링이 미리 만들어 놓은(서버 실행 시 스프링이 미리 xml을 읽어
	//객체를 인스턴스화 해놓음)
	//sqlSessionTemplate 타입 객체를 BookDao 객체에 주입함
	@Autowired
	SqlSessionTemplate sqlSessionTemplate; 
	
	//도서 등록
	public int createPost(BookVO bookVO) {
		//book_SQL.xml 파일의 namespace가 book이고, id가 createPost인
		//태그를 찾아 그 안에 들어있는 sql을 실행함
		//bookVO=>{"bookId":"","title":"총알탄 개똥이","category":"소설","price":10000,"insertDate":""}
		//insert,update,delete는 반영된 건수가 return됨
		//insert성공 : 1이상, 실패면 0
		return this.sqlSessionTemplate.insert("book.createPost",bookVO);
	}

	//도서 상세
	public BookVO detail(BookVO bookVO) {
		//쿼리를 실행해주는 객체?(힌트 : root_context.xml)
		//selectOne() 메소드 : 1행을 가져올 때 사용 / selectList() 메소드 : 결과 집합 목록 반환(다중행)
		//결과 행 수가 0일 때? null을 반환
		//결과 행 수가 2 이상일 때? TooManyResultsException 예외 발생
		//selectOne("namespace.id", 파라미터)
		return this.sqlSessionTemplate.selectOne("book.detail", bookVO);
	}

	//도서 변경
	public int updatePost(BookVO bookVO) {
		//update("namespace.id",파라미터)
		return this.sqlSessionTemplate.update("book.updatePost", bookVO);
	}

	//도서 삭제
	public int deletePost(BookVO bookVO) {
		//delete("namespace.id",파라미터)
		return this.sqlSessionTemplate.delete("book.deletePost", bookVO);
	}

	//도서 목록
	public List<BookVO> list(Map<String,Object> map) {
		// selectList("namespace값.id",파라미터)
		return this.sqlSessionTemplate.selectList("book.list", map);
	}

	public int getTotal(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("book.getTotal", map);
	}
	
}



