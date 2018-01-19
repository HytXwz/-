package com.hytXwz.booksys.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 某一个ISBN类别的图书信息
 * 
 * @author 河堐头小王子
 * @version 1.0
 * @date 2017年12月19日
 * @remarks
 */
public class BookInfo implements Serializable {	//保存在文件中
	private static final long serialVersionUID = -1394492691161311829L;
	private String isbn; // 图书ISBN编号
	// private String bookType; //图书类型
	// 将图书类型定义为枚举类，避免出现过多的图书类型
	private BookType booktype; // 图书类型（枚举）
	private String bookName; // 书名
	private String author; // 作者
	private String publisher; // 出版社
	private Date publishDate; // 出版日期
	private String bookPrice; // 图书价格
	private String curCount; // 馆藏数量
	private String count; // 可借数量
	private String bookIntro; // 图书简介
	// private Book[] books; //本isbn编号的图书数组
	//BookInfo和Book类之间是一对多的关系，所以用一个List来表示
	private List<Book> bookList;	//同一个ISBN的图书集合
	
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
	 * 向集合中添加图书的方法
	 * @param book
	 */
	public boolean addBook(Book book){
		if(null == bookList){
			bookList = new ArrayList<>();
		}
		if(null == book || !(book instanceof Book))return false;
		//如果是同一本书，返回false（这里用到了Book类重写的equals方法）
		if(bookList.contains(book))return false;
		//为已添加的图书对象设置图书信息
		book.setBookInfo(this);
		bookList.add(book);
		return true;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public boolean setIsbn(String isbn) {
		// 这里用一个正则表达式来判断输入是否合法--默认isbn是8位数字
		if (isbn.matches("[0-9]{8}")) {
			this.isbn = isbn;
			return true;
		} else {
			System.out.println("请正确输入八位isbn编号！"); // 暂时只提示录入错误后续改进
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
		if (bookPrice.matches("[0-9]{1,3}")) { // 价格0-999
			this.bookPrice = bookPrice;
			return true;
		}
		System.out.println("请重新录入图书价格！"); // 暂时只提示录入错误后续改进
		return false;
	}

	public String getCurCount() {
		return curCount;
	}

	public boolean setCurCount(String curCount) {
		if (curCount.matches("[0-9]{1,4}")) { // 数量0-9999
			this.curCount = curCount;
			return true;
		}
		System.out.println("请重新录入图书数量！"); // 暂时只提示录入错误后续改进
		this.curCount = "1";
		return false;
	}

	public String getCount() {
		return count;
	}

	public boolean setCount(String count) {
		if (count.matches("[0-9]{1,4}")) { // 数量0-9999
			this.count = count;
			return true;
		}
		System.out.println("请重新录入图书可借数量！"); // 暂时只提示录入错误后续改进
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
		玄幻小说, 武侠小说, 原创小说, 网游小说, 都市小说, 言情小说, 
		青春小说, 历史小说, 军事小说, 科幻小说, 恐怖小说
	}
}
