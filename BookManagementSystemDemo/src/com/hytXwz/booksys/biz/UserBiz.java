package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.entity.User;
/**
 * �û�ҵ����Ľӿ�
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��4��
 * @remarks
 */
public interface UserBiz extends Biz<User>{
	
	/**
	 * �û���¼
	 * @param user
	 * @return
	 */
	public User login(User user);
	
	
}











