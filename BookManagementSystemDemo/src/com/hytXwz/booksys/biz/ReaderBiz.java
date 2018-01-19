package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;

/**
 * 读者业务类
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月5日
 * @remarks
 */
public interface ReaderBiz extends Biz<Reader>{
	
	/**
	 * 借书方法
	 * @param reader
	 * @return
	 */
	public boolean borrowBook(Reader reader, BookInfo bookInfo);
	
	/**
	 * 还书方法
	 * @param reader
	 * @return
	 */
	public boolean returnBook(Reader reader, BookInfo bookInfo);

}






