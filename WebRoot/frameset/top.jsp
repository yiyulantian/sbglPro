<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="java.sql.*" %>
<%@page import="com.wabacus.config.Config;" %>

<%
	String path = request.getContextPath();
	//String schoolname="";//分校
	String username="";//人名
	int role;
	String user = session.getAttribute("user").toString();
	Connection conn=Config.getInstance().getDataSource("ds_mysql").getConnection();
	PreparedStatement pstmt;
	
	ResultSet rs;
	pstmt = conn.prepareStatement("select * from user where username = ?");
	pstmt.setString(1, user);
	rs = pstmt.executeQuery();
	if(rs.next()) {
		username = rs.getString("realname");
	}
	


%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>设备管理系统</title>
<link rel="stylesheet" type="text/css" href="../css/style.css"></link>
<style type="text/css">
<!--
.STYLE1 {color: #FFFFFF}
body {
	background-color: #FFFFFF;
	height:100%;
}
.menuSelect{
	text-decoration:underline;
	font-size: 13px;
	font-weight: bold;
	font-style: inherit;
	margin-top: 
}
#table_bg{
width:100%;
height:108px;

}
-->
</style>
<script type="text/javascript" src="<%=path%>/commons/widget/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/topmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/commons/js/refreshTime.js"></script>
<script type="text/javascript">
function addFavorite()
{
	window.external.AddFavorite(parent.location.href, document.title);
}
</script>
</head>
<body onload="startclock()" > 

<div id="table_bg"> 
<table width="100%" height="108" border="0" cellpadding="0" cellspacing="0"  style="background: url(../images/top_bg.png)  repeat-x " >
  <tr>
    <td><table width="100%" height="108" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="right" valign="bottom"  style="   background:url(../images/top.png) no-repeat "   class="bj-top">
         <table width="98%" height="77" border="0" cellpadding="0" cellspacing="0">
		<tr height="45">
			<td height="37" colspan="13"><span style="margin-left:80%; font-size:14px; ">欢迎<%=username%>使用该系统!</span>		</td>
		</tr>
           <tr>
             <td width="150">&nbsp;</td>     

            <td width="120" align="center" valign="middle"><a href="#" onclick="studentmanage('<%=path%>')"><span class="STYLE1">设备管理</span></a></td>
            <td width="120" align="center" valign="middle"><a href="#" onclick="classmanage('<%=path%>')"><span class="STYLE1">XXXX</span></a></td>
            <td width="120" align="center" valign="middle"><a href="#" onclick="staffmanage('<%=path%>')"><span class="STYLE1">XXXX</span></a></td>           
            <td width="120" align="center" valign="middle"><a href="#" onclick="chargesys('<%=path%>')"><span class="STYLE1">XXXX</span></a></td>
             <!--  <td width="60" align="center" valign="middle"><a href="#" onclick="wagesys('<%=path%>')"><span class="STYLE1">工资核算</span></a></td>
            <td width="60" align="center" valign="middle"><a href="#" onclick="financesys('<%=path%>')"><span class="STYLE1">财务管理</span></a></td>
            <td width="60" align="center" valign="middle"><a href="#" onclick="booksys('<%=path%>')"><span class="STYLE1">教材购销</span></a></td>
            <td width="60" align="center" valign="middle"><a href="#" onclick="systemmanage('<%=path%>')"><span class="STYLE1">系统管理</span></a></td>
             -->
            
            <td width="12">&nbsp;</td>
            <td width="25" align="left" valign="middle"><img src="../images/new_9.jpg" width="20" height="20" border="0" onclick="window.parent.location='${pageContext.request.contextPath}/logout.servlet'"/></td>
            <td width="40" valign="middle"><a href="${pageContext.request.contextPath}/logout.servlet" target="_parent"><span class="STYLE1">退出</span></a></td>
            <td width="12">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>

</div>
</body>
</html>
 
<%

	conn.close();
	
%>
