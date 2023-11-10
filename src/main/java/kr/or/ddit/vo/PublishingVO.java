package kr.or.ddit.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PublishingVO {
	private String pubId;
	private String bookId;
	private String wrId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pubDt;
	private Date regDt;
}
