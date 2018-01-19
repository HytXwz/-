package com.hytXwz.booksys.biz.impl;

import java.util.Map;

import com.hytXwz.booksys.biz.ReaderBiz;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;

/**
 * 读者业务类实现接口
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月5日
 * @remarks
 */
public class ReaderBizImplV1 implements ReaderBiz{

	private static final long serialVersionUID = -3855310961104630509L;
	
	
	/**
	 * 借书
	 */
	@Override
	public boolean borrowBook(Reader reader, BookInfo bookInfo) {
		if(reader == null){
			return false;
		}
		if(reader.getBorrowBook().size() == 5){
			System.out.println(reader.getReaderName() + "已经借了5本书，借书失败！");
			return false;
		}
		BookInfo fileBookInfo = new BookInfoBizImplV1().findByIsbn(bookInfo.getIsbn());
		if(Integer.parseInt(fileBookInfo.getCount()) < 0){
			System.out.println(bookInfo.getBookName() + "数量为0！");
			return false;
		}
		reader.getBorrowBook().add(fileBookInfo);
		System.out.println("图书借出成功！");
		int count = (Integer.parseInt(fileBookInfo.getCount()) - 1);
		fileBookInfo.setCount(Integer.toString(count));
		new BookInfoBizImplV1().update(fileBookInfo);
		return true;
	}

	/**
	 * 还书
	 */
	@Override
	public boolean returnBook(Reader reader, BookInfo bookInfo) {
		BookInfo fileBookInfo = new BookInfoBizImplV1().findByIsbn(bookInfo.getIsbn());
		reader.getBorrowBook().remove(fileBookInfo);
		System.out.println("图书归还成功！");
		int count = (Integer.parseInt(fileBookInfo.getCount()) + 1);
		fileBookInfo.setCount(Integer.toString(count));
		new BookInfoBizImplV1().update(fileBookInfo);
		return true;
	}
	
	@Override
	public boolean add(Reader t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Reader t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reader update(Reader t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Reader> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}





