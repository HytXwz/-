package com.hytXwz.booksys.biz.impl;

import java.util.Map;

import com.hytXwz.booksys.biz.ReaderBiz;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;

/**
 * ����ҵ����ʵ�ֽӿ�
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��5��
 * @remarks
 */
public class ReaderBizImplV1 implements ReaderBiz{

	private static final long serialVersionUID = -3855310961104630509L;
	
	
	/**
	 * ����
	 */
	@Override
	public boolean borrowBook(Reader reader, BookInfo bookInfo) {
		if(reader == null){
			return false;
		}
		if(reader.getBorrowBook().size() == 5){
			System.out.println(reader.getReaderName() + "�Ѿ�����5���飬����ʧ�ܣ�");
			return false;
		}
		BookInfo fileBookInfo = new BookInfoBizImplV1().findByIsbn(bookInfo.getIsbn());
		if(Integer.parseInt(fileBookInfo.getCount()) < 0){
			System.out.println(bookInfo.getBookName() + "����Ϊ0��");
			return false;
		}
		reader.getBorrowBook().add(fileBookInfo);
		System.out.println("ͼ�����ɹ���");
		int count = (Integer.parseInt(fileBookInfo.getCount()) - 1);
		fileBookInfo.setCount(Integer.toString(count));
		new BookInfoBizImplV1().update(fileBookInfo);
		return true;
	}

	/**
	 * ����
	 */
	@Override
	public boolean returnBook(Reader reader, BookInfo bookInfo) {
		BookInfo fileBookInfo = new BookInfoBizImplV1().findByIsbn(bookInfo.getIsbn());
		reader.getBorrowBook().remove(fileBookInfo);
		System.out.println("ͼ��黹�ɹ���");
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





