package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.biz.impl.BookInfoBizImplV1;
import com.hytXwz.booksys.biz.impl.UserBizImplV1;

/**
 * 业务工厂类
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月2日
 * @remarks
 */
public class BizFactory {
	
	/**
	 * 通过业务类名称，获得相应业务类的实现
	 * @param bizName
	 * @return
	 */
	public static Biz<?> getBiz(String bizName){
		switch (bizName.toLowerCase()) {
		case "administrator":	//超级管理员
			return new BookInfoBizImplV1();
		case "default":			//默认角色，与超管一样
			return new BookInfoBizImplV1();
		case "operator":
			return new UserBizImplV1();
		default:
			return null;
		}
		
	}
	
	
}
