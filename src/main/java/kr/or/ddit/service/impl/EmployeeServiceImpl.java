package kr.or.ddit.service.impl;

import java.io.File;
import java.io.IOException;
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
import kr.or.ddit.dao.EmployeeDao;
import kr.or.ddit.service.EmployeeService;
import kr.or.ddit.vo.AtchFileDetailVO;
import kr.or.ddit.vo.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
	//DI(의존성 주입), IoC(제어의 역전)
	@Autowired
	EmployeeDao employeeDao;
	//DI, IoC
	@Autowired
	AtchFileDetailDao atchFileDetailDao;
	
	@Transactional
	@Override
	public int createPost(EmployeeVO employeeVO) {
		//EMPLOYEE 테이블에 insert
		//employeeVO[eEmpno=null..
		int result = this.employeeDao.createPost(employeeVO);
		//employeeVO[eEmpno=1023..
		
		MultipartFile[] uploadFile = employeeVO.getUploadFile();
		log.info("uploadFile : " + uploadFile);
		log.info("uploadFile : " + uploadFile.length);
		
		
		//파일업로드     파일객체들			기본키 데이터
		uploadFile(uploadFile, employeeVO.geteEmpno());
		
		return result;
	}

	@Override
	public EmployeeVO detail(EmployeeVO employeeVO) {
		return this.employeeDao.detail(employeeVO);
	}

	@Override
	public int updatePost(EmployeeVO employeeVO) {
		return this.employeeDao.updatePost(employeeVO);
	}

	@Override
	public int deletePost(EmployeeVO employeeVO) {
		return this.employeeDao.deletePost(employeeVO);
	}

	@Override
	public List<EmployeeVO> list(Map<String,Object> map) {
		return this.employeeDao.list(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		return this.employeeDao.getTotal(map);
	}
	
	//파일 업로드
	public String uploadFile(MultipartFile[] uploadFile, String eEmpno) {
		String uploadFolder = "C:\\eGovFrameDev310\\workspace\\springProj\\src\\main\\webapp\\resources\\upload";
		
		int result = 0;
		
		//연월일
		File uploadPath = new File(uploadFolder, getFolder());
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("multipartFile : " + multipartFile);
			String uploadFileName = multipartFile.getOriginalFilename();
			//////////!!!!!!!!!!!첨부파일 없을경우!!!!!!!!!!!!!!/////////////////
			if(multipartFile.getOriginalFilename().length() < 1) {
				log.error("파일 미첨부");
				break;
			}
			//////////!!!!!!!!!!!첨부파일 없을경우!!!!!!!!!!!!!!/////////////////
			
			log.error("파일 첨부");
			
			log.info("uploadFileName : " + uploadFileName);
//			if(uploadFileName == null || uploadFileName.isEmpty()) {
//				return;
//			}
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				//파일 업로드 수행
				multipartFile.transferTo(saveFile);
				
				//ATCH_FILE_DETAIL 테이블에 insert
				//ATCH_FILE_ID, FILE_SN, FILE_STRE_COURS, STRE_FILE_NM
				//, ORIGNL_FILE_NM, FILE_EXTSN, FILE_CN, FILE_SIZE
				AtchFileDetailVO atchFileDetailVO = new AtchFileDetailVO();
				atchFileDetailVO.setAtchFileId(eEmpno);	//P.K
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
				
				result += this.atchFileDetailDao.insertAtchFileDetail(atchFileDetailVO);
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
			}
		}
		return "success";
	}
	
	//연월일 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date); 
		
		return str.replace("-", File.separator);
	}
	
}
