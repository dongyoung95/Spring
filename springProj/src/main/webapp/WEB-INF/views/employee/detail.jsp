<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<title>사원 등록</title>
</head>
<body>
<h2>사원등록</h2>
<!-- EmployeeVO(eEmpno=E011, eName=개똥이, eAddress=대전, eTelno=010-123-1234
	, ePosition=사원, eDept=개발부) -->
<%-- <p>${data}</p> --%>
<!-- 
요청URI : /employee/create
요청파라미터 : {eName=개똥이,eAddress=대전,eTelno=010-123-1234,
			ePosition=과장,eDept=개발부}
요청방식 : post
-->
<form name="frm" action="/employee/create" method="post">
	<input type="hidden" name="eEmpno" value="${data.eEmpno}" />
	<p><input class="clsInput" type="text" name="eName" placeholder="사원명을 입력해주세요"
		 value="${data.eName}" required /></p>
	<p><input class="clsInput" type="text" name="eAddress" placeholder="주소를 입력해주세요"
		value="${data.eAddress}" required /></p>
	<p><input class="clsInput" type="text" name="eTelno" placeholder="연락처를 등록해주세요"
		value="${data.eTelno}" required /></p>
	<!-- 
	<select 태그에서 선택은 selected
	<radio 태그, <checkbox 태그에서 선택은 checked 
	-->
	<p>
		<label for="ePosition1">부장</label>
		<input class="clsInput" type="radio" id="ePosition1" name="ePosition" value="부장"
			<c:if test="${data.ePosition=='부장'}">checked</c:if>
		 />
		<label for="ePosition2">차장</label>
		<input class="clsInput" type="radio" id="ePosition2" name="ePosition" value="차장" 
			<c:if test="${data.ePosition=='차장'}">checked</c:if>
		/>
		<label for="ePosition3">과장</label>
		<input class="clsInput" type="radio" id="ePosition3" name="ePosition" value="과장" 
			<c:if test="${data.ePosition=='과장'}">checked</c:if>
		/>
		<label for="ePosition4">대리</label>
		<input class="clsInput" type="radio" id="ePosition4" name="ePosition" value="대리"
			<c:if test="${data.ePosition=='대리'}">checked</c:if>
		 />
		<label for="ePosition5">사원</label>
		<input class="clsInput" type="radio" id="ePosition5" name="ePosition" value="사원"
			<c:if test="${data.ePosition=='사원'}">checked</c:if>
		/>
	</p>
	<p>
		<select class="clsInput" name="eDept">
			<option value="">부서 선택</option>
			<option value="개발부"
				<c:if test="${data.eDept=='개발부'}">selected</c:if>
			>개발부</option>
			<option value="영업부"
				<c:if test="${data.eDept=='영업부'}">selected</c:if>
			>영업부</option>
			<option value="총무부"
				<c:if test="${data.eDept=='총무부'}">selected</c:if>
			>총무부</option>
		</select>
	</p>
	<p>
		<!-- mav.addObject("data", employeeVO); 
		data.atchFileDetailVOList : List<AtchFileDetailVO>
		-->
		<c:forEach var="atchFileDetailVO" items="${data.atchFileDetailVOList}">
			<img src="/resources/upload/${atchFileDetailVO.streFileNm}" />
		</c:forEach>
	</p>
	<!-- 일반모드 시작 -->
	<p id="p1">
		<input type="button" id="edit" value="수정" />
		<input type="button" id="delete" value="삭제" />
		<input type="button" id="list" value="목록" />
	</p>
	<!-- 일반모드 끝 -->
	<!-- 수정모드 시작 -->
	<p id="p2" style="display:none;">
		<input type="submit" id="confirm" value="확인" />
		<input type="button" id="cancel" value="취소" />
	</p>
	<!-- 수정모드 끝 -->
</form>
<script type="text/javascript">
$(function(){
	console.log("개똥이");
	//입력요소들을 읽기전용처리
	//readonly : submit 시 파라미터로 전송됨
	//disabled : submit 시 파라미터로 전송되지 않음
	$(".clsInput").attr("disabled","disabled");
	
	//수정 버튼 클릭 시 p1은 안보이게, p2는 보이게 처리해보자
	$("#edit").on("click",function(){
		$("#p1").css("display","none");
		$("#p2").css("display","block");
		//disabled된 요소들을 활성화 해보자
		$(".clsInput").removeAttr("disabled");
		//name 속성의 값이 frm인 폼태그를 선택 후 action속성의 값을 
		//  /employee/updatePost로 변경
		$("form[name='frm']").attr("action","/employee/updatePost");
	});
	//취소 버튼 클릭 시 /employee/detail.jsp?eEmpno=E011 를 요청
	$("#cancel").on("click",function(){
		location.href="/employee/detail?eEmpno=${param.eEmpno}";
	});
	//삭제버튼 클릭
	$("#delete").on("click",function(){
		//name 속성의 값이 frm인 폼태그를 선택 후 action속성의 값을 
		//  /employee/deletePost로 변경
		$("form[name='frm']").attr("action","/employee/deletePost");
		//"삭제하시겠습니까?"컨펌창에서 확인 선택 시 폼을 submit, 취소 선택 시 "삭제가 취소됨"
		let result = confirm("삭제하시겠습니까?");
		
		if(result>0){
			$("form").submit();	
		}else{
			alert("삭제가 취소됨");
		}
	});
});
</script>
</body>
</html>




