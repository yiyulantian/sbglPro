<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="commons/widget/jquery/jquery.js"></script>
<script type="text/javascript" src="commons/widget/jquery/jquery.validate.pack.js"></script>



<!-- 
<script type="text/javascript" src="js/DD_belatedPNG.js"></script>
<script>
DD_belatedPNG.fix('*');
</script>
 -->
<title>设备管理系统</title>
<style type="text/css">
#mytable{
	background: url(images/viti.png) no-repeat;
}

body {
   	background-image: url(images/bg3.png);
	/*background-position: 100% 100%;
	background-color:#FAFFF0;*/
	background-repeat: repeat-x;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE3 {font-size: 12px; color: #FFFFFF; }
</style>
<script type="text/javascript">
var times=0;
$(document).ready(function(){

	  $("#loginForm").validate({
	    rules: {
				username: {
					required:true
				},
				password: {
					required:true
				},
				validateword: {
					required:true
	    		}
	    },
	    messages: {
	      username: "<br>&nbsp;<font color='#FF0000'>请输入用户名！</font>",
	      password: "<br>&nbsp;<font color='#FF0000'>请输入密码！</font>",
	      validateword: "<br>&nbsp;<font color='#FF0000'>请输入验证码！</font>"
	    }
	  });
});
function keySubmit()
{
     if (event.keyCode == 13)
     	loginMethod();
}
function loginMethod()
{
	if(document.loginForm.username.value==null||document.loginForm.username.value=="")
	{
		alert('请输入用户名！');
		return false;
	}
	if(document.loginForm.password.value==null||document.loginForm.password.value=="")
	{
		alert('请输入密码！');
		return false;
	}
	if(document.loginForm.validateword.value==null||document.loginForm.validateword.value=="")
	{
		alert('请输入验证码！');
		return false;
	}
	document.getElementById('loginForm').submit();
}
function cancelMethod()
{
	document.loginForm.username.value="";
	document.loginForm.password.value="";
	document.loginForm.validateword.value="";
}
function refresh(obj)
{
	obj.src="auth.jpg?"+(times++);
}

function refreshSelf(){
	if(window.location.href!= window.parent.location.href){
　		top.location="/CarSystemDemo/login.jsp";
	}
}
</script>
<link rel="shortcut icon" href="./favicon.ico">
</head>

<body onkeydown="keySubmit()" onload="refreshSelf();">
<form action="login.jsp?action=login" method="post" id="loginForm" name="loginForm">
<table align="center" width="1003" height="100%" border="0" cellpadding="0" cellspacing="0"  id="mytable"   >
  <tr>
    <td align="left" valign="top"><table width="98%" height="485" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="69%" height="222">&nbsp;</td>
        <td width="31%">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td align="left" valign="top">
          <table width="70%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="32%" height="40" align="right" valign="middle"><span class="STYLE3">用户名：</span></td>
              <td height="40" colspan="2" align="left" valign="middle"><label>
              	<input type="text" name="username" style="width: 130" value="${requestScope.user}"/>
                </label></td>
            </tr>
            <tr>
              <td height="40" align="right" valign="middle"><span class="STYLE3">密　码：</span></td>
              <td height="40" colspan="2" align="left" valign="middle">
              <input type="password" name="password" style="width: 130"/>
              </td>
            </tr>
            <tr>
              <td height="40" align="right" valign="middle"><span class="STYLE3">验证码：</span></td>
              <td width="39%" height="40" align="left" valign="middle">
              <input type="text" name="validateword" style="width: 80"/>
              </td>
              <td width="29%" height="40" align="left" valign="middle" title="点击更换验证码">
              <img src="auth.jpg" width="55" height="24" onClick="refresh(this)" style="cursor:pointer"/>
              </td>
            </tr>
            <tr>
              <td colspan="3" height="25" align="center" valign="top"><span class="STYLE3"><font color="blue">${requestScope.login_error}</font></span></td>
            </tr>
            <tr>
              <td height="39" colspan="3" align="center" valign="top"><img src="images/bot1.jpg" width="70" height="28" onClick="loginMethod()"/>　<img src="images/bot2.jpg" width="70" height="28" onClick="cancelMethod()"/></td>
              </tr>
          </table>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<input type="hidden" name="origin_uri" value="${requestScope.origin_uri}"/>
</form>
</body>
</html>
