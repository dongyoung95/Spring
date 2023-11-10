package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ArticleVO {

	private String articleNo;
	private String writerId;
	private String writerName;
	private String title;
	private String artContent;
	private Date regdate;
	private Date moddate;
	private int readCnt;
	
	// 파일 객체들
	private MultipartFile[] uploadFile;
	
	// ARTICLE : ATCH_FILE_DETAIL = 1 : N
	private List<AtchFileDetailVO> atchFileDetailVOList;
	
}
