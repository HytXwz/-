package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;

/**
 * ����ҵ����
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��5��
 * @remarks
 */
public interface ReaderBiz extends Biz<Reader>{
	
	/**
	 * ���鷽��
	 * @param reader
	 * @return
	 */
	public boolean borrowBook(Reader reader, BookInfo bookInfo);
	
	/**
	 * ���鷽��
	 * @param reader
	 * @return
	 */
	public boolean returnBook(Reader reader, BookInfo bookInfo);

}






