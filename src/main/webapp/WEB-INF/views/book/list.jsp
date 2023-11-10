<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>책 목록</title>
</head>
<body>
<!-- p.98 -->
	<h1>책 목록</h1>
	<p id="demo"></p>
	<p>
		<!-- action속성 및 값이 생략 시, 현재 URI(/list)를 재요청. 
		method는 GET(form 태그의 기본 HTTP 메소드는 GET임) 
		/list?keyword=개똥이
		-->
		<form>
			<input type="text" placeholder="검색어를 입력하세요" name="keyword"
				value="${keyword}" />
			<input type="submit" value="검색" />
		</form>
	</p>
	<table border="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>카테고리</th>
				<th>가격</th>
			</tr>
		</thead>
		<tbody>
		<!-- 
		forEach 태그? 배열(String[], int[][]), Collection(List, Set) 또는 
		Map(HashTable, HashMap, SortedMap)에 저장되어 있는 값들을 
		순차적으로 처리할 때 사용함. 자바의 for, do~while을 대신해서 사용함
		var : 변수
		items : 아이템(배열, Collection, Map)
		varStatus : 루프 정보를 담은 객체 활용
			- index : 루프 실행 시 현재 인덱스(0부터 시작)
			- count : 실행 회수(1부터 시작. 보통 행번호 출력)
		 -->
		 <!-- data : mav.addObject("data", list); -->
		 <!-- row : bookVO 1행 -->
		<c:forEach var="bookVO" items="${data.content}" varStatus="stat">
			<tr>
				<td>${bookVO.rnum}</td>
<%-- 				<td><a href="/detail?bookId=${bookVO.bookId}">${bookVO.title}</a></td> --%>
				<td><a href="/detail2/${bookVO.bookId}">${bookVO.title}</a></td>
				<td>${bookVO.category}</td>
				<td><fmt:formatNumber value="${bookVO.price}" pattern="#,###" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<p>
		<a href="/create">책 생성</a>
		<div id="divCopy"></div>
	</p>
	${data.getPagingArea()}
	
<script>	
	var myObj = { "name":"John", "age":31, "city":"New York" };//json 오브젝트
	//JSON.stringify() 함수는 객체를 텍스트로 변환하는 메서드임
	var myJSON = JSON.stringify(myObj);	//텍스트
	console.log("myJSON : " + myJSON);
	localStorage.setItem("cp",myJSON);	//로컬스토리지에 저장
// 	window.location = "/upload/demo_json.jsp?x=" + myJSON;
	//서버의 JSON 형식의 데이터를 웹 브라우저가 받으면 자바스크립트 객체로 변환할 수 있음
	var myObj = JSON.parse(myJSON);	//myObj(x)
	console.log("name : " + myObj.name);
	console.log("name : " + myObj.age);
	console.log("name : " + myObj.city);
	document.getElementById("demo").innerHTML = myObj.name;
	$("#demo").append("<p>"+myObj.age+"</p>");
	$("#demo").append("<p>"+myObj.city+"</p>");
	let cpObje = localStorage.getItem("cp");	//로컬스토리지에서 꺼냄(myJSON), 텍스트
	let jsObj = JSON.parse(cpObje);	//J/S 오브젝트
	$("#divCopy").html("이름 : " + jsObj.name);
</script>
</body>
</html>














