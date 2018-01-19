package com.hytXwz.booksys.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * ĳһ��ISBN����ͼ����Ϣ
 * 
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2017��12��19��
 * @remarks
 */
public class BookInfo implements Serializable {	//�������ļ���
	private static final long serialVersionUID = -1394492691161311829L;
	private String isbn; // ͼ��ISBN���
	// private String bookType; //ͼ������
	// ��ͼ�����Ͷ���Ϊö���࣬������ֹ����ͼ������
	private BookType booktype; // ͼ�����ͣ�ö�٣�
	private String bookName; // ����
	private String author; // ����
	private String publisher; // ������
	private Date publishDate; // ��������
	private String bookPrice; // ͼ��۸�
	private String curCount; // �ݲ�����
	private String count; // �ɽ�����
	private String bookIntro; // ͼ����
	// private Book[] books; //��isbn��ŵ�ͼ������
	//BookInfo��Book��֮����һ�Զ�Ĺ�ϵ��������һ��List����ʾ
	private List<Book> bookList;	//ͬһ��ISBN��ͼ�鼯��
	
	public BookInfo(){
		
	}
	
	public BookInfo(String isbn){
		this.setIsbn(isbn);
	}

	@Override
		public boolean equals(Object obj) {
			if(null == obj)return false;
			Book book = (Book)obj;
			return this.getIsbn().equals(book.getIsbn());
		}
	
	/**
	 * �򼯺������ͼ��ķ���
	 * @param book
	 */
	public boolean addBook(Book book){
		if(null == bookList){
			bookList = new ArrayList<>();
		}
		if(null == book || !(book instanceof Book))return false;
		//�����ͬһ���飬����false�������õ���Book����д��equals������
		if(bookList.contains(book))return false;
		//Ϊ����ӵ�ͼ���������ͼ����Ϣ
		book.setBookInfo(this);
		bookList.add(book);
		return true;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public boolean setIsbn(String isbn) {
		// ������һ��������ʽ���ж������Ƿ�Ϸ�--Ĭ��isbn��8λ����
		if (isbn.matches("[0-9]{8}")) {
			this.isbn = isbn;
			return true;
		} else {
			System.out.println("����ȷ�����λisbn��ţ�"); // ��ʱֻ��ʾ¼���������Ľ�
			return false;
		}
	}
	
	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public String getBookName() {
		return bookName;
	}

	public boolean setBookName(String bookName) {
		if(null == bookName)return false;
		this.bookName = bookName;
		return true;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public boolean setBookPrice(String bookPrice) {
		if (bookPrice.matches("[0-9]{1,3}")) { // �۸�0-999
			this.bookPrice = bookPrice;
			return true;
		}
		System.out.println("������¼��ͼ��۸�"); // ��ʱֻ��ʾ¼���������Ľ�
		return false;
	}

	public String getCurCount() {
		return curCount;
	}

	public boolean setCurCount(String curCount) {
		if (curCount.matches("[0-9]{1,4}")) { // ����0-9999
			this.curCount = curCount;
			return true;
		}
		System.out.println("������¼��ͼ��������"); // ��ʱֻ��ʾ¼���������Ľ�
		this.curCount = "1";
		return false;
	}

	public String getCount() {
		return count;
	}

	public boolean setCount(String count) {
		if (count.matches("[0-9]{1,4}")) { // ����0-9999
			this.count = count;
			return true;
		}
		System.out.println("������¼��ͼ��ɽ�������"); // ��ʱֻ��ʾ¼���������Ľ�
		return false;
	}

	public String getBookIntro() {
		return bookIntro;
	}

	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}

	public BookType getBooktype() {
		return booktype;
	}

	public void setBooktype(BookType booktype) {
		this.booktype = booktype;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	enum BookType {
		����С˵, ����С˵, ԭ��С˵, ����С˵, ����С˵, ����С˵, 
		�ഺС˵, ��ʷС˵, ����С˵, �ƻ�С˵, �ֲ�С˵
	}
}
