package com.hytXwz.booksys.biz;

import com.hytXwz.booksys.biz.impl.BookInfoBizImplV1;
import com.hytXwz.booksys.biz.impl.UserBizImplV1;

/**
 * ҵ�񹤳���
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��2��
 * @remarks
 */
public class BizFactory {
	
	/**
	 * ͨ��ҵ�������ƣ������Ӧҵ�����ʵ��
	 * @param bizName
	 * @return
	 */
	public static Biz<?> getBiz(String bizName){
		switch (bizName.toLowerCase()) {
		case "administrator":	//��������Ա
			return new BookInfoBizImplV1();
		case "default":			//Ĭ�Ͻ�ɫ���볬��һ��
			return new BookInfoBizImplV1();
		case "operator":
			return new UserBizImplV1();
		default:
			return null;
		}
		
	}
	
	
}
