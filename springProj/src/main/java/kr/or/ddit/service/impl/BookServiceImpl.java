package kr.or.ddit.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.dao.AtchFileDetailDao;
import kr.or.ddit.dao.BookDao;
import kr.or.ddit.dao.PublishingDao;
import kr.or.ddit.mapper.BookMapper;
import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.AtchFileDetailVO;
import kr.or.ddit.vo.BookVO;
import kr.or.ddit.vo.PublishingVO;
import kr.or.ddit.vo.WriterVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

//서비스 클래스 : 비즈니스 로직(기능 수행)
//스프링 MVC 구조에서 Controller와 DAO를 연결하는 역할
/*
스프링 프레임워크는 직접 클래스를 생성하는 것을 지양하고,
* 프링은 인터페이스를 좋아해. 자꾸자꾸 좋아지면 Impl은 어떡해
인터페이스를 통해 접근하는 것을 권장하고 있기 때문.(확장성, 유연성)
그래서 서비스 레이어는 인터페이스(BookService)와 클래스(BookServiceImpl)를 함께 사용함

Impl : implement의 약자
*/
//"프링아 이 클래스 서비스 클래야"라고 알려주자. 프링이가 자바빈으로 등록해줌.
@Slf4j
@Service
public class BookServiceImpl implements BookService {
	//데이터베이스 접근을 위해 BookDao 인스턴스를 주입받자
	@Autowired
	BookDao bookDao;
	
	//DI, IoC
	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	AtchFileDetailDao atchFileDetailDao;
	
	//root-context.xml에서 빈으로 등록해놓은 upload폴더 경로를 주입받음
	@Autowired
	String uploadFolder;
	
	@Autowired
	PublishingDao publishingDao; 
	
	//도서 등록
	//Override어노테이션 : 메소드 재정의
	//bookVO : BookVO [bookId=null, title=개똥이, category=소설, price=10000
	//				 , insertDate=null,uploadFile=파일객체]
	@Transactional
	@Override
	public int createPost(BookVO bookVO) {
		//1) BOOK테이블에 insert
		//BookVO [bookId=null..
		int result = this.bookMapper.createPost(bookVO);
		//BookVO [bookId=BK0001..
		
		MultipartFile[] uploadFile = bookVO.getUploadFile();
		
		//파일처리 메소드 호출
		result += uploadFile(uploadFile, bookVO.getBookId());
		
		//출판 등록
		List<WriterVO> writerVOList = bookVO.getWriterVOList();
		/*
		INSERT INTO PUBLISHING(PUB_ID, BOOK_ID, WR_ID, PUB_DT, REG_DT)
		VALUES(샵{pubId}, 샵{bookId}, 샵{wrId}, 샵{pubDt}, sysdate)
		 */
		for(WriterVO writerVO : writerVOList) {
			PublishingVO publishingVO = new PublishingVO();
			publishingVO.setBookId(bookVO.getBookId());//도서 아이디
			publishingVO.setWrId(writerVO.getWrId());//저자 아이디
			publishingVO.setPubDt(bookVO.getPubDt());//출판일
			
			log.info("publishingVO : " + publishingVO);
			
			result += this.publishingDao.insertPub(publishingVO);
		}
		
		return result;
	}

	//도서 상세
	@Override
	public BookVO detail(BookVO bookVO) {
		return this.bookMapper.detail(bookVO);
	}

	//도서 변경
	@Override
	public int updatePost(BookVO bookVO) {
		return this.bookMapper.updatePost(bookVO);
	}

	//도서 삭제
	@Override
	public int deletePost(BookVO bookVO) {
		return this.bookMapper.deletePost(bookVO);
	}

	//도서 목록
	@Override
	public List<BookVO> list(Map<String,Object> map) {
		//return this.bookDao.list(map);
		return this.bookMapper.list(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		//return this.bookDao.getTotal(map);
		return this.bookMapper.getTotal(map);
	}

	//파일처리 메소드
	public int uploadFile(MultipartFile[] uploadFile, String bookId) {
		
		//파일이 업로드 될 경로
//		String uploadFolder = "C:\\eGovFrameDev310\\workspace\\springProj\\src\\main\\webapp\\resources\\upload"; 
		String uploadFileName = "";
		log.info("uploadFile->uploadFolder : " + uploadFolder);
		
		int result = 0;
		
		//연월일 폴더 만들기 시작/////////////////////
		// , : \\
		//...\\upload\\2023\\10\\31
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("uploadPath : " + uploadPath);
		
		//만약 연/월/일 해당 폴더가 없으면 생성
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		//연월일 폴더 만들기 끝/////////////////////
		
		//파일객체배열로부터 파일을 하나씩 꺼내보자
		for(MultipartFile multipartFile : uploadFile) {
			log.info("-------------------");
			log.info("파일명 : " + multipartFile.getOriginalFilename());
			log.info("크기 : " + multipartFile.getSize());
			log.info("MIME타입 : " + multipartFile.getContentType());
			
			//파일명
			uploadFileName = multipartFile.getOriginalFilename();
			
			// 같은날 같은 이미지를 업로드 시 파일 중복 방지 시작//////////////
			//java.util.UUID => 랜덤값 생성
			UUID uuid = UUID.randomUUID();	//임의의 값을 생성
			//원래의 파일 이름과 구분하기 위해서 _를 붙임
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			// 같은날 같은 이미지를 업로드 시 파일 중복 방지 끝//////////////
			
			//File 객체 설계(복사할 대상 경로, 파일명)
			//uploadPath : 연월일 처리된 경로
			//uploadFileName : uuid 처리된 파일명
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				//파일 복사가 일어남
				//파일객체.transferTo(설계)
				multipartFile.transferTo(saveFile);
				
				//썸네일
				//이미지인지 체킹
				if(checkImagetype(saveFile)) {//이미지라면..
					//설계
					FileOutputStream thumbnail = new FileOutputStream(
						new File(uploadPath, "s_" + uploadFileName)
					);
					//섬네일 생성
					Thumbnailator.createThumbnail(multipartFile.getInputStream(),thumbnail,100,100);
					thumbnail.close();
				}
				
				//ATCH_FILE_DETAIL 테이블에 insert
				//ATCH_FILE_ID, FILE_SN, FILE_STRE_COURS, STRE_FILE_NM
				//, ORIGNL_FILE_NM, FILE_EXTSN, FILE_CN, FILE_SIZE
				AtchFileDetailVO atchFileDetailVO = new AtchFileDetailVO();
				atchFileDetailVO.setAtchFileId(bookId);	//P.K
				atchFileDetailVO.setFileSn(0);				//P.K
				atchFileDetailVO.setFileStreCours(
						uploadPath + "\\" + uploadFileName);
				// 2023\\10\\31\\safdlkdsfj_개똥이2.jpg
				// getFolder() : 2023\\10\\31
				// uploadFileName : safdlkdsfj_개똥이2.jpg	
				// 2023/10/31/safdlkdsfj_개똥이2.jpg				
				atchFileDetailVO.setStreFileNm(
						getFolder().replaceAll("\\\\", "/") + "/" + uploadFileName);
				atchFileDetailVO.setOrignlFileNm(multipartFile.getOriginalFilename());
				atchFileDetailVO.setFileExtsn(
						uploadFileName.substring(uploadFileName.lastIndexOf(".")+1));
				atchFileDetailVO.setFileCn("");
				atchFileDetailVO.setFileSize(multipartFile.getSize());
				
				log.info("atchFileDetailVO : " + atchFileDetailVO);
				
				//쿼리 실행
				result += this.atchFileDetailDao.insertAtchFileDetail(atchFileDetailVO);
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
			}
		}//end for
		
		return result;
	}
	
	//용량이 큰 파일을 섬네일 처리를 하지 않으면
	//모바일과 같은 환경에서 많은 데이터를 소비해야 하므로
	//이미지의 경우 특별한 경우가 아니면 섬네일을 제작해야 함.
	//섬네일은 이미지만 가능함.
	//이미지 파일의 판단
	private boolean checkImagetype(File file) {
		/* Multipurpose Internet Mail Extensions
		 .jpeg / .jpg(JPEG 이미지)의 MIME 타입 : image/jpeg
		 */
		//MIME 타입을 통해 이미지 여부 확인
		//file.toPath() : 파일 객체를 path객체로 변환
		String contentType;
		try {
			contentType = Files.probeContentType(file.toPath());
			log.info("contentType : " + contentType);		
			
			return contentType.startsWith("image");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return false;
	}

	//연/월/일 폴더 생성
	private String getFolder() {
		//2023-10-31형식(format) 지정
		//간단 날짜 형식
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//날짜 객체 생성(java.util패키지)
		Date date = new Date();
		//2023-10-31
		String str = sdf.format(date);
		//윈도의 경로는 역슬러시 두개 2023\\10\\31
		return str.replace("-", File.separator);
	}
	
	//기본키 값 가져오기
	@Override
	public String getBookId() {
		return this.bookMapper.getBookId();
	}
}



