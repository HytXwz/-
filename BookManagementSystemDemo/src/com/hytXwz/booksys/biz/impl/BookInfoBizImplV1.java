package com.hytXwz.booksys.biz.impl;

import java.util.Map;

import com.hytXwz.booksys.biz.BookInfoBiz;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.util.FileUtil;



/**
 * BookInfoBiz的第一版实现类
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月1日
 * @remarks
 */
public class BookInfoBizImplV1 implements BookInfoBiz{

	private static final long serialVersionUID = -7478475775850289607L;

	@Override
	public boolean add(BookInfo bookInfo) {
		//1、获得所有的BookInfoMap
		//2、向Map中添加传入的BookInfo对象
		//3、将Map对象重新写会文件
		if(null == bookInfo)return false;
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return false;
		if(bookInfoMap.containsKey(bookInfo.getIsbn())){
			return false;	//已存在
		}
		bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		FileUtil.SaveBookInfoMap(bookInfoMap);
		return false;
	}

	@Override
	public boolean delete(BookInfo bookInfo) {
		if(null == bookInfo)return false;
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return false;
		if(!bookInfoMap.containsKey(bookInfo.getIsbn())){
			System.out.println("要删除的图书不存在！");
			return false;	//不存在
		}
		bookInfoMap.remove(bookInfo.getIsbn());	//注意remove的参数是key
		FileUtil.SaveBookInfoMap(bookInfoMap);
		return true;
	}

	/**
	 * 修改图书信息
	 */
	@Override
	public BookInfo update(BookInfo bookInfo) {
		if(null == bookInfo)return null;
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return null;
		if(!bookInfoMap.containsKey(bookInfo.getIsbn())){
			return null;	//不存在
		}
		bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		FileUtil.SaveBookInfoMap(bookInfoMap);
		return bookInfo;
	}

	@Override
	public BookInfo findById(String id) {
		//如果一个方法子类中没有意义，可以手动抛出一下异常
		throw new UnsupportedOperationException("BookInfoBiz中不支持根据ID的查询操作。"
				+ "\n请使用findByIsbn()方法");
	}

	@Override
	public Map<String, BookInfo> findAll() {
		return FileUtil.ReadBookInfoMap();
	}

	@Override
	public boolean outStore(String isbn, int outCount) {
		BookInfo bookInfo = findByIsbn(isbn);
		if(null == bookInfo)return false;	//没有这本书
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return false;
		if(outCount > Integer.parseInt(bookInfo.getCount())){
			System.out.println("库存不足！");
			//出库数量大于实际库存量
			return false;
		}
		int count = Integer.parseInt(bookInfo.getCount()) - outCount;//出库后的实际库存量，注意与馆藏数量区分
		bookInfo.setCurCount(Integer.toString(count));	//修改图书信息
		bookInfoMap.put(isbn, bookInfo);	//将修改后的图书信息放回集合中
		FileUtil.SaveBookInfoMap(bookInfoMap);	//写入文件
		return true;
	}

	@Override
	public boolean inStore(String isbn, int inCount) {
		BookInfo bookInfo = findByIsbn(isbn);
		if(null == bookInfo)return false;	//没有这本书
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return false;
		if(!bookInfoMap.containsKey(isbn))return false;	//只能对集合中存在的图书进行操作
		int count = Integer.parseInt(bookInfo.getCurCount()) + inCount;
		bookInfo.setCurCount(Integer.toString(count));	//修改图书信息
		bookInfo.setCount(Integer.toString(count));
		bookInfoMap.put(isbn, bookInfo);	//将修改后的图书信息放回集合中
		FileUtil.SaveBookInfoMap(bookInfoMap);	//写入文件
		return true;
	}

	@Override
	public BookInfo findByIsbn(String isbn) {
		if(!isbn.matches("[0-9]{8}"))return null;
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return null;
		if(!bookInfoMap.containsKey(isbn)){
			return null;	//不存在
		}
		return bookInfoMap.get(isbn);
	}
}
