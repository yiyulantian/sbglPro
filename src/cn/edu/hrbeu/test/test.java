package cn.edu.hrbeu.test;

import java.sql.Connection;
import java.sql.ResultSet;

import cn.edu.hrbeu.util.DatabaseUtil;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String roleid="99999999";
  		String sql="SELECT * FROM t_sromapping where operationid='130102104000' and staffroleid='"+roleid+"'";
		Connection conn=null;
		ResultSet rs=null;
		System.out.println("ok");
  		try{
  			
  			conn=DatabaseUtil.getRawConnection();
  			rs= DatabaseUtil.getDBPojo(sql, conn);
  			if(rs!=null&&rs.next())
  				System.out.println("ok");
		
  		}catch(Exception e){
  		
  		}finally{
			try{
				DatabaseUtil.closeAll(conn, null, rs);
			}catch(Exception e1){}
		}	
	}

}
