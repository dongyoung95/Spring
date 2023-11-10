<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
//data : BookVO bookVO
// mav.addObject("data", bookVO);
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<title>책 상세</title>
</head>
<body>
<h1>책 상세</h1>
<!-- 
JSTL(JSP Standard Tag Library) : 개발자들이 자주 사용하는 패턴을 모아놓은 집합
=> BookController에서 보내준 데이터(ModelAndView)를 뷰(jsp)에 표현하도록 도와줌

method
1) GET : 데이터를 변경하지 않을 때. 목록/상세보기
2) POST : 데이터를 변경할 때. 입력/수정/삭제

업데이트 쎄대여
UPDATE BOOK
SET    TITLE='개똥이의 모험', CATEGORY='소설', PRICE=12000, CONTENT='재미있다냥'
WHERE  BOOK_ID = 1

등푸른생선 주세여
DELETE FROM BOOK
WHERE  BOOK_ID = 1

WHERE
1) 단일행 : =, <, >, <=, >=, !=, <>
2) 다중행 : IN(교집합), ANY(OR), ALL(AND), EXISTS(교집합)
 -->
<form id="frm" name="frm" action="/updatePost" method="post">
	<!-- 폼데이터 -->
	<!-- data : BookVO bookVO 
	BookVO [bookId=15, title=제목15
	, category=카테고리15, price=15000, insertDate=Tue Oct 24 09:50:55 KST 2023]
	-->
	<input type="hidden"          name="bookId" value="${data.bookId}"  />
	<p>제목 : <input type="text"    name="title" class="formdata" value="${data.title}" readonly /></p>
	<p>카테고리 : <input type="text" name="category" class="formdata" value="${data.category}" readonly /></p>
	<p>가격 : <input type="text"    name="price" class="formdata" maxLength="10" 
				value='<fmt:formatNumber type="number" value="${data.price}" pattern="#,###" />' 
				readonly /></p>
	<p>설명 : <textarea rows="5" cols="30" name="content" class="formdata" readonly>${data.content}</textarea>
	<p>
		<!-- data.atchFileDetailVOList : List<AtchFileDetailVO> -->
		<c:forEach var="atchFileDetailVO" items="${data.atchFileDetailVOList}">
			<img src="/resources/upload/${atchFileDetailVO.streFileNm}" /><br />
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
	<p>Access a JavaScript object:</p>
	<p id="demo01"></p>
    <p id="demo02"></p>
</form>
<script type="text/javascript">
//document 내의 모든 요소들이 로딩이 완료된 후에 실행
$(function(){
	let myObj, x, y;
	myObj = {"name":"John","age":30,"city":"New York",
			"cars": [
		        { "name":"Ford", "models":[ "Fiesta", "Focus", "Mustang" ] },
        		{ "name":"BMW", "models":[ "320", "X3", "X5" ] },
		        { "name":"Fiat", "models":[ "500", "Panda" ] }
	    	],
			"homes":["대전","서울","부산"]
		};	//JSON오브젝트
	console.log("myObj : ",myObj);
	console.log("name : " + myObj.name);
	console.log("name : " + myObj.city);
	myObj.name = "홍길동";	//{"name":"홍길동","age":30,"city":"New York"};	//JSON오브젝트
	console.log("name : " + myObj.name);//name : 홍길동
	$("#demo01").html(myObj.name);//홍길동
	document.getElementById("demo02").innerHTML = myObj.name;//홍길동
	//for - in 루프를 사용하여 객체 속성을 반복하여 접근할 수 있음
	// x : 인덱스
	for(x in myObj){
// 		console.log("x : " + x);
		console.log("x : " + myObj[x]);
	}
	
	console.log("=======================");
	/*
	myObj = {"name":"John","age":30,"city":"New York",
			"cars":{
				"car1":"Ford","car2":"BMW","car3":"Fiat"
			},
			"homes":["대전","서울","부산"]
		};	//JSON오브젝트
	*/
	myObj.cars.car2 = "택시";
	//delete 키워드를 사용하여 JSON 객체에서 속성을 삭제할 수 있음
	delete myObj.cars.car1;
	
	for(y in myObj.cars){
		console.log("y : " + myObj.cars[y]);
	}
	console.log("======== 배열 값에 접근하기(인덱스 번호 사용)==========")
	
	for(i = 0;i<myObj.homes.length;i++){
		console.log("home[" + i + "] : " + myObj.homes[i]);
	}
	
// 	for(i in myObj.homes){
// 		console.log("home[" + i + "] : " + myObj.homes[i]);
// 	}
	
	console.log("============== 중첩된 배열 =================");
	/*
	"cars": [
       [0] { "name":"Ford", "models":[ "Fiesta", "Focus", "Mustang" ] },
       [1]	{ "name":"BMW", "models":[ "320", "X3", "X5" ] },
       [2] { "name":"Fiat", "models":[ "500", "Panda" ] }
   	]
	*/
	for(i in myObj.cars){
		console.log("name : " + myObj.cars[i].name);
		for(j in myObj.cars[i].models){
			console.log("models : " + myObj.cars[i].models[j]);
		}
	}
	//JSON -> JSON.stringify 텍스트 -> 서버(톰켓) -> 크롬 텍스트 -> JSON.parse 오브젝트
	console.log("개똥이");
	//수정 버튼 클릭 -> 수정모드로 전환
	$("#edit").on("click",function(){
		$("#p1").css("display","none");//css : style
		$("#p2").css("display","block");
		$(".formdata").removeAttr("readonly");//readonly속성을 제거
		
		//가격요소를 선택 후 쉼표 제거 후 type을 number로 바꾸자
		let objPrice = $("input[name='price']");
		let price = objPrice.val();//15,000
		price = price.replaceAll(",","");//15,000에서 쉼표를 ""로 모두 바꾸자=>15000
		objPrice.val(price);
		objPrice.attr("type","number");
		//textarea태그 중에서 name속성의 값이 content인 요소를 CKEditor로 바꾸자
		//CKEDITOR.replace("content");
		//<form id="frm" name="frm" action="/updatePost" method="post">
		$("form").attr("action","/updatePost");
	});
	//취소 버튼 클릭
	$("#cancel").on("click",function(){
		//주소표시줄 : /detail?bookId=15
		//param : bookId=15
		//param.bookId : 15
		location.href="/detail?bookId=${param.bookId}";
	});
	
	//삭제 버튼 클릭
	//DELETE FROM BOOK
	//WHERE  BOOK_ID = 1
	$("#delete").on("click",function(){
		$("form").attr("action","/deletePost");
						
		let result = confirm("삭제하시겠습니까?");
		
		//확인 : true(1) / 취소 : false(0)
		console.log("result : " + result);
		
		//가격요소를 선택 후 쉼표 제거 후 type을 number로 바꾸자
		let objPrice = $("input[name='price']");
		let price = objPrice.val();//150002
		price = price.replaceAll(",","");//150002
		objPrice.val(price);
		objPrice.attr("type","number");
		
		if(result > 0){//확인
			$("form").submit();
		}else{//취소
			alert("삭제가 취소되었습니다.");
		}
	});
	
	//목록 버튼 클릭
	//<input type="button" id="list" value="목록" />
	$("#list").on("click",function(){
		location.href="/list";
	});
	
	
});
</script>
</body>
</html>










