package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.entity.BookInfo;

/**
 * ͼ��ҵ����Ľӿ�
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��1��
 * @remarks
 */
public interface BookInfoBiz extends Biz<BookInfo> {
	
	/**
	 * ����
	 * @param isbn		//����ͼ���isbn
	 * @param outCount	//��������
	 * @return			//�Ƿ����ɹ�
	 */
	public boolean outStore(String isbn, int outCount);
	
	/**
	 * ���
	 * @param isbn		//���ͼ���isbn
	 * @param inCount	//�������
	 * @return			//�Ƿ����ɹ�
	 */
	public boolean inStore(String isbn, int inCount);
	
	/**
	 * ͨ��isbn����ѯͼ����Ϣ
	 * @param isbn
	 * @return	���ز��ҵ���ͼ����Ϣ
	 */
	public BookInfo findByIsbn(String isbn);
	
}





