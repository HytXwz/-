package com.hytXwz.booksys.biz;

/**
 * ����������ҵ���ͨ�ò���
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��1��
 * @remarks
 */

import java.io.Serializable;
import java.util.Map;

public interface Biz<T> extends Serializable{
	
	public boolean add(T t);
	
	public boolean delete(T t);
	
	public T update(T t);
	
	public T findById(String id);
	
	public Map<String, T> findAll();
}




