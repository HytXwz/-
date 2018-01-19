package com.hytXwz.booksys.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ������
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��5��
 * @remarks
 */
public class Reader implements Serializable{

	private static final long serialVersionUID = 3650290733503293460L;
	
	private String readerName;
	private String readerId;
	private ArrayList<BookInfo> borrowBook;
	
	
	public Reader(){
		
	}
	
	public Reader(String readerName, String readerId){
		this.setReaderName(readerName);
		this.setReaderId(readerId);
		borrowBook = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		Reader reader = (Reader)obj;
		return this.getReaderId().equals(reader.getReaderId());
	}
	
	public String getReaderName() {
		return readerName;
	}
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	public String getReaderId() {
		return readerId;
	}
	public void setReaderId(String readerId) {
		if (readerId.matches("[0-9]{6}")) { // �ݶ�ΪbookIdλ6λ����
			this.readerId = readerId;
		}
	}
	
	public ArrayList<BookInfo> getBorrowBook() {
		return borrowBook;
	}

	public void setBorrowBook(ArrayList<BookInfo> borrowBook) {
		this.borrowBook = borrowBook;
	}

	
	
	
	
}








