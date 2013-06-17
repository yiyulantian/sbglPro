package cn.edu.hrbeu.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.edu.hrbeu.jndi.JNDIDataSource;
import cn.edu.hrbeu.service.ILogoutService;
import cn.edu.hrbeu.util.DatabaseUtil;

public class LogoutServiceImpl implements ILogoutService {

	public void updateUserLastLoginTime(String loginName) {
		
	}

}
