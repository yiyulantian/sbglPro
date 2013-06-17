<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="cn.edu.hrbeu.util.Constants"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%
	String path = request.getContextPath();
	Integer authorityFunctionSet = (Integer)request.getSession().getAttribute(Constants.QUANXIAN);
	System.out.println(authorityFunctionSet);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>爱尔英语学校管理系统</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css"></link>
		<script type="text/javascript" src="<%=path%>/js/leftTree.js"></script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" valign="middle" noWrap id="frmTitle">
					<!--开始-->
					<div id="leftbox">
					
						<div id="aa">
							<strong>人员管理</strong>
						</div>
						<%
									if(authorityFunctionSet.equals(Integer.valueOf(0))){
								%>
						<div id="bb"	onmouseup="with(findObj('cc'))if(style.display=='none'){style.display=''; this.style.backgroundImage='url(../images/title_bg_hide.gif)'}else{style.display='none'; this.style.backgroundImage='url(../images/title_bg_show.gif)'}">
							个人信息管理
						</div>
						<div id="cc" style="display: none">
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=StudentConsultRegPage"
									target="mainFrame" name="a1" class="aaa"
									onclick="changeColor(this)">咨询学员登记</a>
							</div>
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=StudentConsultManagePage"
									target="mainFrame" name="a1" class="aaa"
									onclick="changeColor(this)">咨询学员跟踪</a>
							
									<%
						}
						%>
							</div>
						</div>
						<%
						if(authorityFunctionSet.equals(Integer.valueOf(1))){
								%>
						<div id="bb"	onmouseup="with(findObj('dd'))if(style.display=='none'){style.display=''; this.style.backgroundImage='url(../images/title_bg_hide.gif)'}else{style.display='none'; this.style.backgroundImage='url(../images/title_bg_show.gif)'}">
							部门信息管理
						</div>
						<div id="dd">
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=bmld"
									target="mainFrame" name="a1" class="aaa"
									onclick="changeColor(this)">部门人员管理</a>
							</div>
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=rytj"
									target="mainFrame" name="a1" class="aaa"
									onclick="changeColor(this)">人员统计</a>
							</div>
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=xxxx"
									target="mainFrame" name="a1" class="aaa"
									onclick="changeColor(this)">个人信息</a>
							</div>
							
						</div>
						<%
						}
						if(authorityFunctionSet.equals(Integer.valueOf(2))){
								%>
						<div id="bb"	onmouseup="with(findObj('ee'))if(style.display=='none'){style.display=''; this.style.backgroundImage='url(../images/title_bg_hide.gif)'}else{style.display='none'; this.style.backgroundImage='url(../images/title_bg_show.gif)'}">
							所务信息管理
						</div>
						<div id="ee" style="display: none">
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=sld"
									target="mainFrame" name="a1"
									class="aaa" onclick="changeColor(this)">全所人员管理</a>
							</div>
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=rytj"
									target="mainFrame" name="a1" class="aaa"
									onclick="changeColor(this)">人员统计</a>
							</div>
							<div id="divLink">
								<a href="../ShowReport.wx?PAGEID=xxxx"
									target="mainFrame" name="a1" class="aaa"
									onclick="changeColor(this)">个人信息</a>
							</div>
						</div>
						<%
						}
						if(authorityFunctionSet.equals(Integer.valueOf(3))){
								%>
						<div id="bb" onmouseup="with(findObj('ff'))if(style.display=='none'){style.display=''; this.style.backgroundImage='url(../images/title_bg_hide.gif)'}else{style.display='none'; this.style.backgroundImage='url(../images/title_bg_show.gif)'}">
							管理员
						</div>
						<div id="ff" style="display: none">
							<div id="divLink">
								<a href="<%=path%>/earthquake/glyinfo.jsp"
									target="mainFrame" name="a1"
									class="aaa" onclick="changeColor(this)">人员管理</a>
							</div>
							<div id="divLink">
								<a href="<%=path%>/earthquake/addpeople.jsp"
									target="mainFrame" name="a1"
									class="aaa" onclick="changeColor(this)">新增人员</a>
							</div>
							<div id="divLink">
								<a href="<%=path%>/earthquake/peopletj.jsp"
									target="mainFrame" name="a1"
									class="aaa" onclick="changeColor(this)">人员统计</a>
							</div>
							<div id="divLink">
								<a href="<%=path%>/earthquake/peopleinfo.jsp"
									target="mainFrame" name="a1"
									class="aaa" onclick="changeColor(this)">个人信息</a>
							</div>
						</div>
					</div>
					<%
						}
								%>
					<!--结束-->
				</td>
			</tr>
		</table>
	</body>
</html>
