package com.hytXwz.booksys.auth;
/**
 * ��ɫȨ����
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��2��
 * @remarks
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.hytXwz.booksys.biz.BizFactory;
import com.hytXwz.booksys.biz.BookInfoBiz;
import com.hytXwz.booksys.biz.ReaderBiz;
import com.hytXwz.booksys.biz.UserBiz;
import com.hytXwz.booksys.biz.impl.BookInfoBizImplV1;
import com.hytXwz.booksys.biz.impl.ReaderBizImplV1;
import com.hytXwz.booksys.biz.impl.UserBizImplV1;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;
import com.hytXwz.booksys.entity.User;
import com.hytXwz.booksys.expection.NoSuchOptPermissionException;

public class Role implements Serializable{
	
	private static final long serialVersionUID = -3602069606877456288L;
	private String name;	//��ɫ���ƣ���ʾ���û�����
	private String key;		//�����ļ��е�Ȩ��key
	private List<String> permissions;	//�����ļ��е�Ȩ�޼���
	private BookInfoBiz bookInfoBiz;	//ͼ��ҵ����
	private UserBiz userBiz;			//�û�ҵ����
	private ReaderBiz readerBiz;		//����ҵ����
	
	
	public Role(){
		setName("Ĭ�Ͻ�ɫ");
		setKey("default");
		getPermissions(key);
		//bookInfoBiz = new BookInfoBizImplV1();	//Ӳ����ʵ�� - ����Ϊ��������������ù���ģʽ������
		//����ģʽ�����ϼӹ�  ����Factoryһ���ַ���������������ַ���������Ӧ������ʵ��
		//��������������ʱû�кõİ취
		if(key.equals("administrator") || key.equals("default")){
			bookInfoBiz = (BookInfoBiz)BizFactory.getBiz(key);
		}else{
			userBiz = (UserBiz)BizFactory.getBiz(key);
		}
	}
	
	public Role(String name, String key){
		setName(name);
		setKey(key);
		getPermissions(key);
		if(key.equals("administrator") || key.equals("default")){
			bookInfoBiz = (BookInfoBiz)BizFactory.getBiz(key);
		}else{
			userBiz = (UserBiz)BizFactory.getBiz(key);
		}
	}

	/**
	 * ͨ������Ĳ���key����ļ��д洢��Ȩ�޼���
	 * @param key
	 */
	private void getPermissions(String key) {
		//�������ļ��л��Ȩ��
		Properties props = new Properties();	
		try {
			props.load(Role.class.getResourceAsStream("Role_Permissions.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String strPermission = props.getProperty(key);	//�õ�Ȩ���ַ���
		if(null == permissions) permissions = new ArrayList<>();
		String[] permissionArray = strPermission.split(",");
		for (String permission : permissionArray) {
			if("".equals(permission)) continue;	//�����������Ȩ���ַ���Ϊ�գ�������ȡ��һ����ûɶ�ã�
			permissions.add(permission.trim());	//ȥ�����߿����еĿո�
		}
	}
	
	/**
	 * ��֤��ǰ��ɫ�Ƿ���Ȩ��ִ�д����ͼ���������ҵ�񷽷�����book
	 * @param optName	//����ķ�����
	 * @return
	 */
	public boolean checkBookPermission(String optName){
		if(null == permissions || "".equals(permissions)) return false;
		//1��administrator=bookinfobiz.add, bookinfobiz.findall, bookinfobiz.findbyisbn...
		//2��administrator=bookinfobiz.*, userbiz.*
		for(String permission : permissions){
			if(optName.equals(permission)) return true;	//�в����˷�����Ȩ��
			if(permission.equals("bookinfobiz.*")){
				return true;	//�в�������BookInfoBiz������Ȩ��
			}
//			if(optName.equals("userbiz.*")) return true;
		}
		return false;
	}
	
	/**
	 * ��֤��ǰ��ɫ�Ƿ���Ȩ��ִ�д�����û���������ҵ�񷽷�����user
	 * @param optName	//����ķ�����
	 * @return
	 */
	public boolean checkUserPermission(String optName){
		if(null == permissions || "".equals(permissions)) return false;
		//1��administrator=bookinfobiz.add, bookinfobiz.findall, bookinfobiz.findbyisbn...
		//2��administrator=bookinfobiz.*, userbiz.*
		for(String permission : permissions){
			if(optName.equals(permission)) return true;	//�в����˷�����Ȩ��
			if(permission.equals("userbiz.*")){
				return true;	//�в�������BookInfoBiz������Ȩ��
			}
//			if(optName.equals("userbiz.*")) return true;
		}
		return false;
	}
	/**
	 * �û���¼
	 * @param user
	 * @return
	 */
	public User login(User user){
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkUserPermission("userbiz.login") || checkUserPermission("bookinfobiz.login")){
			if(userBiz == null) userBiz = new UserBizImplV1();
			return userBiz.login(user);
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException(
					"\n" + user.getUserName() + "û�в���userBiz.login������Ȩ��"
					);
		}
	}
	
	/**
	 * ��ɫ����ⷽ��������������ͼ��ҵ����������
	 * @param isbn
	 * @param inCount
	 * @return
	 */
	public boolean inStore(String isbn, int inCount){
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.inStore")){
			bookInfoBiz.inStore(isbn, inCount);
			return true;
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.inStore������Ȩ��");
		}
	}
	
	/**
	 * ͼ�����
	 * @param isbn
	 * @param outCount
	 * @return
	 */
	public boolean outStore(String isbn, int outCount) {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.outStore")){
			if(bookInfoBiz.outStore(isbn, outCount))
				return true;
			return false;
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.outStore������Ȩ��");
		}
	}
	
	/**
	 * ���ͼ��
	 * @param bookInfo
	 * @return
	 */
	public boolean add(BookInfo bookInfo) {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.inStore")){
			bookInfoBiz.add(bookInfo);
			return true;
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.add������Ȩ��");
		}
	}
	
	/**
	 * ɾ��ͼ��
	 * @param bookInfo
	 * @return
	 */
	public boolean delete(BookInfo bookInfo) {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.delete")){
			bookInfoBiz.delete(bookInfo);
			return true;
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.delete������Ȩ��");
		}
	}
	
	/**
	 * �޸�ͼ����Ϣ
	 * @param bookInfo
	 * @return
	 */
	public void update(BookInfo bookInfo) {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.update")){
			BookInfo newBookInfo = bookInfoBiz.update(bookInfo);
			if(newBookInfo == null){
				System.out.println("�޸�ʧ�ܣ�");
				return;
			}
			System.out.println("�޸ĳɹ�!");
			System.out.println("ͼ��ISBN��ţ�" + newBookInfo.getIsbn() + "\n"
					+ "������" + newBookInfo.getBookName());
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.update������Ȩ��");
		}
	}
	
	/**
	 * �鿴�����鼮
	 * @return
	 */
	public void findAll() {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.findAll") || checkUserPermission("userbiz.login")){
			if(bookInfoBiz == null) bookInfoBiz = new BookInfoBizImplV1();
			Map<String, BookInfo> bookInfoMap =  bookInfoBiz.findAll();
			System.out.println("����\t\tisbn���");
			//��ӡ����
			for (Entry<String, BookInfo> entry: bookInfoMap.entrySet()) {
				System.out.println(entry.getValue().getBookName() + "\t\t" + entry.getKey());
			}
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.findAll������Ȩ��");
		}
	}
	
	/**
	 * ͨ��isbn����ͼ��
	 * @param isbn
	 * @return
	 */
	public void findByIsbn(String isbn) {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.findByIsbn") || checkUserPermission("userbiz.login")){
			if(bookInfoBiz == null) bookInfoBiz = new BookInfoBizImplV1();
			BookInfo bookInfo = bookInfoBiz.findByIsbn(isbn);
			System.out.println("ͼ��ISBN��ţ�" + bookInfo.getIsbn() + "\n"
					+ "������" + bookInfo.getBookName());
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.findByIsbn������Ȩ��");
		}
	}
	
	/**
	 * ����
	 * @param reader
	 * @param bookInfo
	 * @return
	 */
	public boolean borrowBook(Reader reader, BookInfo bookInfo) {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.findAll") || checkUserPermission("userbiz.login")){
			readerBiz = new ReaderBizImplV1();
			if(readerBiz.borrowBook(reader, bookInfo)) return true;
			return false;
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.findAll������Ȩ��");
		}
	}

	public boolean returnBook(Reader reader, BookInfo bookInfo) {
		//��֤��ǰ��ɫ�Ƿ���ִ�е�ǰ������Ȩ��
		if(checkBookPermission("bookinfobiz.findAll") || checkUserPermission("userbiz.login")){
			readerBiz = new ReaderBizImplV1();
			if(readerBiz.returnBook(reader, bookInfo)) return true;
			return false;
		}else{
			//��������Զ���һ���쳣���׳����ߴ�ӡ�쳣��Ϣ
			throw new NoSuchOptPermissionException("\n" + name + "û�в���BookInfoBiz.findAll������Ȩ��");
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<String> permission) {
		this.permissions = permission;
	}
	public BookInfoBiz getBookInfoBiz() {
		return bookInfoBiz;
	}
	public void setBookInfoBiz(BookInfoBiz bookInfoBiz) {
		this.bookInfoBiz = bookInfoBiz;
	}
	
	public UserBiz getUserBiz() {
		return userBiz;
	}

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}
	
	public ReaderBiz getReaderBiz() {
		return readerBiz;
	}

	public void setReaderBiz(ReaderBiz readerBiz) {
		this.readerBiz = readerBiz;
	}

}







