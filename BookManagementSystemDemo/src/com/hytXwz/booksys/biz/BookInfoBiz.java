package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.entity.BookInfo;

/**
 * 图书业务类的接口
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月1日
 * @remarks
 */
public interface BookInfoBiz extends Biz<BookInfo> {
	
	/**
	 * 出库
	 * @param isbn		//出库图书的isbn
	 * @param outCount	//出库数量
	 * @return			//是否出库成功
	 */
	public boolean outStore(String isbn, int outCount);
	
	/**
	 * 入库
	 * @param isbn		//入库图书的isbn
	 * @param inCount	//入库数量
	 * @return			//是否入库成功
	 */
	public boolean inStore(String isbn, int inCount);
	
	/**
	 * 通过isbn来查询图书信息
	 * @param isbn
	 * @return	返回查找到的图书信息
	 */
	public BookInfo findByIsbn(String isbn);
	
}





