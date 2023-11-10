<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="card card-primary">
	<div class="card-header">
		<h3 class="card-title">CREATE ARTICLE</h3>
	</div>


	<form:form modelAttribute="articleVO" id="frm" name="frm" class="" enctype="multipart/form-data" action="/article/createForm">
		<div class="card-body">
			<div class="form-group">
				<label for="articleNo">Article No</label> 
				<form:input type="text" class="form-control" path="articleNo"
					placeholder="articleNo" />
			</div>
			<div class="form-group">
				<label for="writerId">Writer ID</label> 
				<form:input type="text" class="form-control" path="writerId"
					placeholder="writerId" />
			</div>
			<div class="form-group">
				<label for="writerId">Writer Name</label> 
				<form:input type="text" class="form-control" path="writerName"
					placeholder="writerName" />
			</div>
			<div class="form-group">
				<label for="title">Title</label> 
				<form:input type="text" class="form-control" path="title"
					placeholder="title" />
			</div>
			<div class="form-group">
				<label for="exampleInputFile">File input</label>
				<div class="input-group">
					<div class="custom-file">
						<input type="file" class="custom-file-input" id="uploadFile" name="uploadFile" multiple>
						<label class="custom-file-label" for="uploadFile">Choose file</label>
					</div>
				</div>
			</div>
			
		</div>

		<div class="card-footer">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</form:form>
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
					$('.custom-file-label').html("");
				}
				
			});
		}
	</script>
</div>