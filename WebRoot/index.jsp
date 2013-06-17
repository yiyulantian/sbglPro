<%@ page contentType="text/html; charset=UTF-8" %>
<%String path = request.getContextPath(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>车载物联网系统</title>
<link rel="shortcut icon" href="./favicon.ico">
<script type="text/javascipt">
window.onbeforeunload = function(){     
      var n = window.event.screenX - window.screenLeft;     
      var b = n > document.documentElement.scrollWidth-20;     
      if(b && window.event.clientY<0 || window.event.altKey)     
      {     
          alert("是关闭而非刷新");     
          window.event.returnValue = "是否关闭？";  
      }else{  
          alert("是刷新而非关闭");     
      }     
} 
</script>
</head>

<frameset rows="108,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="frameset/top.jsp" name="topFrame" scrolling="auto" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="200,8,*" frameborder="no" border="0" framespacing="0" id="oa_frame">
    <frame src="frameset/left.jsp?bigCatalog=11" name="leftFrame" scrolling="auto" noresize="noresize" id="leftFrame" title="leftFrame" />
	<frame src="frameset/center.jsp" name="centerFrame" id="centerFrame" title="centerFrame" />
    <frame src="frameset/content.jsp" name="mainFrame" scrolling="auto" id="mainFrame" title="mainFrame" />
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes></html>