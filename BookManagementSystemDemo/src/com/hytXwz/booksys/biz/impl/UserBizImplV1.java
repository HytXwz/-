package com.hytXwz.booksys.biz.impl;

import java.util.Map;
import java.util.Set;

import com.hytXwz.booksys.biz.UserBiz;
import com.hytXwz.booksys.entity.User;
import com.hytXwz.booksys.util.FileUtil;

/**
 * 用户业务接口实现类
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月4日
 * @remarks
 */
public class UserBizImplV1 implements UserBiz{

	private static final long serialVersionUID = -6877073759861271823L;

	@Override
	public boolean add(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User update(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 */
	@Override
	public User login(User user) {
		Set<User> userSet = FileUtil.readUserInfo();
		if(userSet == null || userSet.size() == 0)return null;
//		System.out.println(userSet.contains(user));
//		if(userSet.contains(user)){	//前提是User实现了equals方法
//			return user;  这个是不对的，当前user仅仅只有用户名和密码，其他属性都没有。应该从userSet中取出user
		for(User u : userSet){
			if(u.equals(user)) return u;
		}
//		}
		return null;
	}
}











