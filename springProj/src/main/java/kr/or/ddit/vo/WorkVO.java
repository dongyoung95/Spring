package kr.or.ddit.vo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public class WorkVO {
	private int rnum;
	private String eEmpno;
	private String bSiteno;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date wInpdate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date wEnddate;
	private int wScore;
	private String workId;	
	
	//직원
	private List<EmployeeVO> employeeVOList;
	
	//사업장
	private List<BusinessVO> businessVOList;
	
	//첨부파일
	//<input type="file" class="form-control form-control-user"
	//			id="uploadFile" name="uploadFile" 
	private MultipartFile[] uploadFile;
	
	//관계차수 => 1:1, 1:N, N:M(DB에서 처리할 수 없음)
	//Work테이블 : ATCH_FILE_DETAIL테이블 = 1 : N
	private List<AtchFileDetailVO> atchFileDetailVOList;
	
	public WorkVO() {}

	public String geteEmpno() {
		return eEmpno;
	}

	public void seteEmpno(String eEmpno) {
		this.eEmpno = eEmpno;
	}

	public String getbSiteno() {
		return bSiteno;
	}

	public void setbSiteno(String bSiteno) {
		this.bSiteno = bSiteno;
	}

	public Date getwInpdate() {
		return wInpdate;
	}

	public void setwInpdate(Date wInpdate) {
		this.wInpdate = wInpdate;
	}

	public Date getwEnddate() {
		return wEnddate;
	}

	public void setwEnddate(Date wEnddate) {
		this.wEnddate = wEnddate;
	}

	public int getwScore() {
		return wScore;
	}

	public void setwScore(int wScore) {
		this.wScore = wScore;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public MultipartFile[] getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile[] uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<AtchFileDetailVO> getAtchFileDetailVOList() {
		return atchFileDetailVOList;
	}

	public void setAtchFileDetailVOList(List<AtchFileDetailVO> atchFileDetailVOList) {
		this.atchFileDetailVOList = atchFileDetailVOList;
	}

	public List<EmployeeVO> getEmployeeVOList() {
		return employeeVOList;
	}

	public void setEmployeeVOList(List<EmployeeVO> employeeVOList) {
		this.employeeVOList = employeeVOList;
	}

	public List<BusinessVO> getBusinessVOList() {
		return businessVOList;
	}

	public void setBusinessVOList(List<BusinessVO> businessVOList) {
		this.businessVOList = businessVOList;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	@Override
	public String toString() {
		return "WorkVO [rnum=" + rnum + ", eEmpno=" + eEmpno + ", bSiteno=" + bSiteno + ", wInpdate=" + wInpdate
				+ ", wEnddate=" + wEnddate + ", wScore=" + wScore + ", workId=" + workId + ", employeeVOList="
				+ employeeVOList + ", businessVOList=" + businessVOList + ", uploadFile=" + Arrays.toString(uploadFile)
				+ ", atchFileDetailVOList=" + atchFileDetailVOList + "]";
	}

	
}
