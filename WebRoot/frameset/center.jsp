<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>鹤岗电业局科技管理系统</title>
<link rel="stylesheet" type="text/css" href="../css/style.css"></link>
<script>
function oa_tool(){
if(window.parent.oa_frame.cols=="0,8,*"){
frameshow.src="../images/ht_ss_left.jpg";
oa_tree.title="a"
window.parent.oa_frame.cols="200,8,*";
}
else{
frameshow.src="../images/ht_ss_right.jpg";
oa_tree.title="a"
window.parent.oa_frame.cols="0,8,*";}
}
		</script>
<style type="text/css">
<!--
html.body {
	background-color: #ffffff;
	margin:0px;
	height:100%;
}
body {
	background-color: #A4A4A4;
}
-->
</style></head>
<body>
<table width="8"  height="400" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#a4a4a4" style="margin:0px; padding:0px;">
<tr align="center">
<td valign="middle" style="background-repeat:no-repeat;">
<div id="oa_tree" onclick="oa_tool();" title="隐藏/显示工具栏" style="margin:0px; padding:0px;"><img title="隐藏/显示工具栏" src="../images/ht_ss_left.jpg" name="frameshow" width="8" height="84" id="frameshow"/></div>
</td>
</tr>
</table>
</body>
</html>