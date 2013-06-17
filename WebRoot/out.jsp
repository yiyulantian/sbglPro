<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="cn.edu.hrbeu.util.*" %>
<%
	
 String jsonData = "{\"data\":[";
        
       try{
       	   String roleid=request.getParameter("roleid");
       	   String userroleid=request.getParameter("userroleid");
    	   System.out.println("---------begin-------");
           Connection conn = DatabaseUtil.getConnection();          
           Statement statement = conn.createStatement();
           ResultSet result = statement.executeQuery("SELECT B.staffroleid as roleid,A.* FROM (SELECT b.* FROM t_sromapping A, t_staffoperation B where a.staffroleid='"+userroleid+"' and a.operationid=b.operationid) A,(select * from t_sromapping where staffroleid='"+roleid+"') B  where A.operationid=B.operationid(+) ");
			
           while(result.next()) {
        	   jsonData += "{\"id\":\""+result.getString("OPERATIONID")+"\",\"pId\":\""+result.getString("PARENTID")+"\",\"name\":\""+result.getString("operationname")+"\",\"open\":\"true\",";   
        	   
        	   if(result.getString("roleid") == null) {
        	       jsonData +=  "\"checked\":\"false\"},";    
        	     }
        	     else {
        	     jsonData += "\"checked\":\"true\"},";
        	     }
           }
           
           conn.close();  
       }
       catch (Exception e)  {
    	   e.printStackTrace();
       }
       
       jsonData = jsonData.substring(0,jsonData.length()-1); 
       jsonData += "]}";
       jsonData = jsonData.toString();
      
 //String jsonData1="{\"data\":[{\"id\":1,\"pid\":0,\"name\":\"姓名\"},{\"id\":11,\"pid\":1,\"name\":\"name2\"}]}";
      System.out.println(jsonData);
      //System.out.println(jsonData1);
		out.write(jsonData);
%>

