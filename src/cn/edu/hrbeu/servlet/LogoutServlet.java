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
		//�޸ĵ�ǰ��¼�û���������ʱ��
		ILogoutService logoutService = new LogoutServiceImpl();
		logoutService.updateUserLastLoginTime((String)session.getAttribute(Constants.USER_KEY));
		//ע��session����
		session.invalidate();
		response.sendRedirect("login.jsp");
	}

}
