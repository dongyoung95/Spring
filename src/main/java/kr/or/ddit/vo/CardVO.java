package kr.or.ddit.vo;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class CardVO {
	@NotBlank
	private String eEmpno;
	@NotBlank
	private String cardNum;
	// 연월(6자리) 202503
	@NotBlank
	@Pattern(regexp = "^[0-9]{6}$", message = "연도 4자리와 월 2자리를 입력해주세요")
	private String cardYm;
}
