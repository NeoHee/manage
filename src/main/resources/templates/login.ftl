<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0" />
<#include "head.ftl">
<title>账户管理登陆界面</title>

<!-- <link rel="stylesheet" type="text/css" href="${res}/css/bootstrap.min.css" /> -->

<style type="text/css">
html,body {
	height: 100%;
}
.box {
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#6699FF', endColorstr='#6699FF'); /*  IE */
	background-image:linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-o-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-moz-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-webkit-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	background-image:-ms-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
	
	margin: 0 auto;
	position: relative;
	width: 100%;
	height: 100%;
}
.login-box {
	width: 100%;
	max-width:500px;
	height: 400px;
	position: absolute;
	top: 50%;

	margin-top: -200px;
	/*设置负值，为要定位子盒子的一半高度*/
	
}
@media screen and (min-width:500px){
	.login-box {
		left: 50%;
		/*设置负值，为要定位子盒子的一半宽度*/
		margin-left: -250px;
	}
}	

.form {
	width: 100%;
	max-width:500px;
	height: 275px;
	margin: 25px auto 0px auto;
	padding-top: 25px;
}	
.login-content {
	height: 300px;
	width: 100%;
	max-width:500px;
	background-color: rgba(255, 250, 2550, .6);
	float: left;
}		
	
	
.input-group {
	margin: 0px 0px 30px 0px !important;
}
.form-control,
.input-group {
	height: 40px;
}

.form-group {
	margin-bottom: 0px !important;
}
.login-title {
	padding: 20px 10px;
	background-color: rgba(0, 0, 0, .6);
}
.login-title h1 {
	margin-top: 10px !important;
}
.login-title small {
	color: #fff;
}

.link p {
	line-height: 20px;
	margin-top: 30px;
}
.btn-sm {
	padding: 8px 24px !important;
	font-size: 16px !important;
}
</style>

</head>

<body>
<div class="box">
		<div class="login-box">
			<div class="login-title text-center">
				<h1><small class="top">登录</small></h1>
			</div>
			<div class="login-content ">
			<div class="form">
			<form id="sub" method="post">
				<div class="form-group">
					<div class="col-xs-12  ">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
							<input type="text" id="username" name="userName" class="form-control" placeholder="用户名">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12  ">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
							<input type="password" id="password" name="passWord" class="form-control" placeholder="密码">
						</div>
					</div>
				</div>
				<div class="form-group form-actions">
					<div class="col-xs-4 col-xs-offset-4 ">
					<!-- <span class="glyphicon glyphicon-off"></span> -->
						<button type="button"  class="btn btn-sm btn-info" style="margin-left:30px;">登录</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6 link" style="margin-left:130px;">
						<p class="text-center remove-margin"><small class="bottom1">还没注册?</small> <a href="javascript:void(0)" onclick="change()"><small class="bottom2">注册</small></a>
						</p>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
	function change(){
		var vali = $(".bottom2").text();
		$("#username").val("");
		$("#password").val("");
		if(vali=="注册"){
			$(".bottom2").text("登录");
			$(".bottom1").text("已有帐号？");
			$(".top").text("注册");
			$(".btn").text("注册");
		}else{
			$(".bottom2").text("注册");
			$(".bottom1").text("还没注册？");
			$(".top").text("登录");
			$(".btn").text("登录");
		}
	}
	
	$(".btn").on("click",function(){
		var tt = $(".btn").text();
		var name = $("#username").val();
		var pas = $("#password").val();
		if(tt == "登录"){
			$.post("${springMacroRequestContext.contextPath}/manage/common/log.do", {userName:name,passWord:pas},function(data){
				if(data == "true"){
					$("#sub").attr("action","${springMacroRequestContext.contextPath}/manage/common/index.do");
					$("#sub").submit();
				}else{
					$("#username").attr("placeholder","用户名或密码错误请重新输入");
					$("#username").val("");
					$("#password").val("");
				}
			});
		}else if(tt == "注册"){
			$.post("${springMacroRequestContext.contextPath}/manage/common/reg.do", {userName:name,passWord:pas},function(data){
				if(data == "true"){
					$("#sub").attr("action","${springMacroRequestContext.contextPath}/manage/common/index.do");
					$("#sub").submit();
				}else{
					$("#username").attr("placeholder","用户名已被注册请重新输入");
					$("#username").val("");
					$("#password").val("");
				}
			});
		}
	});
</script>

</html>