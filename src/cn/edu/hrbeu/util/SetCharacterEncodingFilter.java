package cn.edu.hrbeu.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.wabacus.config.Config;

public class SetCharacterEncodingFilter implements Filter{
	private FilterConfig filterConfig = null;
	
	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 try {
	            if(!Config.encode.equalsIgnoreCase("utf-8")) {//如果当前项目采用的不是UTF-8编码
	                HttpServletRequest httpRequest=(HttpServletRequest)request;
	                String contentType=httpRequest.getContentType();
	                if (contentType != null
	                        && contentType.toLowerCase().startsWith(
	                                "application/x-www-form-urlencoded; charset=utf-8")) {//报表提交
	                    request.setCharacterEncoding("UTF-8");
	                }else {
	                    request.setCharacterEncoding(Config.encode);
	                }
	                response.setContentType("text/html; charset="+Config.encode);
	            }else{
	                request.setCharacterEncoding("UTF-8");
	                response.setContentType("text/html;charset=UTF-8");
	            }
	            chain.doFilter(request,response);
	        } catch (Exception e){
	            e.printStackTrace();
	        }
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
