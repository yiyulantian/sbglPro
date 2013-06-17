package cn.edu.hrbeu.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HrTag extends SimpleTagSupport{
	private String afterheight;

	public String getAfterheight() {
		return afterheight;
	}

	public void setAfterheight(String afterheight) {
		this.afterheight = afterheight;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = this.getJspContext().getOut();
		out.println("<tr><td valign=\"top\"><hr/></td></tr>");
		out.println("<tr><td height=\"" + afterheight + "\"></td></tr>");
	}
	
}
