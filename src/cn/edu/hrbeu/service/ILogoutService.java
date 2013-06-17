package cn.edu.hrbeu.service;

public interface ILogoutService {

	/**
	 * 功能：更新当前登录用户的最后登录时间
	 * @param loginName 
	 */
	public void updateUserLastLoginTime(String loginName);

}
