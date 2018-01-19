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
 * ͼ�����ϵͳ�Ĳ�����
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��1��
 * @remarks
 */
public class BookText {
	public static void main(String[] args) {		
		
//		����ͼ��ʵ����();
//		����ͼ��ҵ����();
//		���Խ�ɫ();
//		�����û���();
		
		������ͼ��();
		
		
	}
	
	public static void ������ͼ��(){
		new BookView();
	}
	
	public static void �����û���(){
		User user = new User(new Role("�����ʥ", "operator"));
		user.setUserName("�����");
		user.setPassWord("jingubang");
		Set<User> userSet = new HashSet<>();
		userSet.add(user);
		FileUtil.saveUserInfo(userSet);
		Set<User> newUserSet = FileUtil.readUserInfo();
		for(User u : newUserSet){
			System.out.println(u.getUserName() + u.getPassWord() + u.getRole().getKey());
		}
	}
	
	public static void ���Խ�ɫ(){
		Role role = new Role();
		System.out.println(role.getName() + "\t" + role.getKey());
		System.out.println("Ȩ�޼��ϣ�" + role.getPermissions());
		System.out.println();
//		Role role2 = new Role("����Ա", "operator");
//		System.out.println(role2.getName() + "\t" + role2.getKey());
//		System.out.println("Ȩ�޼��ϣ�" + role2.getPermissions());
		System.out.println(role.inStore("12345678", 77));
		BookInfo bookInfo2 = role.getBookInfoBiz().findByIsbn("12345678");
		System.out.println(bookInfo2.getCurCount());
	}
	
	
	
	public static void ����ͼ��ҵ����(){
		BookInfoBiz bookBiz = new BookInfoBizImplV1();
//		BookInfo bookInfo = bookBiz.findByIsbn("12345678");
//		System.out.println(bookInfo.getBookName());
//		bookBiz.outStore("12345678", 60);
		bookBiz.inStore("12345678", 77);
		//�ٴδ��ļ��в鿴ͼ����Ϣ
		BookInfo bookInfo2 = bookBiz.findByIsbn("12312312");
		System.out.println(bookInfo2.getCurCount());
		System.out.println(bookInfo2.getCount());
	}
	
	public static void ����ͼ��ʵ����(){
		BookInfo bookInfo = new BookInfo();
		bookInfo.setBookName("��ʱ����");
		bookInfo.setIsbn("12345678");
		bookInfo.setCurCount("100");
		bookInfo.setCount("100");
		Book book = new Book("12345678", "123456");
		bookInfo.addBook(book);
		System.out.println(book.getBookInfo().getBookName());
		
		System.out.println(bookInfo.getCurCount());
		
		Map<String, BookInfo> bookInfoMap = new HashMap<>();
		bookInfoMap.put(book.getIsbn(), bookInfo);
		FileUtil.SaveBookInfoMap(bookInfoMap);	//static����ֱ������������
		FileUtil.ReadBookInfoMap();
		for (String isbn : bookInfoMap.keySet()) {	//keySet()����ȥ��map�е����ж���
			System.out.println(isbn);
		}
	}
	
	
}




