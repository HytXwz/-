package com.hytXwz.booksys.biz;

/**
 * 定义了所有业务的通用操作
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月1日
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




