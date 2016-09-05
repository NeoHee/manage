<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "head.ftl">
<title></title>
</head>
<body>
		<form id="sub" class="form-horizontal" role="form" method="post">
                    <fieldset>
                        <legend>修改学生信息</legend>
                       <div class="form-group">
                          <label class="col-sm-2 control-label" for="ds_host">编码</label>
                          <div class="col-sm-4">
                          	<input name="code" type="hidden" value="${st.code}"/>
                             <input class="form-control" id="ds_host" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${st.code}" disabled="true"/>
                          </div>
                          <div style="margin-top:5px;"></div>
                          <span style="color:red" class="waring"></span>
                       </div>
                       
                       <div class="form-group">
                          <label class="col-sm-2 control-label" for="ds_password">姓名</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="ds_password" type="text" name="name" value="${st.name!''}"/>
                          </div>
                          <div style="margin-top:5px;"></div>
                          <span style="color:red" class="waring1"></span>
                       </div>
                       
                       <div class="form-group">
                          <label class="col-sm-2 control-label" for="ds_password">类型</label>
                          <div class="col-sm-4">
                            <select class="form-control" id="select1">
							  <option value="1">学生会会长</option>
							  <option value="2">宣传委员</option>
							  <option value="3">学习委员</option>
							  <option value="4">体育委员</option>
							  <option value="5">学生</option>
							</select>
                          </div>
                       </div>
                       
                       <div class="form-group">
                          <label class="col-sm-2 control-label" for="ds_password">入学时间</label>
                          <div class="col-sm-4">
                          	<!-- placeholder="123456" -->
                             <input class="form-control" id="reservertion" type="text" name="createDate" value="${st.createDate!''}"/>
                          </div>
                       </div>
                       
                       <div class="form-group">
                          <label class="col-sm-2 control-label" for="ds_password">备注</label>
                          <div class="col-sm-4">
                             <textarea class="form-control" id="ds_password" name="remark">${st.remark!''}</textarea>
                          </div>
                       </div>
                    
                    <div class="form-group">
                          <label class="col-sm-2 control-label" for="ds_password">状态</label>
                          <div class="col-sm-4">
                          	<div style="margin-top:7px"></div>
                              <label><input name="Fruit" type="radio" class="state0" value="0" />启封</label> 
							  <label><input name="Fruit" type="radio" class="state1" value="1" />封存</label> 
                          </div>
                       </div>
                    </fieldset>   
                    <input name="typeId" type="hidden"/>
                    <input name="state" type="hidden"/>
                    <button type="button"  class="btn btn-sm btn-info" style="width:100px;height:35px;margin-left:181px;">确定</button>  
                </form>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
  var ddd = $("#select1").select2();
 //设置类型初始化
  ddd.val(${st.typeId}).trigger("change");
  $("#reservertion").daterangepicker({
      singleDatePicker: true
    });
});
//设置状态栏初始化
$(".state${st.state}").attr("checked","true");

var flag = true;

//检测编码是否有重复
$("input[name='code']").blur(function(){
	var code = $("input[name='code']").val();
	if(code == ""){
		$(".waring").text("编码不能为空");
		flag = false;
	}else{
		$(".waring").text("");
		$.post("${springMacroRequestContext.contextPath}/student/queryCode.do",{code:code},function(data){
			if(data=="true"){
				$(".waring").text("编码重复请重新输入");
			}else{
				flag = true;
			}
		});
	}
	
});
//姓名不能为空
$("input[name='name']").blur(function(){
	var name = $("input[name='name']").val();
	if(name == ""){
		flag = false;
		$(".waring1").text("姓名不能为空");
	}else{
		$(".waring1").text("");
		flag = true;
	}
});
//提交表单
$(".btn").on("click",function(){
	var typeId = jQuery("#select1  option:selected").val();
	var state = $("input[name='Fruit']:checked").val();
	$("input[name='typeId']").val(typeId);
	$("input[name='state']").val(state);
	$("#sub").attr("action","${springMacroRequestContext.contextPath}/student/update.do");
	if(flag){
		$("#sub").submit();
	}
});

</script>
