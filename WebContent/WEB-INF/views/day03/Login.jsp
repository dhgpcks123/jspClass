<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>JavaScript Study</title>
		<link rel="stylesheet" type="text/css" href="../css/w3.css"> 
<!-- 		http://localhost/jspcls/day03/login.cls  프로젝트안에서 경로타고 들어가는데
			당연히 어디 컨테이너인지 얘는 인식할 수 있지! -->
		<link rel="stylesheet" type="text/css" href="../css/cls.css">
		<script type="text/javascript" src="../js/jquery-3.5.1.min.js"></script>
		<script type="text/javascript" src="../js/login/Login.js"></script>
		<style type="text/css">
			label{
				font-size: 16pt;
			}
			#msg {
				font-size: 15pt;
				color: indigo;
			}
		</style>
		<script type="text/javascript">
		</script>
	</head>
	
	<body>
		<div class="w3-content w3-center mw700">
		<h1 class="w3-blue w3-padding w3-card-4">회원로그인</h1>
		<form method="POST" action="/jspcls/day03/loginProc.cls" class="w3-col w3-padding w3-card-4 w3-margin-top" id="frm" name="frm">
			<div class="w3-col w3-margin-top">
				<label for="id" class="w3-col m2 w3-right-align w3-text-grey">I D : &nbsp;</label>
				<input type="text" name="id" id="id" placeholder="아이디를 입력하세요!"
						class="w3-col m9 w3-input w3-border w3-round-4">
			</div>
			<div class="w3-col w3-margin-top">
				<label for="pw" class="w3-col m2 w3-right-align w3-text-grey">P W : &nbsp;</label>
				<input type="password" name="pw" id="pw" placeholder="비밀번호를 입력하세요!"
						class="w3-col m9 w3-input w3-border w3-round-4">
			</div>
			<div class="w3-col w3-margin-top">
				<!-- 과제 : 홈버튼을 클릭하면 메인페이지로 이동하도록 하세요  -->
				<div class="w3-half w3-button w3-red w3-hover-orange" id="btn1">HOME</div>
				<div class="w3-half m2 w3-button w3-blue w3-hover-aqua" id="btn2">Login</div>
			</div>
		</form>
		<div class="w3-col w3-padding w3-card-4 w3-hide"><span id="msg">${SID} 님은 이미 로그인 했습니다!!!</span></div>
	</div>
	</body>
</html>