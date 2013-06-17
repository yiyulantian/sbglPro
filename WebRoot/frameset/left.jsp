<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="cn.edu.hrbeu.util.Constants"%>
<%@page import="cn.edu.hrbeu.pojo.Operation"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.wabacus.config.Config"%>
<%@page import="cn.edu.hrbeu.util.*" %>
<%
	String path = request.getContextPath();
	
	
	String bigCatalog = request.getParameter("bigCatalog");
	//String roleid = "";
	//if(session.getAttribute("userroleid")!=null)
	//roleid=session.getAttribute("userroleid").toString();
	//Connection conn = Config.getInstance().getDataSource("ds_mysql")
	//		.getConnection();
	Connection conn = DatabaseUtil.getConnection();
	
	PreparedStatement pstmt = null;
	List<Operation> operationList = new ArrayList<Operation>();

	try {
		pstmt = conn.prepareStatement("SELECT * FROM operation where parentid like ?  order by operationid");
		pstmt.setString(1, bigCatalog+"%");
		//pstmt = conn
		//		.prepareStatement("select C.operationId,C.operationname,C.parentId,C.url from STAFF  A inner join roleopmapping B  on A.ROLE = B.ROLEID,Operation C where A.ST_ACCOUNT = ? and C.parentId like ? AND C.OPERATIONID = B.OPERATIONID order by C.OPERATIONID");
		//pstmt.setString(1, user);
	//	pstmt.setString(2, bigCatalog+"%");

		ResultSet userOperationRs = pstmt.executeQuery();
		while (userOperationRs.next()) {
			Operation operation = new Operation();
			operation.setOperationID(userOperationRs.getString("OPERATIONID"));
			operation.setOperationName(userOperationRs.getString("OPERATIONNAME"));
			System.out.println("-------------------------"+userOperationRs.getString("OPERATIONNAME"));
			operation.setParentID(userOperationRs.getString("PARENTID"));
			
			operation.setURL(userOperationRs.getString("URL"));
			
			operationList.add(operation);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	String str = "<div id=\"leftbox\">";
	if(bigCatalog.equals("11")){

		str += "<div id=\"aa\"><strong>设备管理</strong></div>";
		
	}
	else if(bigCatalog.equals("12")){

		str += "<div id=\"aa\"><strong>XXXX</strong></div>";

		
	}
	else if(bigCatalog.equals("13")){
		str += "<div id=\"aa\"><strong>XXXX</strong></div>";
		
	}
	else if(bigCatalog.equals("14")){
		str += "<div id=\"aa\"><strong>XXXX</strong></div>";
		
	}
	
	String parentName= "";
	char id = 'a';
	for (Operation o : operationList) {
		if (!o.getParentID().equals("00000000")&&o.getParentID().substring(2,4).equals("00"))
		{	
			if(!parentName.equals("")){
				str+="</div>";
			}
			
			str+="<div id=\"bb\" onmouseup=\"with(findObj('"+String.valueOf(id)+"'))if(style.display=='none'){style.display=''; this.style.backgroundImage='url(../images/title_bg_hide.gif)'}else{style.display='none'; this.style.backgroundImage='url(../images/title_bg_show.gif)'}\">"+o.getOperationName()+"</div><div id=\""+String.valueOf(id)+"\" style=\"display: none\">";
			parentName = o.getParentID();
			id++;
		}
		else if (!o.getParentID().equals("00000000")&&!o.getParentID().substring(2,4).equals("00"))
			str+="<div id=\"divLink\"><a href=\".."+o.getURL()+"\"target=\"mainFrame\" name=\"a1\" class=\"aaa\" onclick=\"changeColor(this)\">"+o.getOperationName()+"</a></div>";
	}
		str+="</div></div>";
		
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>车载系统</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css"></link>
		<script type="text/javascript" src="<%=path%>/js/leftTree.js"></script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" valign="middle" noWrap id="frmTitle">
					<!--开始-->
					<%=str %>

					
						
					<!--结束-->
				</td>
			</tr>
		</table>
	</body>
</html>

