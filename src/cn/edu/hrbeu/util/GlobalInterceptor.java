package cn.edu.hrbeu.util;

import java.sql.Connection;
import java.sql.ResultSet;
import com.wabacus.system.ReportRequest;
import com.wabacus.system.intercept.AbsPageInterceptor;


public class GlobalInterceptor extends AbsPageInterceptor{

	public void doStart(ReportRequest rrequest) {
		String pageid=rrequest.getPagebean().getId();
		String roleid=(String) rrequest.getRequest().getSession().getAttribute("userroleid");
		System.out.println(roleid+"===+++====");
		if("999999991".equals(roleid));
		else{
			String sql="";
			sql="SELECT * FROM (SELECT * FROM t_staffoperation where pageid='"+pageid+"' and componentid is not null)A ,（select * from t_sromapping where staffroleid="+roleid+" )B where A.operationid=B.operationid(+)";
			//取出page有的权限点，和角色有的权限点。做左联接。roleid为0 的即没有此权限。
			Connection conn;
			ResultSet rs;
			try {
				conn=DatabaseUtil.getConnection();
				rs= DatabaseUtil.getDBPojo(sql, conn);
				System.out.println(sql);

					while(rs.next()){
						
						if(rs.getInt("staffroleid")==0){//因为数据库中存的都是disabled，readonly（负权限），所以==0时，应该让负权限起效，即为true
							rrequest.authorize(rs.getString("componentid"),rs.getString("parttype"),rs.getString("partid"),rs.getString("permissiontype"),"true"); 
							System.out.println(rs.getString("partid")+"----");
						}else{
							rrequest.authorize(rs.getString("componentid"),rs.getString("parttype"),rs.getString("partid"),rs.getString("permissiontype"),"false"); 
							System.out.println(rs.getString("partid")+"*****");
						}
					}
					DatabaseUtil.closeAll(conn, null, rs);
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}
		}
		
	}
	
	
	
	public void doEnd(ReportRequest rrequest) {
		
		
	}
}
