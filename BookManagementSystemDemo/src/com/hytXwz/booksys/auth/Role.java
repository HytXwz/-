package com.hytXwz.booksys.auth;
/**
 * 角色权限类
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月2日
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
	private String name;	//角色名称，显示给用户看的
	private String key;		//配置文件中的权限key
	private List<String> permissions;	//配置文件中的权限集合
	private BookInfoBiz bookInfoBiz;	//图书业务类
	private UserBiz userBiz;			//用户业务类
	private ReaderBiz readerBiz;		//读者业务类
	
	
	public Role(){
		setName("默认角色");
		setKey("default");
		getPermissions(key);
		//bookInfoBiz = new BookInfoBizImplV1();	//硬编码实现 - 这里为方便后面升级，用工厂模式来代替
		//工厂模式：来料加工  传给Factory一个字符串，工厂类根据字符串返回相应的子类实现
		//。。。。。。暂时没有好的办法
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
	 * 通过传入的参数key获得文件中存储的权限集合
	 * @param key
	 */
	private void getPermissions(String key) {
		//从配置文件中获得权限
		Properties props = new Properties();	
		try {
			props.load(Role.class.getResourceAsStream("Role_Permissions.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String strPermission = props.getProperty(key);	//得到权限字符串
		if(null == permissions) permissions = new ArrayList<>();
		String[] permissionArray = strPermission.split(",");
		for (String permission : permissionArray) {
			if("".equals(permission)) continue;	//如果读出来的权限字符串为空，继续读取下一个（没啥用）
			permissions.add(permission.trim());	//去掉两边可能有的空格
		}
	}
	
	/**
	 * 验证当前角色是否有权限执行传入的图书操作名（业务方法名）book
	 * @param optName	//传入的方法名
	 * @return
	 */
	public boolean checkBookPermission(String optName){
		if(null == permissions || "".equals(permissions)) return false;
		//1、administrator=bookinfobiz.add, bookinfobiz.findall, bookinfobiz.findbyisbn...
		//2、administrator=bookinfobiz.*, userbiz.*
		for(String permission : permissions){
			if(optName.equals(permission)) return true;	//有操作此方法的权限
			if(permission.equals("bookinfobiz.*")){
				return true;	//有操作错有BookInfoBiz方法的权限
			}
//			if(optName.equals("userbiz.*")) return true;
		}
		return false;
	}
	
	/**
	 * 验证当前角色是否有权限执行传入的用户操作名（业务方法名）user
	 * @param optName	//传入的方法名
	 * @return
	 */
	public boolean checkUserPermission(String optName){
		if(null == permissions || "".equals(permissions)) return false;
		//1、administrator=bookinfobiz.add, bookinfobiz.findall, bookinfobiz.findbyisbn...
		//2、administrator=bookinfobiz.*, userbiz.*
		for(String permission : permissions){
			if(optName.equals(permission)) return true;	//有操作此方法的权限
			if(permission.equals("userbiz.*")){
				return true;	//有操作错有BookInfoBiz方法的权限
			}
//			if(optName.equals("userbiz.*")) return true;
		}
		return false;
	}
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user){
		//验证当前角色是否有执行当前方法的权限
		if(checkUserPermission("userbiz.login") || checkUserPermission("bookinfobiz.login")){
			if(userBiz == null) userBiz = new UserBizImplV1();
			return userBiz.login(user);
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException(
					"\n" + user.getUserName() + "没有操作userBiz.login方法的权限"
					);
		}
	}
	
	/**
	 * 角色的入库方法，调用真正的图书业务类进行入库
	 * @param isbn
	 * @param inCount
	 * @return
	 */
	public boolean inStore(String isbn, int inCount){
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.inStore")){
			bookInfoBiz.inStore(isbn, inCount);
			return true;
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.inStore方法的权限");
		}
	}
	
	/**
	 * 图书出库
	 * @param isbn
	 * @param outCount
	 * @return
	 */
	public boolean outStore(String isbn, int outCount) {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.outStore")){
			if(bookInfoBiz.outStore(isbn, outCount))
				return true;
			return false;
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.outStore方法的权限");
		}
	}
	
	/**
	 * 添加图书
	 * @param bookInfo
	 * @return
	 */
	public boolean add(BookInfo bookInfo) {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.inStore")){
			bookInfoBiz.add(bookInfo);
			return true;
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.add方法的权限");
		}
	}
	
	/**
	 * 删除图书
	 * @param bookInfo
	 * @return
	 */
	public boolean delete(BookInfo bookInfo) {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.delete")){
			bookInfoBiz.delete(bookInfo);
			return true;
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.delete方法的权限");
		}
	}
	
	/**
	 * 修改图书信息
	 * @param bookInfo
	 * @return
	 */
	public void update(BookInfo bookInfo) {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.update")){
			BookInfo newBookInfo = bookInfoBiz.update(bookInfo);
			if(newBookInfo == null){
				System.out.println("修改失败！");
				return;
			}
			System.out.println("修改成功!");
			System.out.println("图书ISBN编号：" + newBookInfo.getIsbn() + "\n"
					+ "书名：" + newBookInfo.getBookName());
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.update方法的权限");
		}
	}
	
	/**
	 * 查看所有书籍
	 * @return
	 */
	public void findAll() {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.findAll") || checkUserPermission("userbiz.login")){
			if(bookInfoBiz == null) bookInfoBiz = new BookInfoBizImplV1();
			Map<String, BookInfo> bookInfoMap =  bookInfoBiz.findAll();
			System.out.println("书名\t\tisbn编号");
			//打印书名
			for (Entry<String, BookInfo> entry: bookInfoMap.entrySet()) {
				System.out.println(entry.getValue().getBookName() + "\t\t" + entry.getKey());
			}
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.findAll方法的权限");
		}
	}
	
	/**
	 * 通过isbn查找图书
	 * @param isbn
	 * @return
	 */
	public void findByIsbn(String isbn) {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.findByIsbn") || checkUserPermission("userbiz.login")){
			if(bookInfoBiz == null) bookInfoBiz = new BookInfoBizImplV1();
			BookInfo bookInfo = bookInfoBiz.findByIsbn(isbn);
			System.out.println("图书ISBN编号：" + bookInfo.getIsbn() + "\n"
					+ "书名：" + bookInfo.getBookName());
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.findByIsbn方法的权限");
		}
	}
	
	/**
	 * 借书
	 * @param reader
	 * @param bookInfo
	 * @return
	 */
	public boolean borrowBook(Reader reader, BookInfo bookInfo) {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.findAll") || checkUserPermission("userbiz.login")){
			readerBiz = new ReaderBizImplV1();
			if(readerBiz.borrowBook(reader, bookInfo)) return true;
			return false;
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.findAll方法的权限");
		}
	}

	public boolean returnBook(Reader reader, BookInfo bookInfo) {
		//验证当前角色是否有执行当前方法的权限
		if(checkBookPermission("bookinfobiz.findAll") || checkUserPermission("userbiz.login")){
			readerBiz = new ReaderBizImplV1();
			if(readerBiz.returnBook(reader, bookInfo)) return true;
			return false;
		}else{
			//这里可以自定义一个异常来抛出或者打印异常信息
			throw new NoSuchOptPermissionException("\n" + name + "没有操作BookInfoBiz.findAll方法的权限");
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







