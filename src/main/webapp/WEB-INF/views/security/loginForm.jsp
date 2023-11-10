<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="login-box">

	<div class="card">
		<div class="card-body login-card-body">
			<p class="login-box-msg">${error }${logout }</p>
			<!-- action="/login" method="post" 필수 -->
			<!-- input의 name값은 꼭 username, password 여야 함 -->
			<form action="/login" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control"
						name="username" placeholder="아이디를 입력해주세요" 
						required="required">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-envelope"></span>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="password" class="form-control"
					name="password" 
					placeholder="비밀번호를 입력해주세요" 
					required="required">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-lock"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-8">
						<div class="icheck-primary">
							<input type="checkbox" id="remember"> <label
								for="remember"> Remember Me </label>
						</div>
					</div>

					<div class="col-4">
						<button type="submit" class="btn btn-primary btn-block">Sign
							In</button>
					</div>

				</div>
				<!-- CSRF : Coress Site Request Forgery -->
				<sec:csrfInput/>
			</form>
		</div>

	</div>
</div>