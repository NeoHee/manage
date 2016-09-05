<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<#include "head.ftl">
<!-- <script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script> -->
</head>

<script type="text/javascript">
		function turnOverPage(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
</script>

<body>
	<fieldset>
         <legend>学生信息列表</legend>
         
    <div class="search">
			<form action="${springMacroRequestContext.contextPath}/student/list.do" id="searchForm" method="post">
				<input id="pageNo" name="pageNo" type="hidden"/>
				<input id="pageSize" name="pageSize" type="hidden" />
			</form>	
				<div style="margin-left:17px;width:100px;float:left;">
				<select class="form-control" id="select1" onchange="setSearchCondition(this.value)">
							  <option value="1" selected="true">编码</option>
							  <option value="2">姓名</option>
							  <option value="3">类型</option>
							  <option value="4">入学时间</option>
				</select>
				</div>
				
				<div style="float:left;margin-left:5px;" class="conditions">
					<input style="height:28px;" type="text" class="form-control" name="keyWord" maxlength="20" placeholder="请输入您要的内容"/>
				</div>
				
				<div style="margin-left:5px;float:left;display:none;" class="conditions">
					<select class="form-control" id="select2">
							  <option value="1" selected="true">学生会会长</option>
							  <option value="2">宣传委员</option>
							  <option value="3">学习委员</option>
							  <option value="4">体育委员</option>
							  <option value="5">学生</option>
					</select>
				</div>
				
				<div style="float:left;margin-left:5px;display:none;" class="conditions">
					<input style="width:130px;height:28px;float:left;" type="text" class="form-control date" name="dateFrom"/>
					<input style="width:130px;height:28px;float:left;margin-left:5px;" type="text" class="form-control date" name="dateTo"/>
				</div>
				
				<input type="button" class="btn btn-sm btn-info" onclick="page(0,10)" value=" 搜索 " style="height:28px;margin-left:5px;width:50px;"/>
			
	</div>
    
    
    <div class="panel-body">
        <table id="table_local" class="table table-bordered table-striped table-hover">
            <thead>
                <tr>
                    <th style="text-align: center;">编码</th>
                    <th style="text-align: center;">姓名</th>
                    <th style="text-align: center;">类型</th>
                    <th style="text-align: center;">入学时间</th>
                    <th style="text-align: center;">备注</th>
                    <th style="text-align: center;">状态</th>
                    <th style="text-align: center;">修改</th>
                </tr>
            </thead>
                <tbody>
                <#list page.list as st>
                        <tr>
                            <td>${st.code}</td>
                            <td>${st.name!''}</td>
                            <td>
                            		<#switch (st.typeId)>
                            			<#case 1>
                            				学生会会长
                            			<#break>
                            			<#case 2>
                            				宣传委员
                            			<#break>
                            			<#case 3>
                            				学习委员
                            			<#break>
                            			<#case 4>
                            				体育委员
                            			<#break>
                            			<#case 5>
                            				学生
                            			<#break>
                            		</#switch>
                            </td>
                            <td>${(st.createDate?string("yyyy-MM-dd"))!''}</td>
                            <td>${st.remark!''}</td>
                            <td>
                            		<#if (st.state) == 0>
                            			<a href="javascript:void(0);" onclick="stateInfo(this,${st.code},1)" style="color:#666666;">封存</a>
                            		<#else>
                            			<a href="javascript:void(0);" onclick="stateInfo(this,${st.code},0)" style="color:red;">启封</a>
                            		</#if>
                            </td>
                            <td>
                            		<a href="${springMacroRequestContext.contextPath}/student/editPage.do?code=${st.code}"><img src="/images/yun_back_img2.gif"/></a>
								<a href="${springMacroRequestContext.contextPath}/student/del.do?code=${st.code}"><img src="/images/yun_back_img3.gif"/></a>
                            </td>
                        </tr>
                   </#list>
                </tbody>
        </table>
    </div>
</div>
    
    
         
			<div class="paging center">
				${page}
			</div>
     </fieldset>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
	  $("#select2").select2();
	  $("#select1").select2();
	  $(".date").daterangepicker({
	      singleDatePicker: true,
	      startDate: moment().subtract(0, 'days')
	    });
	});
	
//切换搜索条件
function setSearchCondition(val){
	$('.conditions').hide();
	if(val==1 || val==2){
		$('.conditions').eq(0).show();
	}else if(val==3){
		$('.conditions').eq(1).show();	
	}else if(val==4){
		$('.conditions').eq(2).show();	
	}
}
//更改信息状态 封存－－－－启封
function stateInfo(i,code,num){
	var state = $(i).text();
	$.post("${springMacroRequestContext.contextPath}/student/chageState.do",{code:code,stateId:num},function(data){
		if(data == "true"){
			if(state=="封存"){
				$(i).text("启存").css("color","red");
			}else{
				$(i).text("封存").css("color","#666666");
			} 
		}
	});
}

function page(n,s){
	//条件
	var op = jQuery("#select1  option:selected").val();
	//类型
	var type = jQuery("#select2  option:selected").val();
	//关键字
	var kw = $("input[name='keyWord']").val();
	//日期从多久开始到多久结束
	var df = $("input[name='dateFrom']").val();
	var dt = $("input[name='dateTo']").val();
	$.post("${springMacroRequestContext.contextPath}/student/queryList.do?pageNo="+n+"&pageSize="+s,{option:op,keyWord:kw,typeId:type,dateFrom:df,dateTo:dt},function(data){
		data=JSON.parse(data);
		var arr = data.list;
		var tablehtml="";
		tablehtml += "<thead><tr><th style=\"text-align: center;\">编码</th><th style=\"text-align: center;\">姓名</th><th style=\"text-align: center;\">类型</th><th style=\"text-align: center;\">入学时间</th><th style=\"text-align: center;\">备注</th><th style=\"text-align: center;\">状态</th><th style=\"text-align: center;\">修改</th></tr></thead><tbody>";
		for (var i = 0; i < arr.length; i++) {
			tablehtml+= "<tr>";
			tablehtml+= "<td>"+arr[i].code+"</td>";
			tablehtml+= "<td>"+arr[i].name+"</td>";
			tablehtml+= "<td>"+getType(arr[i].typeId)+"</td>";
			tablehtml+= "<td>"+arr[i].createDate+"</td>";
			tablehtml+= "<td>"+arr[i].remark+"</td>";
			tablehtml+="<td>";
			if(arr[i].state==0){
				tablehtml+="<a href=\"javascript:void(0);\" style=\"color:#666666;\" onclick=\"stateInfo(this,"+arr[i].code+",1)\">封存</a>"
			}else{
				tablehtml+="<a href=\"javascript:void(0);\" style=\"color:red;\" onclick=\"stateInfo(this,"+arr[i].code+",0)\">启封</a>"
			}
			tablehtml+="</td>";
			tablehtml+= "<td>"
			tablehtml+="<a href='${springMacroRequestContext.contextPath}/student/editPage.do?code="+arr[i].code+"'><img src='${res}/images/yun_back_img2.gif'/></a>";
			tablehtml+="<a href='${springMacroRequestContext.contextPath}/student/del.do?code="+arr[i].code+"'><img src='${res}/images/yun_back_img3.gif'/></a>";
			tablehtml+= "</td>"
			tablehtml+="</tr>"
		}
		tablehtml += "</tbody>";
		$(".table").html(tablehtml);
		$(".paging").html(data.pageHtml);
	});
}
//返回类型名字
function getType(type){
	var str = "";
	switch(type){
		case 1:
			str="学生会会长";
			break;
		case 2:
			str="宣传委员";
			break;
		case 3:
			str="学习委员";
			break;
		case 4:
			str="体育委员";
			break;
		case 5:
			str="学生";
			break;
	}
	return str;
}


</script>
