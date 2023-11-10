<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	console.log("개똥이");
	//EL표기 -> J/S 오브젝트/변수
	//2023/11/01/2bedee69-53e2-4504-a8de-20bc8fb52d04_d001.jpg
	let streFileNm = "${data.atchFileDetailVOList[0].streFileNm}";
	
	//클래스 값이 form-control-user인 요소를 선택하여 disabled 처리해보자
	$(".form-control-user").attr("disabled","disabled");
	
	//수정버튼 클릭->수정모드 전환
	$("#edit").on("click",function(){
		//id속성의 값이 spn1인 요소를 보이지 않도록 하고
		$("#spn1").css("display","none");		
		//id속성의 값이 spn2인 요소를 보이도록 하자
		$("#spn2").css("display","block");
		//클래스 값이 form-control-user인 요소를 선택하여 disabled속성을 제거하자
		$(".form-control-user").removeAttr("disabled");
		//id속성의 값이 frm인 요소(form태그)의 action속성의 값을 /business/updatePost로 바꾸자
		$("#frm").attr("action","/business/updatePost");
	});
	
	//삭제버튼 클릭
	$("#delete").on("click",function(){
		//id속성의 값이 frm인 요소(form태그)의 action속성의 값을 /business/deletePost로 바꾸자
		$("#frm").attr("action","/business/deletePost");
		//삭제하시겠습니까? confirm 처리
		//true(1) / false(0)
		let result = confirm("삭제하시겠습니까?");
		//확인 선택 시 form을 submit
		if(result>0){
			$("#frm").submit();
		}else{
			//취소 선택 시 "삭제가 취소되었습니다" 경고창
			alert("삭제가 취소되었습니다");
		}
	});
	//streFileNm : 2023/11/01/2bedee69-53e2-4504-a8de-20bc8fb52d04_d001.jpg
	$(".bg-register-image").css({"background-image":"url(/resources/upload/"+streFileNm+")","background-position":"center","background-size":"cover"});
});
</script>
<div class="card o-hidden border-0 shadow-lg my-5">
	<div class="card-body p-0">
		<!-- Nested Row within Card Body -->
		<div class="row">
			<p>${data}</p>
			<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
			<div class="col-lg-7">
				<div class="p-5">
					<div class="text-center">
						<h1 class="h4 text-gray-900 mb-4">사업장 상세</h1>
					</div>
					<!-- 
					요청URI : /business/updatePost
					요청파라미터 : {bSiteno=BS0012,bName=대전사업장,bAddress=대전
							   , bTelno=042-111-2222,bAmount=12200000}
					요청방식 : post
					
					data : BusinessVO
					-->
					<form id="frm" name="frm" class="user" action="/business/updatePost" 
						method="post">
						<input type="text" name="bSiteno" value="${data.bSiteno}" />
						<!-- 폼데이터 -->
						<div class="form-group row">
							<div class="col-sm-6 mb-3 mb-sm-0">
								<input type="text" class="form-control form-control-user"
									id="bName" name="bName" value="${data.bName}"
									placeholder="사업장명" required />
							</div>
							<div class="col-sm-6">
							</div>
						</div>
						<div class="form-group">
							<input type="text" class="form-control form-control-user"
								id="bAddress" name="bAddress" value="${data.bAddress}"
								placeholder="사업장 주소" required />
						</div>
						<div class="form-group row">
							<div class="col-sm-6 mb-3 mb-sm-0">
								<input type="text" class="form-control form-control-user"
									id="bTelno" name="bTelno" value="${data.bTelno}" 
									placeholder="사업장 연락처" required />
							</div>
							<div class="col-sm-6">
								<input type="number" class="form-control form-control-user"
									id="bAmount" name="bAmount" value="${data.bAmount}"
									placeholder="사업장 보유금액" required />
							</div>
						</div>
						<!-- 일반모드 시작 -->
                        <span id="spn1">
                           <p>
                              <button type="button" id="edit"  
                                 class="btn btn-primary btn-user btn-block" 
                                 style="width:50%;float:left;">수정</button>
                              <button type="button" id="delete"  
                                   class="btn btn-primary btn-user btn-block" 
                                   style="width:50%;">삭제</button>
                           </p>
                           <p>
                              <a href="/business/list" class="btn btn-success btn-user btn-block">
                                   	 목록
                              </a>
                           </p>
                        </span>
                        <!-- 일반모드 끝 -->
                        <!-- 수정모드 시작 -->
                        <span id="spn2" style="display:none;">
                           <button type="submit" class="btn btn-primary btn-user btn-block">
                                  	확인
                           </button>
                           <a href="/article/detail?articleNo=${param.articleNo}" class="btn btn-success btn-user btn-block">
                                  	취소
                           </a>
                        </span>
                        <!-- 수정모드 끝 -->
						<hr />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>