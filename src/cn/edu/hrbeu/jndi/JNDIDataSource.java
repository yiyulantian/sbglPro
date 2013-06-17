package cn.edu.hrbeu.jndi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import com.wabacus.config.Config;
import com.wabacus.system.assistant.WabacusAssistant;

public class JNDIDataSource implements IDataSource {

	//功能：实现方法，关闭所有，包括结果集、sql执行器、数据库连接对象
	public void closeAll(Connection conn, Statement stmt, ResultSet rs)
			throws SQLException {
		// 判断rs是否为空，若不为空则关闭
		if(null != rs){
			rs.close();
		}
		//判断stmt是否为空，若为空则关闭
		if(null != stmt){
			stmt.close();
		}
		//判断conn是否为空，若为空且没有关闭则关闭
		if(null != conn && !conn.isClosed()){
			conn.close();
		}
	}

	//功能：实现方法，关闭所有，包括结果集、sql执行器、数据库连接对象
	public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs)
			throws SQLException {
		// 判断rs是否为空，若不为空则关闭
		if(null != rs){
			rs.close();
		}
		//判断stmt是否为空，若为空则关闭
		if(null != pstmt){
			pstmt.close();
		}
		//判断conn是否为空，若为空且没有关闭则关闭
		if(null != conn && !conn.isClosed()){
			conn.close();
		}
	}
	
	//功能：实现方法，获得数据库连接
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Connection con  = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");//加载com.mysql.jdbc包里面的Driver(mysql的驱动程序)类
			
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/educationsys","root","123");
			 con = Config.getInstance().getDataSource("ds_oracle").getConnection();
			return con;
			/*//获得Context对象
			javax.naming.Context ctx = new javax.naming.InitialContext();
			//通过调用lookup()方法来获得javax.sql.DataSource对象，强制类型转换
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/KJGL");
			//调用javax.sql.DataSource对象的getConnection()方法来获得数据库连接对象Connection
			conn = ds.getConnection();*/
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	public Connection getSysConnection(){
		Connection conn = null;
		conn = Config.getInstance().getDataSource("ds_oracle").getConnection();
		return conn;
	}

}

