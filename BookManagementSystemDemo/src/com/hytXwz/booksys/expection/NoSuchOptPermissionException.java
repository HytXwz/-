package com.hytXwz.booksys.expection;
/**
 * 自定义异常-- 此操作没有权限
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月2日
 * @remarks
 */
public class NoSuchOptPermissionException extends RuntimeException{

	private static final long serialVersionUID = -6573580484619569273L;
	
	public NoSuchOptPermissionException(){
		super();
		System.out.println("此操作没有权限异常");
	}

	public NoSuchOptPermissionException(String message){
		super(message);
	}
}
