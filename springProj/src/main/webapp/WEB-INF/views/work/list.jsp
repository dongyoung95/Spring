<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
				<div class="row">
					<div class="col-sm-12 col-md-6">
						<div class="dataTables_length" id="dataTable_length">
							<label>Show <select name="dataTable_length"
								aria-controls="dataTable"
								class="custom-select custom-select-sm form-control form-control-sm"><option
										value="10">10</option>
									<option value="25">25</option>
									<option value="50">50</option>
									<option value="100">100</option></select> entries
							</label>
						</div>
					</div>
					<div class="col-sm-12 col-md-6">
						<div id="dataTable_filter" class="dataTables_filter">
							<label>Search:<input type="search"
								class="form-control form-control-sm" placeholder=""
								aria-controls="dataTable"></label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<table class="table table-bordered dataTable" id="dataTable"
							width="100%" cellspacing="0" role="grid"
							aria-describedby="dataTable_info" style="width: 100%;">
							<thead>
								<tr role="row">
									<th class="sorting sorting_asc" tabindex="0"
										aria-controls="dataTable" rowspan="1" colspan="1"
										aria-sort="ascending"
										aria-label="rnum: activate to sort column descending"
										style="width: 60px;">번호</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="eEmpno: activate to sort column ascending"
										style="width: 68px;">사원</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="bSiteno: activate to sort column ascending"
										style="width: 51px;">사업장</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="wInpdate: activate to sort column ascending"
										style="width: 68px;">근무시작일</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="wEnddate: activate to sort column ascending"
										style="width: 51px;">근무종료일</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="wScore: activate to sort column ascending"
										style="width: 31px;">근무평점</th>
								</tr>
							</thead>
							<tbody>
								<!-- data : ArticlePage<WorkVO> 
									 data.content :	List<WorkVO> data -->
								<c:forEach var="workVO" items="${data.content}">
									<tr class="odd">
										<td class="sorting_1">${workVO.rnum}</td>
										<td><a href="/work/detail?workId=${workVO.workId}">${workVO.employeeVOList[0].eName}</a></td>
<%-- 										<td><a href="/business/detail2/${businessVO.bSiteno}">${businessVO.bName}</a></td> --%>
										<td>${workVO.businessVOList[0].bName}</td>
										<td><fmt:formatDate value="${workVO.wInpdate}" pattern="yyyy-MM-dd" /> </td>
										<td><fmt:formatDate value="${workVO.wEnddate}" pattern="yyyy-MM-dd" /></td>
										<td>${workVO.wScore}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 col-md-5">
						<div class="dataTables_info" id="dataTable_info" role="status"
							aria-live="polite">Showing 1 to 10 of 57 entries</div>
					</div>
					<!-- mav.addObject("data", articlePage)
					data.startPage : 블록의 시작 페이지 번호
					data.endPage   : 블록의 종료 페이지 번호
					 -->
<%-- 					<p>${data.startPage},${data.endPage}</p> --%>
					<!-- EL태그 정리 
						== : eq(equal)
						!= : ne(not equal)
						<  : lt(less than)
						>  : gt(greater than)
						<= : le(less equal)
						>= : ge(greater equal)
					 -->
					${data.getPagingArea()}
				</div>
			</div>
		</div>
	</div>
</div>