<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 뷰(View) : 화면을 담당
	Client -> 요청(/create) -> Server(create()메소드와 매핑. View에 create.jsp를 포워딩)
	       <-응답(create_jsp.java, create_jsp.class, HTML) <-
	웹 브라우저(크롬)에서 렌더링 : 브라우저가 읽어서 해석
-->
<!-- 
   요청URI : localhost/create
   요청파라미터(HTTP파라미터, QueryString) : 
   		{title=개똥이의 모험,category=소설,price=10000,uploadFile=파일객체}
   요청방식 : post
   
 post방식은 요청파라미터가 주소창에 보이지 않음. 주소창의 변화 없이 데이터만 서버로 전달할 수 있음
-->
<!-- BookVO bookVO -->
<form:form modelAttribute="bookVO" action="/createForm" method="post" 
	enctype="multipart/form-data">
	<!-- 스프링 폼데이터 
	규칙 : form:input에서 type 및 name속성 및 id는 path로 대신함
	
	<input type="text" name="bookId" id="bookId" />
	-->
	<form:input path="bookId" />
	<font color="red">
		<form:errors path="bookId" />
	</font>
	<p>제목 : <form:input path="title" required="required" /> </p>
	<p>카테고리 : <form:input path="category" required="required" /></p>
	<p>가격 : <input type="number" name="price" maxlength="10"
		required />
	</p>
	<p>출판일 : <input type="date" name="pubDt" required="required" />
	</p>
	<p>
		<p>저자 :</p>
		<p id="pWriters">
			<span>
				<form:input path="writerVOList[0].wrId" value="WR0001" />
				<form:input path="writerVOList[0].wrName" value="개똥이" />
			</span><br />
			<span>
				<form:input path="writerVOList[1].wrId" value="WR0002" />
				<form:input path="writerVOList[1].wrName" value="수현" />
			</span><br />
			<span>
				<form:input path="writerVOList[2].wrId" value="WR0003" />
				<form:input path="writerVOList[2].wrName" value="찬혁" />
			</span><br />
		</p> 
	</p>
	<p id="body_content">
		
	</p>
	<p>
		<input type="file" id="uploadFile" name="uploadFile" multiple />
	</p>
	<p>
		<input type="submit" value="저장" />
	</p>
</form:form>
<script type="text/javascript">
$(function(){	
	console.log("개똥이");
	
	$("#btnAjax").on("click",function(){
		//ajax 파일 업로드 시작 //////////////////////
		//가상 폼(이미지들을 넣음) <form></form>
		let formData = new FormData();
		//파일 요소를 선택하여 J/S 오브젝트로 할당 
		let inputFile = $("input[name='uploadFile']");
		//이미지 파일들(a001.jpg, b001.jpg)
		let files = inputFile[0].files;
		
		console.log("files : ",files);
		
		//가상폼인 formData에 각각의 이미지를 넣자
		/*
		<form>
			<input type="file" name="uploadFile" 파일객체 />
			<input type="file" name="uploadFile" 파일객체 />
			<input type="text" name="title" value="개똥이의 모험" />
			<input type="text" name="category" value="소설" />
			<input type="text" name="price" value="12000" />
		</form>
		*/
		for(let i=0;i<files.length;i++){
			formData.append("uploadFile", files[i]);
		}
		//ajax 파일 업로드 끝 //////////////////////
		
		let title = $("input[name='title']").val();//개똥이의 모험
		let category = $("input[name='category']").val();//소설
		let price = $("input[name='price']").val();//12000
		
		formData.append("title",title);
		formData.append("category",category);
		formData.append("price",price);
		
		/*formData
		<form>
			<input type="file" name="uploadFile" 파일객체 />
			<input type="file" name="uploadFile" 파일객체 />
			<input type="text" name="title" value="개똥이의 모험" />
			<input type="text" name="category" value="소설" />
			<input type="text" name="price" value="12000" />
		</form>
		*/
		//아작났어유..피씨다타써
		/*
		요청URI : /createFormAjax
		요청파라미터 : {title=개똥이의 모험,category=소설,price=12000
					,uploadFile=파일객체들}
		요청방식 : post
		*/
		$.ajax({
			url:"/createFormAjax",
			processData:false,
			contentType:false,
			data:formData,
			type:"post",
			dataType:"json",
			success:function(result){
				//json오브젝트{"result","success"}
				console.log("result : ", result);
				//{result: 'success', bookId: 'BK0008'}
				if(result.result == "success"){
					location.href="/detail?bookId=" + result.bookId;
				}else{
					alert("오류!");
				}
			}
		});
	});
	
	$("#btnSubmit").on("click",function(){
		//JSON오브젝트
		let data = {
			"title":$("input[name='title']").val(),
			"category":$("input[name='category']").val(),
			"price":$("input[name='price']").val()
		};
		
		console.log("data : ",data);
		
		//validation 처리하기
		
		//아작나써유..(피)씨다타써
		//요청URI : /createAjax
		//요청파라미터 : {"title":"개똥이의 모험","category":"소설","price":"10000"}
		//요청방식 : post
		//contentType : 보내는 타입
		//dataType : 응답 타입
		$.ajax({
			url:"/createAjax",
			contentType:"application/json;charset:utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			success:function(rslt){
				//{"bookId":"72","title":"개똥이의 모험","category":"소설"
				//	,"price":"10000","insertDate":"","content":""}
				console.log("rslt : ",rslt);
				
				if(rslt.bookId!=""){
					console.log("등록 성공!");
					location.href="/detail?bookId="+rslt.bookId;
				}else{
					console.log("등록 실패!");
				}
			}
		});
	});
});
</script>













