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
 * �ļ�������
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��1��
 * @remarks
 */
public class FileUtil {
	//�������У�Ϊ�˲������㣬���ǰ�ͼ����Ϣ�Զ������ķ�ʽ������ļ���
	private static final String BookInfoFilePath = "BookInfo.dat";	//���ݱ�����Ĺ��̸�Ŀ¼
	private static final String UserInfoFilePath = "UserInfo.dat";
	private static final String ReaderInfoFilePath = "ReaderInfo.dat";
	
	/**
	 * ���������Ϣ
	 * @param reader
	 */
	public static void saveReader(Set<Reader> readerSet) {//������Ϣ����
		SaveObject(readerSet, ReaderInfoFilePath);
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<Reader> readReader() {
		Object obj = ReadObject(ReaderInfoFilePath);
		if(null == obj)return null;
		return (Set<Reader>)obj;
	}
	
	/**
	 * �����û�����
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
	 * ����ͼ�����ķ���
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
	 * ͨ�õı������ķ���
	 * @param object		Ҫд���ļ������л�����
	 * @param filePath		�ļ�·��
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
	 * ���ļ��ж�ȡ�����ͨ�÷���
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










