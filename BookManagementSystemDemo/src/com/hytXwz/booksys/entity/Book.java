package com.hytXwz.booksys.entity;

import java.io.Serializable;

/**
 * 某一本书的信息,继承自图书信息类
 * Book对象包含BookInfo对象
 * @author 河堐头小王子
 * @version 1.0
 * @date 2017年12月19日
 * @remarks
 */
public class Book implements Serializable {

	private static final long serialVersionUID = 4422958321755291592L;
	private String isbn; // 图书的ISBN书号
	private String bookId; // 图书的书号
	// private boolean canBorrow; // 图书是否可借
	// 这里把canBorrow用枚举类来表示状态，通过状态来判断图书是否可借
	private States states; // 图书的状态
	private BookInfo bookInfo;	//图书信息对象
	
	public Book() {
	}

	public Book(String isbn, String bookId) {
		this.isbn = isbn;
		this.bookId = bookId;

	}
	
	
	/**
	 * 判断是否是同一个Book对象
	 */
	@Override
	public boolean equals(Object obj) {
		if(null == obj)return false;
		Book book = (Book)obj;
		return this.bookId.equals(book.getBookId());
	}
	
	public String getIsbn() {
		return isbn;
	}

	public boolean setIsbn(String isbn) {
		// 这里用一个正则表达式来判断输入是否合法--默认isbn是8位数字
		if (isbn.matches("[0-9]{8}")) {
			this.isbn = isbn;
			return true;
		}
		System.out.println("请重新录入isbn！"); // 暂时只提示录入错误后续改进
		return false;
	}

	public String getBookId() {
		return bookId;
	}

	public boolean setBookId(String bookId) {
		if (isbn.matches("[0-9]{6}")) { // 暂定为bookId位6位数字
			this.bookId = bookId;
			return true;
		}
		System.out.println("请重新录入bookId！"); // 暂时只提示录入错误后续改进
		return false;
	}

	public States getStates() {
		return states;
	}

	public void setStates(States states) {
		this.states = states;
	}

	public BookInfo getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	
}
enum States {
		可借, 不可借, 损坏, 遗失, 维修中
	}
