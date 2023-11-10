package kr.or.ddit.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.dao.AtchFileDetailDao;
import kr.or.ddit.mapper.ArticleMapper;
import kr.or.ddit.service.ArticleService;
import kr.or.ddit.vo.ArticleVO;
import kr.or.ddit.vo.AtchFileDetailVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	AtchFileDetailDao atchFileDetailDAO;
	
	@Autowired
	String uploadFolder;
	
	
	@Override
	public String getArticleNo() {
		return articleMapper.getArticleNo();
	}
	
	@Transactional
	@Override
	public int createPost(ArticleVO articleVO) {

		log.info("createPost -> articleVO : " + articleVO);
		// ARTICLE 테이블에 INSERT
		int result = this.articleMapper.createPost(articleVO);
		log.info("createPost -> articleVO : " + articleVO);

		MultipartFile[] uploadFile = articleVO.getUploadFile();
		
		// 업로드 파일 처리
		result += uploadFile(uploadFile, articleVO.getArticleNo());
		
		
		return 0;
		
	}

	private int uploadFile(MultipartFile[] uploadFile, String articleNo) {

		// 파일 업로드 경로
//		String uploadFolder = "C:\\eGovFrameDev310\\workspace\\springProj\\src\\main\\webapp\\resources\\upload";
		log.info("uploadFolder : " + uploadFolder);
		
		String uploadFileName = "";
		
		int result = 0;
		
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("getFolder() : " + getFolder());
		log.info("uploadPath : " + uploadPath);
		
		// 해당 경로가 없으면 새로 생성
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			// upload파일은 파일이 안들어와도 length가 1이다
			// => 따라서 multipartFile의 파일명의 길이가 1보다 작으면(0이면)
			// 첨부파일이 없는 것으로 간주
			if(multipartFile.getOriginalFilename().length() < 1) {
				break;
			}
			
			log.info("-----------------------");
			log.info("파일명 : " + multipartFile.getOriginalFilename());
			log.info("크기 : " + multipartFile.getSize());
			log.info("MIME타입 : " + multipartFile.getContentType());
			
			// 파일명
			uploadFileName = multipartFile.getOriginalFilename();
			
			// 같은날 같은 이름의 이미지 업로드 시 파일명의 중복이 생기므로
			// 랜덤값을 생성하여 파일명 앞에 붙임
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			// uploadPath : 연/월/일 처리가 된 업로드 경로
			// uploadFileName : uuid 처리된 파일명
			File saveFile = new File(uploadPath, uploadFileName);
			
			try(FileOutputStream thumbnail = new FileOutputStream(
						new File(uploadPath, "t_" + uploadFileName))) {
				
				// 파일을 실제로 해당 경로/ 해당파일명으로 저장
				multipartFile.transferTo(saveFile);
				
				// 썸네일
				// 이미지 파일인지 확인
				if(checkImagetype(saveFile)) { // 이미지 파일이다
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
				}
				
				// ATCH_FILE_DETAIL 테이블에 INSERT
				// ATCH_FILE_ID, FILE_SN, FILE_STRE_COURS, STRE_FILE_NM
				// , ORIGNL_FILE_NM, FILE_EXTSN, FILE_CN, FILE_SIZE
				AtchFileDetailVO atchFileDetailVO = new AtchFileDetailVO();
				atchFileDetailVO.setAtchFileId(articleNo);
				atchFileDetailVO.setFileStreCours(uploadPath+"\\"+uploadFileName);
				atchFileDetailVO.setStreFileNm(
						getFolder().replaceAll("\\\\", "/")+"/" + uploadFileName );
				atchFileDetailVO.setOrignlFileNm(multipartFile.getOriginalFilename());
				atchFileDetailVO.setFileExtsn(
						uploadFileName.substring(uploadFileName.lastIndexOf(".")+1));
				atchFileDetailVO.setFileCn("");
				atchFileDetailVO.setFileSize(multipartFile.getSize());
				
				log.info("atchFileDetailVO : " + atchFileDetailVO);
				
				// 쿼리 실행
				result += this.atchFileDetailDAO.insertAtchFileDetail(atchFileDetailVO);
				
			}catch(IOException | IllegalStateException e) {
				log.error(e.getMessage());
			}
			
			
		}
		
		
		return result;
	}

	private boolean checkImagetype(File saveFile) {

		String contentType;
		
		try {
			contentType = Files.probeContentType(saveFile.toPath());
			log.info("contentType : " + contentType);
			
			return contentType.startsWith("image");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return false;
	}

	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String dateStr = sdf.format(date);
		
		return dateStr.replace("-", File.separator);
		
	}

	@Override
	public ArticleVO detail(String articleNo) {

		return articleMapper.detail(articleNo);
	}

}
