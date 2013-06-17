package cn.edu.hrbeu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wabacus.config.Config;

public class DatabaseUtil {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			 conn = Config.getInstance().getDataSource("ds_mysql").getConnection();
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KJGLDB?useUnicode=true&characterEncoding=UTF-8", "root", "javasky");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	//功能：实现方法，关闭所有，包括结果集、sql执行器、数据库连接对象
	public static void closeAll(Connection conn, Statement stmt, ResultSet rs)
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
	public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs)
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
	public static Connection getRawConnection() throws SQLException{
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		//	Connection con = Config.getInstance().getDataSource("ds_mysql").getConnection();
			 conn = DriverManager.getConnection("jdbc:oracle:thin:@222.27.255.110:1521:orcl", "chezai", "Apple123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	/**
	 * 执行sql语句，获取数据集
	 * @param sql 用来得到传来的sql语句
	 * @param conn 用来获取到数据库连接的方法
	 * @return ResultSet 执行sql语句所得的结果集
	 */
	public static ResultSet getDBPojo(String sql,Connection conn) {
	//Connection conn = null;
	
	try {
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
//		try {  
//			conn.close();  
//			} catch (Exception e) {}  
		//st.close();
		return rs;
	} catch (Exception e) {
		e.printStackTrace();
		try {
			
			conn.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		return null;
	}
	}
}
