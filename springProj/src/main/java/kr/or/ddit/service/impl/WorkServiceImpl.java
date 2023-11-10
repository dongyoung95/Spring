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

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.dao.AtchFileDetailDao;
import kr.or.ddit.dao.WorkDao;
import kr.or.ddit.mapper.WorkMapper;
import kr.or.ddit.service.WorkService;
import kr.or.ddit.vo.AtchFileDetailVO;
import kr.or.ddit.vo.BusinessVO;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.WorkVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@Service
public class WorkServiceImpl implements WorkService {
	
	@Autowired
	WorkDao workDao;
	
	//DI, IoC
	@Inject
	WorkMapper workMapper;
	
	@Autowired
	AtchFileDetailDao atchFileDetailDao;
	
	//직원 가져옴
	@Override
	public List<EmployeeVO> getEmployee() {
		return this.workMapper.getEmployee();
	}
	
	//사업장 가져옴
	@Override
	public List<BusinessVO> getBusiness() {
		return this.workMapper.getBusiness();
	}
	
	//근무테이블 insert
	@Override
	public int createPost(WorkVO workVO) {
		//1) WORK테이블에 insert
		int result = this.workMapper.createPost(workVO);
		
		MultipartFile[] uploadFile = workVO.getUploadFile();
		
		//2) 첨부파일 업르드 및 테이블에 insert
		//						파일객체들,	  근무의 기본키 데이터
		result += uploadFile(uploadFile, workVO.getWorkId());
		
		return result;
	}
	
	//근무 상세 
	@Override
	public WorkVO detail(WorkVO workVO) {
		return this.workMapper.detail(workVO);
	}
	
	//수정처리
	@Transactional
	@Override
	public int updatePost(WorkVO workVO) {
		//work테이블 update
		int result = this.workMapper.updatePost(workVO);
		
		//첨부파일 처리
		MultipartFile[] uploadFile = workVO.getUploadFile();
		//1) 첨부파일이 없을때->아무것도 안함		
		//2) 첨부파일이 있을때->ATCH_FILE_DETAIL테이블의 데이터 삭제 -> 새로운 파일을 업로드 후 -> 다시 insert
		uploadFile(uploadFile, workVO.getWorkId());
		
		return result;
	}
	
	//근무테이블 삭제(첨부파일테이블(자식테이블)삭제 후 근무테이블 삭제(부모테이블))
	@Transactional
	@Override
	public int deletePost(WorkVO workVO) {
		//1) 첨부파일 데이터 삭제(자식)
		int result =  this.workMapper.delAtch(workVO.getWorkId());
		
		//2) 근무테이블 삭제(부모)
		result = this.workMapper.deletePost(workVO);
		
		return result;
	}
	
	//근무 목록
	@Override
	public List<WorkVO> list(Map<String,Object> map){
		return this.workMapper.list(map);
	}
	
	//전체 행의 수(total) 
	//Map{keyword=김덕배,currentPage=1}
	@Override
	public int getTotal(Map<String,Object> map) {
		return this.workMapper.getTotal(map);
	}
	
	//파일처리 메소드
	public int uploadFile(MultipartFile[] uploadFile, String workId) {
		
		//파일이 업로드 될 경로
		String uploadFolder = "C:\\eGovFrameDev310\\workspace\\springProj\\src\\main\\webapp\\resources\\upload"; 
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
		
		//uploadFile : MultipartFile[] uploadFile
		//uploadFile[0] : MultipartFile 1개
		if(uploadFile[0].getOriginalFilename().length()>0) {
			//ATCH_FILE_DETAIL 테이블의 데이터를 삭제
			this.workMapper.delAtch(workId);
			
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
						atchFileDetailVO.setAtchFileId(workId);	//P.K
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
		}//end if
		
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

	//기본키 생성
	@Override
	public String getWorkId() {
		return this.workMapper.getWorkId();
	}
}



