package cn.edu.hrbeu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.hrbeu.service.ILogoutService;
import cn.edu.hrbeu.service.impl.LogoutServiceImpl;
import cn.edu.hrbeu.util.Constants;

public class LogoutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		//修改当前登录用户的最后访问时间
		ILogoutService logoutService = new LogoutServiceImpl();
		logoutService.updateUserLastLoginTime((String)session.getAttribute(Constants.USER_KEY));
		//注销session对象
		session.invalidate();
		response.sendRedirect("login.jsp");
	}

}
