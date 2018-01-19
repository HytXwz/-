package com.hytXwz.booksys.biz.impl;

import java.util.Map;

import com.hytXwz.booksys.biz.BookInfoBiz;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.util.FileUtil;



/**
 * BookInfoBiz�ĵ�һ��ʵ����
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��1��
 * @remarks
 */
public class BookInfoBizImplV1 implements BookInfoBiz{

	private static final long serialVersionUID = -7478475775850289607L;

	@Override
	public boolean add(BookInfo bookInfo) {
		//1��������е�BookInfoMap
		//2����Map����Ӵ����BookInfo����
		//3����Map��������д���ļ�
		if(null == bookInfo)return false;
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return false;
		if(bookInfoMap.containsKey(bookInfo.getIsbn())){
			return false;	//�Ѵ���
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
			System.out.println("Ҫɾ����ͼ�鲻���ڣ�");
			return false;	//������
		}
		bookInfoMap.remove(bookInfo.getIsbn());	//ע��remove�Ĳ�����key
		FileUtil.SaveBookInfoMap(bookInfoMap);
		return true;
	}

	/**
	 * �޸�ͼ����Ϣ
	 */
	@Override
	public BookInfo update(BookInfo bookInfo) {
		if(null == bookInfo)return null;
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return null;
		if(!bookInfoMap.containsKey(bookInfo.getIsbn())){
			return null;	//������
		}
		bookInfoMap.put(bookInfo.getIsbn(), bookInfo);
		FileUtil.SaveBookInfoMap(bookInfoMap);
		return bookInfo;
	}

	@Override
	public BookInfo findById(String id) {
		//���һ������������û�����壬�����ֶ��׳�һ���쳣
		throw new UnsupportedOperationException("BookInfoBiz�в�֧�ָ���ID�Ĳ�ѯ������"
				+ "\n��ʹ��findByIsbn()����");
	}

	@Override
	public Map<String, BookInfo> findAll() {
		return FileUtil.ReadBookInfoMap();
	}

	@Override
	public boolean outStore(String isbn, int outCount) {
		BookInfo bookInfo = findByIsbn(isbn);
		if(null == bookInfo)return false;	//û���Ȿ��
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return false;
		if(outCount > Integer.parseInt(bookInfo.getCount())){
			System.out.println("��治�㣡");
			//������������ʵ�ʿ����
			return false;
		}
		int count = Integer.parseInt(bookInfo.getCount()) - outCount;//������ʵ�ʿ������ע����ݲ���������
		bookInfo.setCurCount(Integer.toString(count));	//�޸�ͼ����Ϣ
		bookInfoMap.put(isbn, bookInfo);	//���޸ĺ��ͼ����Ϣ�Żؼ�����
		FileUtil.SaveBookInfoMap(bookInfoMap);	//д���ļ�
		return true;
	}

	@Override
	public boolean inStore(String isbn, int inCount) {
		BookInfo bookInfo = findByIsbn(isbn);
		if(null == bookInfo)return false;	//û���Ȿ��
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return false;
		if(!bookInfoMap.containsKey(isbn))return false;	//ֻ�ܶԼ����д��ڵ�ͼ����в���
		int count = Integer.parseInt(bookInfo.getCurCount()) + inCount;
		bookInfo.setCurCount(Integer.toString(count));	//�޸�ͼ����Ϣ
		bookInfo.setCount(Integer.toString(count));
		bookInfoMap.put(isbn, bookInfo);	//���޸ĺ��ͼ����Ϣ�Żؼ�����
		FileUtil.SaveBookInfoMap(bookInfoMap);	//д���ļ�
		return true;
	}

	@Override
	public BookInfo findByIsbn(String isbn) {
		if(!isbn.matches("[0-9]{8}"))return null;
		Map<String, BookInfo> bookInfoMap = findAll();
		if(null == bookInfoMap)return null;
		if(!bookInfoMap.containsKey(isbn)){
			return null;	//������
		}
		return bookInfoMap.get(isbn);
	}
}
