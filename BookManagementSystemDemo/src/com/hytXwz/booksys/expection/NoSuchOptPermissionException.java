package com.hytXwz.booksys.expection;
/**
 * �Զ����쳣-- �˲���û��Ȩ��
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��2��
 * @remarks
 */
public class NoSuchOptPermissionException extends RuntimeException{

	private static final long serialVersionUID = -6573580484619569273L;
	
	public NoSuchOptPermissionException(){
		super();
		System.out.println("�˲���û��Ȩ���쳣");
	}

	public NoSuchOptPermissionException(String message){
		super(message);
	}
}
