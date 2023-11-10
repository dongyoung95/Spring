<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 
요청URI : /employee/create
요청파라미터 : {eName=개똥이,eAddress=대전,eTelno=010-123-1234,
			ePosition=과장,eDept=개발부}
요청방식 : post

model : EmployeeVO employeeVO

//ModelAttribute EmployeeVO employeeVO
-->
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<form:form modelAttribute="employeeVO" name="frm" action="/employee/createForm" method="post" 
	enctype="multipart/form-data">
	<p><form:input path="eEmpno" placeholder="사원번호를 입력해주세요"
		 required="required" />
		 <font color="red">
			 <form:errors path="eEmpno" />
		 </font>
	</p>
	<p><form:input path="eName" placeholder="사원명을 입력해주세요"
		 required="required" />
		 <font color="red">
			 <form:errors path="eName" />
		 </font>
	</p>
	<p>
		<input type="text" name="postNum" id="postNum" readonly="readonly" />
		<button type="button" id="btnPost">우편번호검색</button>
		 <font color="red">
			 <form:errors path="postNum" />
		 </font>
		 <br />
		<form:input path="eAddress" placeholder="주소를 입력해주세요"
		required="required" readonly="readonly" />
		 <font color="red">
			 <form:errors path="eAddress" />
		 </font>
		<input type="text" name="eAddress2" id="eAddress2" placeholder="상세 주소 입력" />
	</p>
	<p><input type="text" name="eTelno" placeholder="연락처를 등록해주세요"
		required="required" /></p>
	<p>
		<label for="ePosition1">부장</label>
		<form:radiobutton path="ePosition" value="부장" />
		<label for="ePosition2">차장</label>
		<form:radiobutton path="ePosition" value="차장" />
		<label for="ePosition3">과장</label>
		<form:radiobutton path="ePosition" value="과장" />
		<label for="ePosition4">대리</label>
		<form:radiobutton path="ePosition" value="대리" />
		<label for="ePosition5">사원</label>
		<form:radiobutton path="ePosition" value="사원" />
	</p>
	<p>
		<form:select path="eDept" items="${eDeptMap}" multiple="false" />
<!-- 			<option value="">부서 선택</option> -->
<!-- 			<option value="개발부">개발부</option> -->
<!-- 			<option value="영업부">영업부</option> -->
<!-- 			<option value="총무부">총무부</option> -->
<%-- 		</form:select> --%>
	</p>
	<p>	<!-- path : name="hobbyArray" id="hobbyArray" -->
		<form:checkbox path="hobbyArray" value="sports" label="sports" />
		<form:checkbox path="hobbyArray" value="Music" label="Music" />
		<form:checkbox path="hobbyArray" value="Movie" label="Movie" />
		<form:checkbox path="hobbyArray" value="game" label="game" />
		<form:checkbox path="hobbyArray" value="drive" label="drive" />
	</p>
	<p>
		<span>
			<form:input path="cardVOList[0].eEmpno" value="E024" />
			<font color="red"><form:errors path="cardVOList[0].eEmpno" /></font>
			<form:input path="cardVOList[0].cardNum" value="11111-22-333333" />
			<font color="red"><form:errors path="cardVOList[0].cardNum" /></font>
			<form:input path="cardVOList[0].cardYm" value="202501" />
			<font color="red"><form:errors path="cardVOList[0].cardYm" /></font>
		</span><br />
		<span>
			<form:input path="cardVOList[1].eEmpno" value="E024" />
			<font color="red"><form:errors path="cardVOList[1].eEmpno" /></font>
			<form:input path="cardVOList[1].cardNum" value="11111-33-666666" />
			<font color="red"><form:errors path="cardVOList[1].cardNum" /></font>
			<form:input path="cardVOList[1].cardYm" value="202503" />
			<font color="red"><form:errors path="cardVOList[1].cardYm" /></font>
		</span><br />
		<span>
			<form:input path="cardVOList[2].eEmpno" value="E024" />
			<font color="red"><form:errors path="cardVOList[2].eEmpno" /></font>
			<form:input path="cardVOList[2].cardNum" value="11111-55-777777" />
			<font color="red"><form:errors path="cardVOList[2].cardNum" /></font>
			<form:input path="cardVOList[2].cardYm" value="202507" />
			<font color="red"><form:errors path="cardVOList[2].cardYm" /></font>
		</span><br />
	</p>
	<p id="body-content" style="width:100%;">
		
	</p>
	<p>
		<input type="file" id="uploadFile" name="uploadFile" multiple />
	</p>
	<p>
		<button type="submit">등록</button>
		<button type="reset">초기화</button>
	</p>
</form:form>
<script type="text/javascript">
$(function(){
	console.log("개똥이");
	
	//다음 우편번호 검색
	$("#btnPost").on("click",function(){
		console.log("우편번호 검색!");
		new daum.Postcode({
			//다음 창에서 검색이 완료되면 콜백함수에 의해 결과 데이터가 data 객체로 들어옴
			oncomplete:function(data){
				$("#postNum").val(data.zonecode);
				$("#eAddress").val(data.address);
				$("#eAddress2").val(data.buildingName);
			}
		}).open();
	});	
	
	$("#btnAjax").on("click",function(){
		//ajax 파일업로드 
		//<form></form>
		let formData = new FormData();
		let inputFile = $("#uploadFile");	//<input type="file"...
		//이미지 파일들 꺼내자
		let files = inputFile[0].files;
		console.log("files : ",files);
		
		for(let i=0;i<files.length;i++){
			//BookVO의 프로퍼티에 private MultipartFile[] uploadFile;
			formData.append("uploadFile",files[i]);
		}
		
		let eName = $("input[name='eName']").val();
		let eAddress = $("input[name='eAddress']").val();
		let eTelno = $("input[name='eTelno']").val();
		let ePosition = $("input[name='ePosition']:checked").val();
		//select 박스의 값 가져오기
		//let eDept = $("select[name='eDept']").val();
		let eDept = $("#eDept option:selected").val();
		
		formData.append("eName",eName);
		formData.append("eAddress",eAddress);
		formData.append("eTelno",eTelno);
		formData.append("ePosition",ePosition);
		formData.append("eDept",eDept);
		
		//아작났어유..피씨다타써
		$.ajax({
			url:"/employee/createFormAjax",
			processData:false,
			contentType:false,
			data:formData,
			type:"post",
			dataType:"json",
			success:function(result){
				//result : {"result":"success"}
				//console.log("result : ",result);	//오브젝트로 확인
				//result :{"result":"success","eEmpno":"E023"}
				console.log("result : " + JSON.stringify(result)); //텍스트로 확인
				//json오브젝트.이름
				if(result.result == "success"){
					/* setTimeOut()
					- 어떤 코드를 바로 실행하지 않고 일정 시간 기다린 후 실행
					- 첫 번재 인자로 실행할 코드를 담고 있는 함수를 받고
					- 두 번째 인자로 지연 시간을 밀리초(ms) 단위로 받음
					- 세 번째 인자부터는 가변 인자를 받음. 첫번째 인자로 넘어온 함수가 인자를 받는 경우, 
						이 함수에 넘길 인자를 명시해주기 위해서 사용함
					*/
					//setTimeout(location.href="/employee/detail?eEmpno="+result.eEmpno,3000);
					//setTimeout(()=>location.href="/employee/detail?eEmpno="+result.eEmpno,3000);
					setTimeout(goDetail,3000,result.eEmpno);
				}
			}
		});
	});
	
	function goDetail(eEmpno){
		location.href="/employee/detail?eEmpno="+eEmpno;
	}
	
	//이미지 미리보기 시작/////////////////////
	$("#uploadFile").on("change",handleImg);
	//e : onchange 이벤트 객체
	function handleImg(e){
		//files : ala.jpg, bara1.jpg, bars2.jpg
		let files = e.target.files;
		let fileArr = Array.prototype.slice.call(files);
		//fileArr = [ala.jpg, bara1.jpg, bars2.jpg]
		fileArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능합니다.");
				return;
			}
			let reader = new FileReader();
			//e : reader가 이미지 객체를 읽는 이벤트
			reader.onload = function(e){
				let img_html = "<img src='"+e.target.result + "' style='width:100%;' />";
				//요소.append : 누적, 요소.html : 새로고침, 요소.innerHTML : J/S에서 새로고침
				$("#body-content").append(img_html);
			}
			reader.readAsDataURL(f);
		});
	}
	//이미지 미리보기 끝/////////////////////
	
	$("#btnSubmit").on("click",function(){
		//ch09_38.jsp참고!
		//radio 버튼의 값 가져오기
		let ePosition = $("input[name='ePosition']:checked").val();
		//select 박스의 값 가져오기
		//let eDept = $("select[name='eDept']").val();
		let eDept = $("#eDept option:selected").val();
		
		//JSON오브젝트
		let data = {
			"eName":$("input[name='eName']").val(),
			"eAddress":$("input[name='eAddress']").val(),
			"eTelno":$("input[name='eTelno']").val(),
			"ePosition":ePosition,
			"eDept":eDept
		};
		
		console.log("data",data);
		
		/*
		요청URI : /employee/createAjax
		요청파라미터 : {eName=개똥이,eAddress=대전,eTelno=010-123-1234,
					ePosition=과장,eDept=개발부}
		요청방식 : post
		*/
		//아작나써유..(피)씨다타써
		//contentType : 보내는 타입 / dataType : 응답 타입
		$.ajax({
			url:"/employee/createAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			success:function(rslt){
				//{eEmpno: 'E011', eName: '개똥이2', eAddress: '대전2'
				//, eTelno: '010-123-1232', ePosition: '차장', …}
				console.log("rslt",rslt);
				
				//selectKey에 의해 생성된 값 E011
				if(rslt.eEmpno!=""){
					console.log("등록 성공!");
					location.href="/employee/detail?eEmpno="+rslt.eEmpno;
				}else{
					console.log("등록 실패!");
				}
			}
		});
	});
});
</script>





