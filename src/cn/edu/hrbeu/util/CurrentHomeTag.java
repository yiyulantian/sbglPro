package cn.edu.hrbeu.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CurrentHomeTag extends SimpleTagSupport {
	private String value;
	private String split;
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setSplit(String split){
		this.split = split;
	}
	
	public String getSplit(){
		return split;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = this.getJspContext().getOut();
		StringBuffer result = new StringBuffer("<strong>您的位置：</strong>");
		//先根据分隔符对value值进行分解
		if(null != value && value.trim().length() > 0 && null != split && split.length() > 0){
			String[] homes = value.split(split);
			if(null != homes && homes.length > 0){
				for(String str : homes){
					result.append(str).append("&nbsp;&gt;&nbsp;");
				}
				int len = result.toString().length();
				result.delete(len - 16, len);//去掉最后一个“&nbsp;&gt;&nbsp;”
			}
		}
		
		out.println("<tr><td>");
		out.println("<table style=\"margin-top='5px'\" width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" background=\"/EarthQuake/images/new_5a.jpg\">");
		out.println("<tr>");
		out.println("<td width=\"4%\" align=\"center\" valign=\"middle\"><img src=\"/EarthQuake/images/new7.gif\" width=\"14\" height=\"19\" /></td>");
		out.println("<td width=\"96%\" align=\"left\" valign=\"middle\"><span class=\"style2\">" + result.toString() + "</span></td>");
		out.println("</tr></table></td></tr>");
	}
}
