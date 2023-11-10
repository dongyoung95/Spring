package kr.or.ddit.vo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public class BusinessVO {
	//BS0069
	@NotBlank
	@Pattern(regexp = "^BS[0-9]{4}$", message = "BS로 시작하고, 4자리의 숫자를 입력해야 합니다")
	private String bSiteno;
	@NotBlank(message = "사업장명은 필수입니다.")
	private String bName;	//사업장명
	@NotBlank(message = "사업장 주소는 필수입니다.")
	private String bAddress; //사업장 주소
	@NotBlank(message = "사업장 연락처는 필수입니다.")
	private String bTelno; //사업장 연락처
	private long bAmount; //사업장 보유금액(B_AMOUNT NUMBER(12))
	private int bManqty;
	private Date bStrdate;
	private Date bPredate;
	private Date bEnddate;
	private String bRemark;
	private int rnum;	//행번호
	
	//첨부파일
	//<input type="file" class="form-control form-control-user"
	//			id="uploadFile" name="uploadFile" 
	private MultipartFile[] uploadFile;
	
	//관계차수 => 1:1, 1:N, N:M(DB에서 처리할 수 없음)
	//BUSINESS테이블 : ATCH_FILE_DETAIL테이블 = 1 : N
	private List<AtchFileDetailVO> atchFileDetailVOList;
	
	public BusinessVO() {}

	public String getbSiteno() {
		return bSiteno;
	}

	public void setbSiteno(String bSiteno) {
		this.bSiteno = bSiteno;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbAddress() {
		return bAddress;
	}

	public void setbAddress(String bAddress) {
		this.bAddress = bAddress;
	}

	public String getbTelno() {
		return bTelno;
	}

	public void setbTelno(String bTelno) {
		this.bTelno = bTelno;
	}

	public long getbAmount() {
		return bAmount;
	}

	public void setbAmount(long bAmount) {
		this.bAmount = bAmount;
	}

	public int getbManqty() {
		return bManqty;
	}

	public void setbManqty(int bManqty) {
		this.bManqty = bManqty;
	}

	public Date getbStrdate() {
		return bStrdate;
	}

	public void setbStrdate(Date bStrdate) {
		this.bStrdate = bStrdate;
	}

	public Date getbPredate() {
		return bPredate;
	}

	public void setbPredate(Date bPredate) {
		this.bPredate = bPredate;
	}

	public Date getbEnddate() {
		return bEnddate;
	}

	public void setbEnddate(Date bEnddate) {
		this.bEnddate = bEnddate;
	}

	public String getbRemark() {
		return bRemark;
	}

	public void setbRemark(String bRemark) {
		this.bRemark = bRemark;
	}
	
	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public List<AtchFileDetailVO> getAtchFileDetailVOList() {
		return atchFileDetailVOList;
	}

	public void setAtchFileDetailVOList(List<AtchFileDetailVO> atchFileDetailVOList) {
		this.atchFileDetailVOList = atchFileDetailVOList;
	}

	public MultipartFile[] getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile[] uploadFile) {
		this.uploadFile = uploadFile;
	}

	@Override
	public String toString() {
		return "BusinessVO [bSiteno=" + bSiteno + ", bName=" + bName + ", bAddress=" + bAddress + ", bTelno=" + bTelno
				+ ", bAmount=" + bAmount + ", bManqty=" + bManqty + ", bStrdate=" + bStrdate + ", bPredate=" + bPredate
				+ ", bEnddate=" + bEnddate + ", bRemark=" + bRemark + ", rnum=" + rnum + ", uploadFile="
				+ Arrays.toString(uploadFile) + ", atchFileDetailVOList=" + atchFileDetailVOList + "]";
	}

	

	

}
