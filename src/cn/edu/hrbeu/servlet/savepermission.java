package cn.edu.hrbeu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.edu.hrbeu.util.Constants;
import cn.edu.hrbeu.util.DatabaseUtil;
import cn.edu.hrbeu.util.UUIDGenerator;


public class savepermission extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//int roleid=(Integer)request.getSession().getAttribute(Constants.QUANXIAN);
		String checkCount = request.getParameter("text");
		String roleid=request.getParameter("roleid");
		String userroleid=(String)request.getSession().getAttribute("userroleid");
		String sql="";
		sql="delete  FROM  t_sromapping where staffroleid='"+roleid+"' and operationid in (SELECT a.operationid FROM t_sromapping A  where a.staffroleid='"+userroleid+"') ";
		DatabaseUtil db=new DatabaseUtil();
		Connection conn;
		ResultSet rs;
		if(checkCount!=null){
			try{
				conn=DatabaseUtil.getConnection();
				rs=DatabaseUtil.getDBPojo(sql, conn);
				String[] opid=checkCount.split(",");
				for(int i=0;i<opid.length;i++){
					sql="insert into t_sromapping (smappingid,OPERATIONID,staffroleid) values('"+UUIDGenerator.generateID()+"','"+opid[i]+"','"+roleid+"')";
					rs=DatabaseUtil.getDBPojo(sql, conn);
				}
				DatabaseUtil.closeAll(conn, null, rs);
			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		
		
		PrintWriter out = response.getWriter();
		//out.print(checkCount);
		out.flush();
		out.close();
		System.out.println("serverlet----------"+checkCount);
	}

}