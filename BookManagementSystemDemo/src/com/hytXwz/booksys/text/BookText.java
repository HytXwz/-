package com.hytXwz.booksys.text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hytXwz.booksys.auth.Role;
import com.hytXwz.booksys.biz.BookInfoBiz;
import com.hytXwz.booksys.biz.impl.BookInfoBizImplV1;
import com.hytXwz.booksys.entity.Book;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.User;
import com.hytXwz.booksys.util.FileUtil;
import com.hytXwz.booksys.view.BookView;





/**
 * 图书管理系统的测试类
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月1日
 * @remarks
 */
public class BookText {
	public static void main(String[] args) {		
		
//		测试图书实体类();
//		测试图书业务类();
//		测试角色();
//		测试用户类();
		
		测试视图类();
		
		
	}
	
	public static void 测试视图类(){
		new BookView();
	}
	
	public static void 测试用户类(){
		User user = new User(new Role("齐天大圣", "operator"));
		user.setUserName("孙悟空");
		user.setPassWord("jingubang");
		Set<User> userSet = new HashSet<>();
		userSet.add(user);
		FileUtil.saveUserInfo(userSet);
		Set<User> newUserSet = FileUtil.readUserInfo();
		for(User u : newUserSet){
			System.out.println(u.getUserName() + u.getPassWord() + u.getRole().getKey());
		}
	}
	
	public static void 测试角色(){
		Role role = new Role();
		System.out.println(role.getName() + "\t" + role.getKey());
		System.out.println("权限集合：" + role.getPermissions());
		System.out.println();
//		Role role2 = new Role("操作员", "operator");
//		System.out.println(role2.getName() + "\t" + role2.getKey());
//		System.out.println("权限集合：" + role2.getPermissions());
		System.out.println(role.inStore("12345678", 77));
		BookInfo bookInfo2 = role.getBookInfoBiz().findByIsbn("12345678");
		System.out.println(bookInfo2.getCurCount());
	}
	
	
	
	public static void 测试图书业务类(){
		BookInfoBiz bookBiz = new BookInfoBizImplV1();
//		BookInfo bookInfo = bookBiz.findByIsbn("12345678");
//		System.out.println(bookInfo.getBookName());
//		bookBiz.outStore("12345678", 60);
		bookBiz.inStore("12345678", 77);
		//再次从文件中查看图书信息
		BookInfo bookInfo2 = bookBiz.findByIsbn("12312312");
		System.out.println(bookInfo2.getCurCount());
		System.out.println(bookInfo2.getCount());
	}
	
	public static void 测试图书实体类(){
		BookInfo bookInfo = new BookInfo();
		bookInfo.setBookName("秦时明月");
		bookInfo.setIsbn("12345678");
		bookInfo.setCurCount("100");
		bookInfo.setCount("100");
		Book book = new Book("12345678", "123456");
		bookInfo.addBook(book);
		System.out.println(book.getBookInfo().getBookName());
		
		System.out.println(bookInfo.getCurCount());
		
		Map<String, BookInfo> bookInfoMap = new HashMap<>();
		bookInfoMap.put(book.getIsbn(), bookInfo);
		FileUtil.SaveBookInfoMap(bookInfoMap);	//static方法直接用类名调用
		FileUtil.ReadBookInfoMap();
		for (String isbn : bookInfoMap.keySet()) {	//keySet()方法去除map中的所有对象
			System.out.println(isbn);
		}
	}
	
	
}




