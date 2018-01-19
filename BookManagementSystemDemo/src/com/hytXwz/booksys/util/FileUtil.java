package com.hytXwz.booksys.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;
import com.hytXwz.booksys.entity.User;


/**
 * 文件工具类
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月1日
 * @remarks
 */
public class FileUtil {
	//本程序中，为了操作方便，我们把图书信息以对象流的方式存放在文件中
	private static final String BookInfoFilePath = "BookInfo.dat";	//根据本程序的工程根目录
	private static final String UserInfoFilePath = "UserInfo.dat";
	private static final String ReaderInfoFilePath = "ReaderInfo.dat";
	
	/**
	 * 保存读者信息
	 * @param reader
	 */
	public static void saveReader(Set<Reader> readerSet) {//读者信息保存
		SaveObject(readerSet, ReaderInfoFilePath);
	}
	
	/**
	 * 读取读者信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<Reader> readReader() {
		Object obj = ReadObject(ReaderInfoFilePath);
		if(null == obj)return null;
		return (Set<Reader>)obj;
	}
	
	/**
	 * 保存用户对象
	 * @param userSet
	 */
	public static void saveUserInfo(Set<User> userSet){
		SaveObject(userSet, UserInfoFilePath);
	}
	
	@SuppressWarnings("unchecked")
	public static Set<User> readUserInfo(){
		Object obj = ReadObject(UserInfoFilePath);
		if(null == obj)return null;
		return (Set<User>)obj;
	}
	
	
	/**
	 * 保存图书对象的方法
	 * @param bookInfoMap
	 */
	public static void SaveBookInfoMap(Map<String, BookInfo> bookInfoMap){
		SaveObject(bookInfoMap, BookInfoFilePath);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, BookInfo> ReadBookInfoMap(){
		Object obj = ReadObject(BookInfoFilePath);
		if(null == obj)return null;
		return (Map<String, BookInfo>)obj;
	}
	
	/**
	 * 通用的保存对象的方法
	 * @param object		要写入文件的序列化对象
	 * @param filePath		文件路径
	 * @return
	 */
	public static boolean SaveObject(Object object, String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try (
			OutputStream output = new FileOutputStream(file,false);
			ObjectOutputStream out = new ObjectOutputStream(output);
		){
			out.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 从文件中读取对象的通用方法
	 * @param filePath
	 * @return
	 */
	public static Object ReadObject(String filePath){
		Object obj = null;
		if(!new File(filePath).exists())return null;
		try (
			InputStream input = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(input);
		){
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}










