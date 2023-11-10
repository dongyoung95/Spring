<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<style>
	.grid {
		display:grid;
		width:100%;
		grid-template-columns: 1fr 1fr 1fr;
		gap:20px;
	}
	.grid img {
		width:100%;
		min-height:200px;
		max-height:200px;
		object-fit: cover;
	}
</style>
<div class="card card-primary">
	<div class="card-header">
		<h3 class="card-title">ARTICLE DETAIL</h3>
	</div>


	<form id="frm" name="frm" class="" enctype="multipart/form-data" action="/article/updatePost">
		<div class="card-body">
			<div class="form-group">
				<label for="articleNo">Article No</label> 
				<input type="text" class="form-control" id="articleNo" name="articleNo"
					value="${data.articleNo }" placeholder="articleNo" />
			</div>
			<div class="form-group">
				<label for="writerId">Writer ID</label> 
				<input type="text" class="form-control" id="writerId" name="writerId"
					 value="${data.writerId }" placeholder="writerId" />
			</div>
			<div class="form-group">
				<label for="writerId">Writer Name</label> 
				<input type="text" class="form-control" id="writerName" name="writerName"
					value="${data.writerName }" placeholder="writerName" />
			</div>
			<div class="form-group">
				<label for="title">Title</label> 
				<input type="text" class="form-control" id="title" name="title"
					value="${data.title }" placeholder="title" />
			</div>
			<div class="form-group" style="display:none;">
				<label for="exampleInputFile">File input</label>
				<div class="input-group">
					<div class="custom-file">
						<input type="file" class="custom-file-input" id="uploadFile" name="uploadFile" multiple>
						<label class="custom-file-label" for="uploadFile">Choose file</label>
					</div>
				</div>
			</div>
			<div class="grid">
				<c:forEach var="atchFileDetailVO" items="${data.atchFileDetailVOList }">
					<img src="/resources/upload/${atchFileDetailVO.streFileNm }">
				</c:forEach>
			</div>
			
		</div>

		<div class="card-footer">
			<!-- 일반모드 시작 -->
			<p id="p1">
				<input type="button" class="btn btn-primary" id="edit" value="수정" />
				<input type="button" class="btn btn-danger" id="delete" value="삭제" />
				<input type="button" class="btn btn-success" id="list" value="목록" />
			</p>
			<!-- 일반모드 끝 -->
			<!-- 수정모드 시작 -->
			<p id="p2" style="display:none;">
				<input type="submit" class="btn btn-primary" id="confirm" value="확인" />
				<input type="button" class="btn btn-danger" id="cancel" value="취소" />
			</p>
			<!-- 수정모드 끝 -->
		</div>
	</form>
	<script type="text/javascript">
		window.onload=function(){
			let target=document.getElementById('uploadFile');
			console.log(target);	
			target.addEventListener('change',function(){
			
				if(target.value.length){ // 파일 첨부인 상태일경우 파일명 출력
					var fileList = "";
			        for(i = 0; i < target.files.length; i++){
			            fileList += target.files[i].name + ', ';
			        }
			        console.log(fileList);
			        let target2 = $(".custom-file-label");
			        console.log("target2 : " , target2[0]);
			        target2[0].innerHTML = fileList;
				}else{ //버튼 클릭후 취소(파일 첨부 없을 경우)할때 파일명값 안보이게
					$('.custom-file-label').html("Choose file");
				}
				
			});
		}
	</script>
</div>