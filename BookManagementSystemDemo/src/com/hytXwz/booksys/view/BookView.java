package com.hytXwz.booksys.view;

import java.util.Scanner;
import java.util.Set;

import com.hytXwz.booksys.auth.Role;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;
import com.hytXwz.booksys.entity.User;
import com.hytXwz.booksys.util.FileUtil;

/**
 * 图书管理系统的主界面视图
 * @author 河堐头小王子
 * @version 1.0
 * @date 2018年1月3日
 * @remarks		//两个界面视图方法没有实现。借书还书方法没有实现
 */
public class BookView {
	
	private Scanner input = null;
	private User loginedUser = null;	//当前已登录的User
	private Role role = null;		//当前用户对象对应的角色，用来调用业务方法
	private Reader reader;
	
	
	public BookView(){
		input = new Scanner(System.in);
		showWelcome();
	}
	
	/**
	 * 显示欢迎界面
	 */
	public void showWelcome(){
		System.out.println("========= 欢迎来到SE图书管理系统   =========");
		System.out.println("1、登录  2、注册  3、退出");
		System.out.print("请选择：");
		String choice = input.next();
		switch (choice) {
		case "1":
			showRoleView();
			break;
		case "2":
			if(registeredUser()){
				System.out.println("注册成功！请重新登录：");
				//返回选项1
				showRoleView();
			}else{
				showWelcome();
			}
		default:
			System.out.println("系统已成功退出！");
		}
	}
	
	/**
	 * 根据登录的角色显示功能菜单
	 */
	private void showRoleView() {
		//验证用户的账号密码，根据该账号的角色显示菜单
		showLoginView();
		showMAinView();
	}

	/**
	 * 显示登录菜单
	 * @return
	 */
	public User showLoginView(){
		User loginUser = new User();
		System.out.print("请输入用户名：");
		loginUser.setUserName(input.next());
		System.out.print("请输入密码：");
		loginUser.setPassWord(input.next());
		//需要通过业务方法，验证当前录入的（用户对象）用户名跟密码是否合法
		//如果合法，将当前登录用户保存到this.loginUser中，在设置当前登录用户的角色，一边显示相应的菜单和调用相应的方法
		this.loginedUser = loginUser.getRole().login(loginUser);
		if(loginedUser == null){
			//登录失败
			System.out.println("登录失败！返回主菜单！");
			showWelcome();
			return null;
		}else{
			//登陆成功。将当前登录用户保存到this.loginUser中，在设置当前登录用户的角色，一边显示相应的菜单和调用相应的方法
//			this.loginedUser = loginUser;
			this.role = this.loginedUser.getRole();
			return this.loginedUser;
		}
		
	}
	
	/**
	 * 根据用户选择进行不同操作
	 * @param choice
	 */
	public void doMainView(String choice){
		switch(choice){
		case "1":	//新增图书
			showAddView();
			showMAinView();
			break;
		case "2":	//删除
			showDeleteView();
			showMAinView();
			break;
		case "3":	//查看所有图书
			showFindAll();
			showMAinView();
			break;
		case "4":	//通过isbn查找图书
			showFindByIsbn();
			showMAinView();
			break;
		case "5":	//修改图书信息
			showUpdateView();
			showMAinView();
			break;
		case "6":	//入库
			showInStore();
			showMAinView();
			break;
		case "7":	//出库
			showOutStore();
			showMAinView();
			break;
		case "8":	//借书
			showBorrowView();
			showMAinView();
			break;
		case "9":	//还书
			showReturnView();
			showMAinView();
			break;
		case "0":	
			setReader();
			showMAinView();
		default :	
			showWelcome();
		}
	}
	
	/**
	 * 借书
	 */
	private void showBorrowView(){
		System.out.println("请输入要借书的isbn：");
		String isbn = input.next();
		BookInfo bookInfo = new BookInfo(isbn);
		getReader();
		role.borrowBook(this.reader, bookInfo);
	}
	
	/**
	 * 还书
	 */
	private void showReturnView(){
		System.out.println("请输入要还书的isbn：");
		String isbn = input.next();
		BookInfo bookInfo = new BookInfo(isbn);
		getReader();
		role.returnBook(this.reader, bookInfo);
	}
	
	/**
	 * 得到读者信息
	 */
	public void getReader() {
		System.out.println("请输入读者ID：");
		String readerId = input.next();
		Set<Reader> readerSet = FileUtil.readReader();
//		Iterator<Reader> it = readerSet.iterator();
//		while(it.hasNext()){
//			Reader r = it.next();
//			if(r.getReaderId().equals(readerId))
//				this.reader = r;
//		}
		for (Reader r : readerSet) {
			if(r.getReaderId().equals(readerId))
				this.reader = r;
		}
		if(reader == null)
			System.out.println("没有找到该ID!");
	}

	/**
	 * 新建读者信息
	 */
	public void setReader() {//读者信息保存
		System.out.println("请输入读者姓名：");
		String readerName = input.next();
		System.out.println("请输入读者ID：");
		String readerId = input.next();
		this.reader = new Reader(readerName, readerId);
		Set<Reader> readerSet = FileUtil.readReader();
		readerSet.add(new Reader(readerName, readerId));
		FileUtil.saveReader(readerSet);
		System.out.println("新建读者信息成功！");
	}
	
	private void showUpdateView() {
		BookInfo bookInfo = new BookInfo();
		System.out.print("请输入要修改图书的isbn:");
		if(bookInfo.setIsbn(input.next())){
			System.out.print("请输入新添加图书的书名：");
			if(bookInfo.setBookName(input.next())){
				System.out.print("请输入新添加图书的作者：");
				bookInfo.setAuthor(input.next());
			}
		}
		this.role.update(bookInfo);
	}

	private void showAddView() {
		BookInfo bookInfo = new BookInfo();
		System.out.print("请输入新添加图书的isbn:");
		if(bookInfo.setIsbn(input.next())){
			System.out.print("请输入新添加图书的书名：");
			bookInfo.setBookName(input.next());
			System.out.print("请输入新添加图书的作者：");
			bookInfo.setAuthor(input.next());
			System.out.println("请输入新添加图书的数量：");
			String count = input.next();
			bookInfo.setCurCount(count);
			bookInfo.setCount(count);
		}
		
		if(this.role.add(bookInfo)){
			System.out.println("图书添加成功！");
		}else{
			System.out.println("图书添加失败！");
		}
	}

	private void showFindAll() {
		this.role.findAll();
	}

	private void showFindByIsbn() {
		System.out.print("请输入要查看的图书isbn：");
		String isbn =input.next();
		this.role.findByIsbn(isbn);
	}

	private void showInStore() {
		System.out.println("请输入要入库的图书isbn：");
		String isbn_in =input.next();
		System.out.println("请输入要入库的图书数量：");
		int inCount = input.nextInt();
		if(this.role.inStore(isbn_in, inCount)){
			System.out.println("入库成功！");
		}else{
			System.out.println("入库失败！");
		}
	}

	private void showOutStore() {
		System.out.println("请输入要出库的图书isbn：");
		String isbn_out =input.next();
		System.out.println("请输入要出库的图书数量：");
		int outCount = input.nextInt();
		if(this.role.outStore(isbn_out, outCount)){
			System.out.println("出库成功！请检查输入信息后再次尝试！");
		}else{
			System.out.println("出库失败！");
		}
	}

	private void showDeleteView() {
		System.out.println("请输入要删除的图书的isbn：");
		BookInfo bookInfo = new BookInfo(input.next());
		if(this.role.delete(bookInfo)){
			System.out.println("删除成功！");
		}else{
			System.out.println("删除失败！");
		}
	}
	
	/**
	 * 显示操作员主菜单
	 */
	public void showMAinView(){
		System.out.println("\nSE图书管理系统》》主菜单");
		System.out.println("1、新增图书");
		System.out.println("2、删除图书");
		System.out.println("3、查看所有图书");
		System.out.println("4、按出版号查询");
		System.out.println("5、修改图书信息");
		System.out.println("6、入库");
		System.out.println("7、出库");
		System.out.println("8、借书");
		System.out.println("9、还书");
		System.out.println("0、新建读者信息");
		System.out.println(".、返回登录界面..");
		System.out.print("请选择：");
		String choice = input.next();
		doMainView(choice);
	}
	/**
	 * 注册新用户
	 * @return
	 */
	private boolean registeredUser() {
		//注册新用户
		System.out.println("请输入你的账号名（key）：");
		String key = input.next();
		System.out.println("请输入密码：");
		String password = input.next();
		System.out.println("请选择你的身份：");
		System.out.println("1、超级管理员 2、操作员");
		String choice = input.next();
		switch (choice) {
		case "1":
			User user1 = new User(new Role(key, "administrator"));
			user1.setUserName(key);
			user1.setPassWord(password);
			Set<User> userSet1 = FileUtil.readUserInfo();
			userSet1.add(user1);
			FileUtil.saveUserInfo(userSet1);
			return true;
		case "2":
			User user2 = new User(new Role(key, "operator"));
			user2.setUserName(key);
			user2.setPassWord(password);
			Set<User> userSet2 = FileUtil.readUserInfo();
			userSet2.add(user2);
			FileUtil.saveUserInfo(userSet2);
			return true;
		default:
			//返回主菜单
			showWelcome();
		}
		return false;
	}
	
//	/**
//	 * 注册用户时将用户信息保存
//	 * @param key
//	 * @param permissions
//	 * @return
//	 */
//	private static boolean setPermissions(String key, String permissions) {
//		Properties props = new Properties();
//		props.setProperty(key, permissions);
//		try {
//			FileOutputStream out = new FileOutputStream("Role_Permissions.properties");
//			props.store(out, null);
//			return true;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	public User getLoginedUser() {
		return loginedUser;
	}

	public void setLoginedUser(User loginedUser) {
		this.loginedUser = loginedUser;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}






