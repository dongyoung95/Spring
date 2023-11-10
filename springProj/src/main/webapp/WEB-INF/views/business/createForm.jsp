<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="card o-hidden border-0 shadow-lg my-5">
	<div class="card-body p-0">
		<!-- Nested Row within Card Body -->
		<div class="row">
			<!-- .bg-register-image{background:url(https://source.unsplash.com/Mv9hjnEUHR4/600x800);background-position:center;background-size:cover} -->
			<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
			<div class="col-lg-7">
				<div class="p-5">
					<div class="text-center">
						<h1 class="h4 text-gray-900 mb-4">사업장 등록</h1>
					</div>
					<!-- 
					요청URI : /business/create
					요청파라미터 : {bName=대전사업장,bAddress=대전,bTelno=042-111-2222,bAmount=12200000}
					요청방식 : post
					
					골뱅이ModelAttribute BusinessVO businessVO
					-->
					<form:form modelAttribute="businessVO" id="frm" name="frm" class="user" action="/business/createForm" 
						method="post" enctype="multipart/form-data">
<%-- 						<form:hidden path="bSiteno" />					 --%>

						<div class="form-group row">
							<div class="col-sm-6 mb-3 mb-sm-0">
								<form:input class="form-control form-control-user" type="text" path="bSiteno" />
								
								<font color="red">
									<form:errors path="bSiteno" />
								</font>
							</div>
						</div>
						<!-- 폼데이터 -->
						<div class="form-group row">
							<div class="col-sm-6 mb-3 mb-sm-0">
								<form:input class="form-control form-control-user"
									path="bName" 
									placeholder="사업장명" />
									
								<font color="red">
									<form:errors path="bName" />
								</font>
							</div>
						</div>
						<div class="form-group">
							<form:input class="form-control form-control-user"
								path="bAddress" 
								placeholder="사업장 주소" required="required" />
						</div>
						<div class="form-group row">
							<div class="col-sm-6 mb-3 mb-sm-0">
								<form:input class="form-control form-control-user"
									path="bTelno" 
									placeholder="사업장 연락처" required="required" />
							</div>
							<div class="col-sm-6">
								<input type="number" class="form-control form-control-user"
									id="bAmount" name="bAmount" value="0"
									placeholder="사업장 보유금액" required />
							</div>
						</div>
						<div class="form-group">
							<!-- multiple : 다중 파일업로드 -->
							<input type="file" class="form-control form-control-user"
								id="uploadFile" name="uploadFile" 
								placeholder="파일을 등록해주세요" multiple />
						</div>
						<form:button name="register" 
							 class="btn btn-primary btn-user btn-block">
							등록</form:button>
						<hr />
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	console.log("개똥이");
	
	$("#btnAjax").on("click",function(){
		let formData = new FormData();
		
		let inputFile = $("#uploadFile");//파일 오브젝트
		let files = inputFile[0].files;//파일들
		
		for(let i=0;i<files.length;i++){
			formData.append("uploadFile",files[i]);
		}
		
		let bName = $("#bName").val();
		let bAddress = $("#bAddress").val();
		let bTelno = $("#bTelno").val();
		let bAmount = $("#bAmount").val();
		
		formData.append("bName",bName);
		formData.append("bAddress",bAddress);
		formData.append("bTelno",bTelno);
		formData.append("bAmount",bAmount);
		
		$.ajax({
			url:"/business/createFormAjax",
			processData:false,
			contentType:false,
			data:formData,
			type:"post",
			dataType:"json",
			success:function(result){
				console.log("result : ", result);
				//{"result":"success","bSiteno":"BS0013"}
				if(result.result == "success"){
					setTimeout(()=>location.href="/business/detail?bSiteno="
								+result.bSiteno);
				}else{
					alert("오류 발생!");
				}
			}
		});
	});
	
	//이미지 미리보기 시작 ///////////////////////
	$("#uploadFile").on("change",handleImg);
	//e : onchange 이벤트 객체
	function handleImg(e){
// 		console.log("이미지 미리보기");
		//e.target : <input type="file" id="uploadFile"..
		let files = e.target.files;
		//이미지 오브젝트 배열
		let fileArr = Array.prototype.slice.call(files);
		
		//f : 각각의 이미지 파일
		fileArr.forEach(function(f){
			//이미지가 아니면
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능합니다.");
				//함수 종료
				return;
			}
			//이미지를 읽어보자
			let reader = new FileReader();
			//e : 파일을 읽어주는 리더객체가 이미지를 읽을 때 그 이벤트
			reader.onload = function(e){
				//background-position:center;background-size:cover
				$(".bg-register-image").css({"background-image":"url("+e.target.result+")","background-position":"center","background-size":"cover"});
// 				console.log(e.target.result);
			}
			//다음 이미지 파일(f)을 위해 리더를 초기화
			reader.readAsDataURL(f);
		});
	}
	//이미지 미리보기 끝 ///////////////////////
	
	$("#btnSubmit").on("click",function(){
		
		//validation 처리 필요
		if($("#bName").val()==""){
			alert("사업장명을 입력해주세요");
			return;
		}
		
		let data = {
			"bName":$("#bName").val(),
			"bAddress":$("#bAddress").val(),
			"bTelno":$("#bTelno").val(),
			"bAmount":$("#bAmount").val()
		};
		console.log("data : " , data);
		/*
		요청URI : /business/createAjax
		요청파라미터 : {"bName":"대전사업장","bAddress":"대전","bTelno":"042-111-2222"
					,"bAmount":"12200000"}
		요청방식 : post
		*/
		$.ajax({
			url:"/business/createAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			success:function(rslt){
				//{bSiteno: 'BS0012', bName: '개똥이 사업장', bAddress: '대전'
				//	, bTelno: '010', bAmount: 11, …}
				console.log("rslt : ", rslt);
				if(rslt.bSiteno!=""){
					console.log("등록 성공!");
					location.href="/business/detail?bSiteno="+rslt.bSiteno;
				}else{
					console.log("등록 실패!");
				}
			}
		});
	});
});
</script>

