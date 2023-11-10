<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>직원 목록</title>
</head>
<body>
	<h1>직원 목록</h1>
	<p>
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
				<th>직원명</th>
				<th>연락처</th>
				<th>부서</th>
			</tr>
		</thead>
		<tbody>
		<!-- mav.addObject("data", articlePage);
		data : ArticlePage<EmployeeVO>		
		data.content : List<EmployeeVO> 
		-->
		<c:forEach var="employeeVO" items="${data.content}" varStatus="stat">
			<tr>
				<td>${employeeVO.rnum}</td>
				<td><a href="/employee/detail?eEmpno=${employeeVO.eEmpno}">
					${employeeVO.eName}</a>
<%-- 				<td><a href="/employee/detail2/${employeeVO.eEmpno}"> --%>
<%-- 					${employeeVO.eName}</a> --%>
				</td>
				<td>${employeeVO.eTelno}</td>
				<td>${employeeVO.eDept}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<p>
		<a href="/employee/create">직원 등록</a>
	</p>
	${data.getPagingArea()}
<!-- 	<p> -->
<!-- 		data : ArticlePage<EmployeeVO>	  -->
<!-- 		<p> -->
<%-- 			<c:if test="${data.startPage < 6}"> --%>
<!-- 				이전 -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${data.startPage >= 6}"> --%>
<%-- 			<a href="/employee/list?keyword=${param.keyword}&currentPage=${data.startPage-5}"> --%>
<!-- 			이전 -->
<!-- 			</a> -->
<%-- 			</c:if> --%>
<!-- 		</p> -->
<!-- 		<p> -->
<%-- 			<c:forEach var="pNo" begin="${data.startPage}"  --%>
<%-- 								 end="${data.endPage}"> --%>
<%-- 			<a href="/employee/list?keyword=${param.keyword}&currentPage=${pNo}">${pNo}</a>&nbsp; --%>
<%-- 			</c:forEach> --%>
<!-- 		</p> -->
<!-- 		<p> -->
<%-- 			<c:if test="${data.totalPages <= data.endPage}"> --%>
<!-- 				다음 -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${data.totalPages > data.endPage}"> --%>
<%-- 			<a href="/employee/list?keyword=${param.keyword}&currentPage=${data.startPage+5}"> --%>
<!-- 				다음 -->
<!-- 			</a> -->
<%-- 			</c:if> --%>
<!-- 		</p> -->
<!-- 	</p> -->
</body>
</html>







