package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.entity.User;
/**
 * 用户业务类的接口
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月4日
 * @remarks
 */
public interface UserBiz extends Biz<User>{
	
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);
	
	
}











