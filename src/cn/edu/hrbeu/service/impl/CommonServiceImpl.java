package cn.edu.hrbeu.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import cn.edu.hrbeu.jndi.JNDIDataSource;
import cn.edu.hrbeu.service.ICommonService;
import cn.edu.hrbeu.util.Constants;
import cn.edu.hrbeu.util.DatabaseUtil;

public class CommonServiceImpl implements ICommonService {
	
	/**
	 * 功能：将页面选中的checkbox项转换为int[]
	 *
	 * 创建时间：2011年04月09日
	 * 修改时间：
	 * @param strSelectedIds 被选中的id
	 * @param split 分隔符，如逗号或者分号
	 * @return
	 * @throws SQLException
	 */
	public static int[] getSelectedCheckBoxs(String strSelectedIds) {
		int[] results = null;
		if(StringUtils.isNotEmpty(strSelectedIds)){
			String[] strIdArr = strSelectedIds.split(",");
			int len = strIdArr.length;
			
			if(null != strIdArr && len > 0){
				results = new int[len];
				int index = 0;
				for(String str : strIdArr){
					if(StringUtils.isNumeric(str))
						results[index++] = Integer.valueOf(str);
				}
			}
		}
		return results;
	}
	
	/**
	 * 功能：验证用户的合法性
	 * 
	 * 创建时间：2013年3月5日 19:08:50
	 * 修改时间：
	 * @param request 请求对象
	 * @return
	 * @author 远东
	 */
	public static boolean validate(HttpServletRequest request){
		boolean result = false;
		HttpSession session = request.getSession();
		String rand = (String)session.getAttribute(Constants.AUTH_IMG);
		String username = request.getParameter("username");//用户名
		String password = request.getParameter("password");//密码
		String validateword = request.getParameter("validateword");//验证码
		
		if(StringUtils.isEmpty(validateword)){
			request.setAttribute(Constants.LOGIN_ERROR, "验证码不能为空！");
			return result;
		}
		if(!validateword.trim().equalsIgnoreCase(rand.trim())){
			request.setAttribute(Constants.LOGIN_ERROR, "验证码输入错误！");
			return result;
		}
		if(StringUtils.isEmpty(username)){
			request.setAttribute(Constants.LOGIN_ERROR, "用户名不能为空！");
			return result;
		}
		if(StringUtils.isEmpty(password)){
			request.setAttribute(Constants.LOGIN_ERROR, "密码不能为空！");
			return result;
		}
		
		//根据username获得其密码信息
		String dbPassword = CommonServiceImpl.getPassword(username);
		/*if(password!=null&&password.equals(dbPassword)){//并且需要验证是否离职
			System.out.println(CommonServiceImpl.ifQualified(username));
			if(Integer.parseInt(CommonServiceImpl.ifQualified(username).trim())==1)
			{	
				result = true;
			}
			else{
				request.setAttribute(Constants.LOGIN_ERROR, "该用户已离职！");
			}
				}else{
			request.setAttribute(Constants.LOGIN_ERROR, "用户名或密码错误！");
		}
		*/
		if(password!=null&&password.equals(dbPassword)){
		    result = true;
		}else{
			request.setAttribute(Constants.LOGIN_ERROR, "用户名或密码错误！");
		}
		return result;
	}
	
	/*
	 * 判断是否在职。
	public static String ifQualified(String userName){
	
		String sql = "select st.isWorking from staff st left join staffaccount sa on st.staffid = sa.staffid where sa.account = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String isWorking = "0";
		try {
			//获得Connection对象
			conn = DatabaseUtil.getConnection();
		
			if(null == conn){
				conn = new JNDIDataSource().getConnection();
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if(null != rs){
				if(rs.next()){
					isWorking = rs.getString("isWorking");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				DatabaseUtil.closeAll(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isWorking;
	}
	*/
	
	/**
	 * 功能：根据用户名从数据库中获得对应的密码
	 * 
	 * 创建时间：2011年04月10日
	 * 修改时间：
	 * @param userName 用户名
	 * @return 用户密码 String
	 */
	public static String getPassword(String userName){
		String result = "";
		//String sql = "select sa.password,s.schoolid from staffaccount sa  inner join staff s on s.staffid = sa.staffid where sa.account = ?";
		String sql = "SELECT password FROM user  WHERE username = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//获得Connection对象
			conn = DatabaseUtil.getConnection();
		
			if(null == conn){
				conn = new JNDIDataSource().getConnection();
			}
			if(null == conn) return "";//表示没有密码
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if(null != rs){
				if(rs.next()){
					result = rs.getString("password");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				DatabaseUtil.closeAll(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.printf(result);
		return result;
	}
	/**
	 * 功能：根据用户名从数据库中获得当前登录用户的分校id并设置到session中
	 * 
	 * 
	 * 创建时间：2011年04月10日
	 * 修改时间：
	 * @param userName 用户名,session 
	 * 
	 */
	public static void setSchoolId(String userName,HttpSession session){
		String schoolid = "" ;
		String schoolname ="";
		String name ="";
		
		String sql = "select A.schoolid,A.name  ,D.schoolname from staff A left join staffaccount C on A.staffid = C.staffid left join branchschool D on D.schoolid = A.schoolid where C.account = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//获得Connection对象
			conn = new JNDIDataSource().getSysConnection();
			if(null == conn){
				conn = DatabaseUtil.getConnection();
			}
			if(null == conn) return ;//表示没有密码
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if(null != rs){
				if(rs.next()){
					schoolid = rs.getString("schoolid");
					schoolname = rs.getString("schoolname");
					if(schoolname==null||schoolname=="null") schoolname="爱尔英语总校";
					name=rs.getString("name");
				}
			}
			
				schoolname+="-";
				session.setAttribute("schoolid", schoolid);
				session.setAttribute("schoolname", schoolname);
				session.setAttribute("name", name);
				System.out.println("session  "+schoolid);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	/**
	 * 功能：根据登录名查询对应的单位权限信息，并将之设置到session中
	 * 
	 * 创建时间：2011年04月10日
	 * 修改时间：2011年05月09日
	 * @param session
	 */
//	public static void getUserAuthorityUnit(HttpSession session) {
//		String loginName = (String)session.getAttribute(Constants.USER_KEY);
//		if(StringUtils.isNotEmpty(loginName)){
//			String sql = "select UNIT_ID from ROLE_UNIT where ROLE_ID in (select role_id from user_role A right join users B on A.user_id = B.user_id where B.login_name = ?)";
//			Set<Integer> authorityIdSet = CommonServiceImpl.getUserAuthority(sql, loginName);
//			StringBuffer inSb = new StringBuffer("");
//			if(null != authorityIdSet && authorityIdSet.size() > 0){
//				for (Iterator<Integer> it = authorityIdSet.iterator(); it.hasNext();) {
//					inSb.append(it.next()).append(",");
//				}
//			}else{
//				authorityIdSet = new HashSet<Integer>();
//			}
//			
//			session.setAttribute(Constants.UNIT_OF_CURRENT_ROLE, authorityIdSet);
//			String units = inSb.toString();
//			/*根据登录名获得用户的部门权限*/
//			if (units.trim().length() > 0) {
//				session.setAttribute(Constants.USER_AUTHORITY_UNIT, units.substring(0,units.length() - 1));
//			}
//		}
//	}
	/**
	 * 功能：根据登录名查询对应的人员id，并将之设置到session中
	 * 
	 * 创建时间：2011年12月08日
	 * @param session
	 */
	public static void getUserAuthorityUnit(HttpSession session) {
		String loginName = (String)session.getAttribute(Constants.USER_KEY);
		System.out.println(loginName);
		if(StringUtils.isNotEmpty(loginName)){
			String sql = "select userid from user where username = ?";
			session.setAttribute("userid",CommonServiceImpl.getUserId(sql, loginName));
			}
	}
	
	/**
	 * 功能：获得当前用户角色的功能权限
	 * 
	 * 创建时间：2011年05月09日
	 * 修改时间：
	 * @param session
	 */
//	public static void getUserAuthorityFunction(HttpSession session) {
//		/*根据登录名获得用户的部门权限*/
//		String loginName = (String)session.getAttribute(Constants.USER_KEY);
//		if(StringUtils.isNotEmpty(loginName)){
//			String sql = "select FUNCTION_ID from ROLE_FUNCTION where ROLE_ID in (select role_id from user_role A right join users B on A.user_id = B.user_id where B.login_name = ?)";
//			Set<Integer> authorityIdSet = CommonServiceImpl.getUserAuthority(sql, loginName);
//			StringBuffer inSb = new StringBuffer("");
//			
//			if(null != authorityIdSet && authorityIdSet.size() > 0){
//				for (Iterator<Integer> it = authorityIdSet.iterator(); it.hasNext();) {
//					inSb.append(it.next()).append(",");
//				}
//			}else{
//				authorityIdSet = new HashSet<Integer>();
//			}
//			session.setAttribute(Constants.FUNCTION_OF_CURRENT_ROLE, authorityIdSet);
//			
//			String functions = inSb.toString();
//			if (functions.trim().length() > 0) {
//				session.setAttribute(Constants.USER_AUTHORITY_FUNCTION, functions.substring(0, functions.length() - 1));
//			}
//		}
//	}
	

	/**
	 * 功能：根据登录名获得用户的部门或者功能权限
	 * 
	 * 创建时间：2011年05月09日
	 * 修改时间：
	 * @param sql
	 * @param loginName
	 * @return 部门或功能ID的集合，用逗号隔开
	 */
	public static String getUserAuthority(String sql, String loginName){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//装用户部门权限或功能角色ID集合
		String authorityIdSet = "";
		try {
			//获得Connection对象
			conn = new JNDIDataSource().getConnection();
			if(null == conn){
				conn = DatabaseUtil.getConnection();
			}
			//if(null == conn) return 0;
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginName);
			rs = pstmt.executeQuery();
			if(null != rs){
				if(rs.next()){
				authorityIdSet = rs.getString("staffroleid");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				DatabaseUtil.closeAll(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(authorityIdSet);
		return authorityIdSet;
	}
	
	public static String getUserId(String sql, String loginName){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//装用户部门权限或功能角色ID集合
		String userid = "";
		try {
			//获得Connection对象
			conn = new JNDIDataSource().getConnection();
			if(null == conn){
				conn = DatabaseUtil.getConnection();
			}
			//if(null == conn) return 0;
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginName);
			rs = pstmt.executeQuery();
			if(null != rs){
				if(rs.next()){
				userid = rs.getString("userid");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				DatabaseUtil.closeAll(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userid;
	}
	
	public static String getUserAuthority2(String sql, String loginName){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//装用户部门权限或功能权限ID集合
		String authorityIdSet ="";
		try {
			//获得Connection对象
			conn = new JNDIDataSource().getConnection();
			if(null == conn){
				conn = DatabaseUtil.getConnection();
			}
			if(null == conn) return null;
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginName);
			rs = pstmt.executeQuery();
			if(null != rs){
				if(rs.next()){
				authorityIdSet = rs.getString(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				DatabaseUtil.closeAll(conn, pstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(authorityIdSet);
		return authorityIdSet;
	}
	/**
	 * 功能：处理附件路径
	 * 
	 * 创建时间：2011年04月27号
	 * 修改时间：
	 * @param FU_JIAN
	 * @return
	 */
	public static String handleFuJianURL(String FU_JIAN){
		FU_JIAN = FU_JIAN == null?"":FU_JIAN.trim();
		if(FU_JIAN.length() > 0){
			FU_JIAN = FU_JIAN.substring(17);
			//System.out.println("============LHQ===============" + FU_JIAN);
		}
		return FU_JIAN;
	}

	/**
	 * 功能：根据ID查询参数名称
	 * @param paramId
	 * @return
	 */
	public static String getParamNameById(String paramId) {
		String sql = "select PARAM_NAME from PARAMS where PARAM_ID = ?";
		return CommonServiceImpl.getNameById(sql, paramId);
	}

	/**
	 * 根据id获得企业的名称
	 * @param untiId
	 * @return
	 */
	public static String getUnitNameById(String unitId) {
		String sql = "select UNIT_NAME from UNITS where UNIT_ID = ?";
		return CommonServiceImpl.getNameById(sql, unitId);
	}
	
	/**
	 * 根据id获得对应的名称
	 * @param sql
	 * @param strId
	 * @return
	 */
	public static String getNameById(String sql, String strId) {
		String result = "";
		
		if(StringUtils.isNotEmpty(strId)){
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				//获得Connection对象
				conn = new JNDIDataSource().getConnection();
				if(null == conn){
					conn = DatabaseUtil.getConnection();
				}
				if(null == conn) return "";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.valueOf(strId).intValue());
				rs = pstmt.executeQuery();
				if(null != rs && rs.next()){
					result = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				try {
					DatabaseUtil.closeAll(conn, pstmt, rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
