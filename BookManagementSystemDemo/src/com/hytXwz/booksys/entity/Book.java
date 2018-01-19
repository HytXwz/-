package com.hytXwz.booksys.entity;

import java.io.Serializable;

/**
 * ĳһ�������Ϣ,�̳���ͼ����Ϣ��
 * Book�������BookInfo����
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2017��12��19��
 * @remarks
 */
public class Book implements Serializable {

	private static final long serialVersionUID = 4422958321755291592L;
	private String isbn; // ͼ���ISBN���
	private String bookId; // ͼ������
	// private boolean canBorrow; // ͼ���Ƿ�ɽ�
	// �����canBorrow��ö��������ʾ״̬��ͨ��״̬���ж�ͼ���Ƿ�ɽ�
	private States states; // ͼ���״̬
	private BookInfo bookInfo;	//ͼ����Ϣ����
	
	public Book() {
	}

	public Book(String isbn, String bookId) {
		this.isbn = isbn;
		this.bookId = bookId;

	}
	
	
	/**
	 * �ж��Ƿ���ͬһ��Book����
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
		// ������һ��������ʽ���ж������Ƿ�Ϸ�--Ĭ��isbn��8λ����
		if (isbn.matches("[0-9]{8}")) {
			this.isbn = isbn;
			return true;
		}
		System.out.println("������¼��isbn��"); // ��ʱֻ��ʾ¼���������Ľ�
		return false;
	}

	public String getBookId() {
		return bookId;
	}

	public boolean setBookId(String bookId) {
		if (isbn.matches("[0-9]{6}")) { // �ݶ�ΪbookIdλ6λ����
			this.bookId = bookId;
			return true;
		}
		System.out.println("������¼��bookId��"); // ��ʱֻ��ʾ¼���������Ľ�
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
		�ɽ�, ���ɽ�, ��, ��ʧ, ά����
	}
