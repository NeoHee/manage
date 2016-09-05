<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学生管理系统</title>
<link href="${res}/css/style.css" rel="stylesheet"/>
<script src="${res}/js/jquery-1.11.0.min.js" language="javascript"></script>
<style>
	html,body{ overflow:hidden;}
</style>
</head>
<body>
<table width="100%" height="100%" cellspacing="0"> 
<tr>
<td class="pagetop" colspan="2">
	<img src="/images/ET_logo1.gif" class="l"/>
    <div class="r">
    	<span>欢迎您:${user.userName}</span>
        <span><a href="${springMacroRequestContext.contextPath}/manage/common/logout.do"><img src="/images/ET_img3.gif"/>退出</a></span>
    </div>
 </td></tr>
 <tr>
     <td class="pageleft" valign="top" width="186">
     	<div style="overflow:auto;" id="scroll">
        	          
                    <!-- <ul class="now">
                        <li class="h"><img src="${res}/images/ET_img5.gif"/>账户管理</li>
                        <li><a href="${res}/view/list.html" target="content">账户列表</a></li>
                    </ul> -->

	                	<ul>
                        <li class="h"><img src="/images/ET_img6.gif"/>学生管理</li>
                        <li><a href="${springMacroRequestContext.contextPath}/student/addPage.do" target="content">添加学生</a></li>                                            
                        <li><a href="${springMacroRequestContext.contextPath}/student/list.do" target="content">学生列表</a></li>
                    </ul>
               
        </div>
     </td>
     <td class="pageright" align="left" valign="top">
     <iframe src="${springMacroRequestContext.contextPath}/manage/common/rightPage.do" width="100%" height="100%" frameborder="0" scrolling="auto" name="content"/></iframe>
     </td>
</tr>
</table>
</body>
</html>
<script>
function setheight(){ //iframe高度
	var obj=$('iframe');
	$('.pageright').width($('body').width()-186);
	obj.height($('body').height()-64);	
	$('#scroll').height(obj.height()-30);
	window.onresize=function(){
		$('.pageright').width($('body').width()-186);
		obj.height($('body').height()-64);
		$('#scroll').height(obj.height()-30);
	}
}

function menu(){
	obj=$('.pageleft ul');
	var a=obj.find('a');
	obj.find('.h').click(function(){
		obj.removeClass('now');
		obj.find('img').attr('src',"${res}/images/ET_img6.gif");
		$(this).find('img').attr('src',"${res}/images/ET_img5.gif");
		$(this).parent().addClass('now');	
	});
	a.click(function(){
		a.removeClass("selecteds");
		$(this).addClass("selecteds");
	});
}

function gettext(){
	return $('.pageleft a.selecteds').html();
}

document.onkeydown=function(event){
	if(event.keyCode==116)return false;
}

menu();
setheight();
</script>