package cn.edu.hrbeu.service;

public interface ILogoutService {

	/**
	 * ���ܣ����µ�ǰ��¼�û�������¼ʱ��
	 * @param loginName 
	 */
	public void updateUserLastLoginTime(String loginName);

}
