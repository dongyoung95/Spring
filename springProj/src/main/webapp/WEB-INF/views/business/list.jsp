<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
										aria-label="bName: activate to sort column ascending"
										style="width: 68px;">사업장 명</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="bAddress: activate to sort column ascending"
										style="width: 51px;">사업장 주소</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="bTelno: activate to sort column ascending"
										style="width: 31px;">연락처</th>
								</tr>
							</thead>
							<tbody>
								<!-- data : ArticlePage<BusinessVO> 
									 data.content :	List<BusinessVO> data -->
								<c:forEach var="businessVO" items="${data.content}">
									<tr class="odd">
										<td class="sorting_1">${businessVO.rnum}</td>
										<td><a href="/business/detail?bSiteno=${businessVO.bSiteno}">${businessVO.bName}</a></td>
<%-- 										<td><a href="/business/detail2/${businessVO.bSiteno}">${businessVO.bName}</a></td> --%>
										<td>${businessVO.bAddress}</td>
										<td>${businessVO.bTelno}</td>
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