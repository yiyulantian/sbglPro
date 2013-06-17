package cn.edu.hrbeu.jndi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface IDataSource {
	//获得数据库连接对象
	public Connection getConnection() throws SQLException;
	//关闭所有
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException;
	//关闭所有
	public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException;
}