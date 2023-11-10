package kr.or.ddit.service.impl;

import java.io.File;
import java.io.IOException;
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
import kr.or.ddit.dao.BusinessDao;
import kr.or.ddit.mapper.BusinessMapper;
import kr.or.ddit.service.BusinessService;
import kr.or.ddit.vo.AtchFileDetailVO;
import kr.or.ddit.vo.BusinessVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {
	//DI, IoC
	@Autowired
	BusinessDao businessDao;
	//DI, IoC
	@Inject
	BusinessMapper businessMapper;
	
	//DI, IoC
	@Autowired
	AtchFileDetailDao atchFileDetailDao;
	//자바빈으로 등록된 업로드폴더 경로
	@Autowired
	String uploadFolder;
	
	//메서드에 트랜잭션 자동화 활성화
	@Transactional
	@Override
	public int createPost(BusinessVO businessVO) {
		//businessVO[bSiteno=null,...
		//BUSINESS 테이블에 insert
		int result = this.businessMapper.createPost(businessVO);
		//businessVO[bSiteno=BS0012,...
		
		//businessVO{bSiteno=null,bName=대전사업장,bAddress=대전,bTelno=042-111-2222
		// ,bAmount=12200000,uploadFile=파일객체}
		MultipartFile[] uploadFile = businessVO.getUploadFile();
		
		//						파일객체들,	사업장의 기본키 데이터
		result += uploadFile(uploadFile, businessVO.getbSiteno());
		
		return result;
	}

	//사업장 상세
	@Override
	public BusinessVO detail(BusinessVO businessVO) {
		return this.businessMapper.detail(businessVO);
	}

	//사업장 수정
	@Override
	public int updatePost(BusinessVO businessVO) {
		return this.businessMapper.updatePost(businessVO);
	}

	@Override
	public int deletePost(BusinessVO businessVO) {
		return this.businessMapper.deletePost(businessVO);
	}

	//사업장 목록
	@Override
	public List<BusinessVO> list(Map<String, Object> map) {
		return this.businessMapper.list(map);
	}

	//전체행의 수(검색 포함)
	@Override
	public int getTotal() {
		return this.businessMapper.getTotal();
	}
	
	//파일 업로드						파일객체들,				사업장 기본키 데이터					
	public int uploadFile(MultipartFile[] uploadFile, String bSiteno) {
//		String uploadFolder = "C:\\eGovFrameDev310\\workspace\\springProj\\src\\main\\webapp\\resources\\upload";
		//첨부파일 insert 성공건수
		int result = 0;
		
		//연월일
		File uploadPath = new File(uploadFolder, getFolder());
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			String uploadFileName = multipartFile.getOriginalFilename();
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile);
				
				//ATCH_FILE_DETAIL 테이블에 insert
				//ATCH_FILE_ID, FILE_SN, FILE_STRE_COURS, STRE_FILE_NM
				//, ORIGNL_FILE_NM, FILE_EXTSN, FILE_CN, FILE_SIZE
				AtchFileDetailVO atchFileDetailVO = new AtchFileDetailVO();
				atchFileDetailVO.setAtchFileId(bSiteno);	//P.K
				atchFileDetailVO.setFileSn(0);				//P.K
				atchFileDetailVO.setFileStreCours(
						uploadPath + "/" + uploadFileName);
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
				
				result += this.atchFileDetailDao.insertAtchFileDetail(atchFileDetailVO);
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
			}
		}
		return result;
	}
	
	//연월일 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date); 
		
		return str.replace("-", File.separator);
	}

	//다음 기본키 값 가져오기
	@Override
	public String getBSiteno() {
		return this.businessMapper.getBSiteno();
	}
}




