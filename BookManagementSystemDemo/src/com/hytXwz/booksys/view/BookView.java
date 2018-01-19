package com.hytXwz.booksys.view;

import java.util.Scanner;
import java.util.Set;

import com.hytXwz.booksys.auth.Role;
import com.hytXwz.booksys.entity.BookInfo;
import com.hytXwz.booksys.entity.Reader;
import com.hytXwz.booksys.entity.User;
import com.hytXwz.booksys.util.FileUtil;

/**
 * ͼ�����ϵͳ����������ͼ
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��3��
 * @remarks		//����������ͼ����û��ʵ�֡����黹�鷽��û��ʵ��
 */
public class BookView {
	
	private Scanner input = null;
	private User loginedUser = null;	//��ǰ�ѵ�¼��User
	private Role role = null;		//��ǰ�û������Ӧ�Ľ�ɫ����������ҵ�񷽷�
	private Reader reader;
	
	
	public BookView(){
		input = new Scanner(System.in);
		showWelcome();
	}
	
	/**
	 * ��ʾ��ӭ����
	 */
	public void showWelcome(){
		System.out.println("========= ��ӭ����SEͼ�����ϵͳ   =========");
		System.out.println("1����¼  2��ע��  3���˳�");
		System.out.print("��ѡ��");
		String choice = input.next();
		switch (choice) {
		case "1":
			showRoleView();
			break;
		case "2":
			if(registeredUser()){
				System.out.println("ע��ɹ��������µ�¼��");
				//����ѡ��1
				showRoleView();
			}else{
				showWelcome();
			}
		default:
			System.out.println("ϵͳ�ѳɹ��˳���");
		}
	}
	
	/**
	 * ���ݵ�¼�Ľ�ɫ��ʾ���ܲ˵�
	 */
	private void showRoleView() {
		//��֤�û����˺����룬���ݸ��˺ŵĽ�ɫ��ʾ�˵�
		showLoginView();
		showMAinView();
	}

	/**
	 * ��ʾ��¼�˵�
	 * @return
	 */
	public User showLoginView(){
		User loginUser = new User();
		System.out.print("�������û�����");
		loginUser.setUserName(input.next());
		System.out.print("���������룺");
		loginUser.setPassWord(input.next());
		//��Ҫͨ��ҵ�񷽷�����֤��ǰ¼��ģ��û������û����������Ƿ�Ϸ�
		//����Ϸ�������ǰ��¼�û����浽this.loginUser�У������õ�ǰ��¼�û��Ľ�ɫ��һ����ʾ��Ӧ�Ĳ˵��͵�����Ӧ�ķ���
		this.loginedUser = loginUser.getRole().login(loginUser);
		if(loginedUser == null){
			//��¼ʧ��
			System.out.println("��¼ʧ�ܣ��������˵���");
			showWelcome();
			return null;
		}else{
			//��½�ɹ�������ǰ��¼�û����浽this.loginUser�У������õ�ǰ��¼�û��Ľ�ɫ��һ����ʾ��Ӧ�Ĳ˵��͵�����Ӧ�ķ���
//			this.loginedUser = loginUser;
			this.role = this.loginedUser.getRole();
			return this.loginedUser;
		}
		
	}
	
	/**
	 * �����û�ѡ����в�ͬ����
	 * @param choice
	 */
	public void doMainView(String choice){
		switch(choice){
		case "1":	//����ͼ��
			showAddView();
			showMAinView();
			break;
		case "2":	//ɾ��
			showDeleteView();
			showMAinView();
			break;
		case "3":	//�鿴����ͼ��
			showFindAll();
			showMAinView();
			break;
		case "4":	//ͨ��isbn����ͼ��
			showFindByIsbn();
			showMAinView();
			break;
		case "5":	//�޸�ͼ����Ϣ
			showUpdateView();
			showMAinView();
			break;
		case "6":	//���
			showInStore();
			showMAinView();
			break;
		case "7":	//����
			showOutStore();
			showMAinView();
			break;
		case "8":	//����
			showBorrowView();
			showMAinView();
			break;
		case "9":	//����
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
	 * ����
	 */
	private void showBorrowView(){
		System.out.println("������Ҫ�����isbn��");
		String isbn = input.next();
		BookInfo bookInfo = new BookInfo(isbn);
		getReader();
		role.borrowBook(this.reader, bookInfo);
	}
	
	/**
	 * ����
	 */
	private void showReturnView(){
		System.out.println("������Ҫ�����isbn��");
		String isbn = input.next();
		BookInfo bookInfo = new BookInfo(isbn);
		getReader();
		role.returnBook(this.reader, bookInfo);
	}
	
	/**
	 * �õ�������Ϣ
	 */
	public void getReader() {
		System.out.println("���������ID��");
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
			System.out.println("û���ҵ���ID!");
	}

	/**
	 * �½�������Ϣ
	 */
	public void setReader() {//������Ϣ����
		System.out.println("���������������");
		String readerName = input.next();
		System.out.println("���������ID��");
		String readerId = input.next();
		this.reader = new Reader(readerName, readerId);
		Set<Reader> readerSet = FileUtil.readReader();
		readerSet.add(new Reader(readerName, readerId));
		FileUtil.saveReader(readerSet);
		System.out.println("�½�������Ϣ�ɹ���");
	}
	
	private void showUpdateView() {
		BookInfo bookInfo = new BookInfo();
		System.out.print("������Ҫ�޸�ͼ���isbn:");
		if(bookInfo.setIsbn(input.next())){
			System.out.print("�����������ͼ���������");
			if(bookInfo.setBookName(input.next())){
				System.out.print("�����������ͼ������ߣ�");
				bookInfo.setAuthor(input.next());
			}
		}
		this.role.update(bookInfo);
	}

	private void showAddView() {
		BookInfo bookInfo = new BookInfo();
		System.out.print("�����������ͼ���isbn:");
		if(bookInfo.setIsbn(input.next())){
			System.out.print("�����������ͼ���������");
			bookInfo.setBookName(input.next());
			System.out.print("�����������ͼ������ߣ�");
			bookInfo.setAuthor(input.next());
			System.out.println("�����������ͼ���������");
			String count = input.next();
			bookInfo.setCurCount(count);
			bookInfo.setCount(count);
		}
		
		if(this.role.add(bookInfo)){
			System.out.println("ͼ����ӳɹ���");
		}else{
			System.out.println("ͼ�����ʧ�ܣ�");
		}
	}

	private void showFindAll() {
		this.role.findAll();
	}

	private void showFindByIsbn() {
		System.out.print("������Ҫ�鿴��ͼ��isbn��");
		String isbn =input.next();
		this.role.findByIsbn(isbn);
	}

	private void showInStore() {
		System.out.println("������Ҫ����ͼ��isbn��");
		String isbn_in =input.next();
		System.out.println("������Ҫ����ͼ��������");
		int inCount = input.nextInt();
		if(this.role.inStore(isbn_in, inCount)){
			System.out.println("���ɹ���");
		}else{
			System.out.println("���ʧ�ܣ�");
		}
	}

	private void showOutStore() {
		System.out.println("������Ҫ�����ͼ��isbn��");
		String isbn_out =input.next();
		System.out.println("������Ҫ�����ͼ��������");
		int outCount = input.nextInt();
		if(this.role.outStore(isbn_out, outCount)){
			System.out.println("����ɹ�������������Ϣ���ٴγ��ԣ�");
		}else{
			System.out.println("����ʧ�ܣ�");
		}
	}

	private void showDeleteView() {
		System.out.println("������Ҫɾ����ͼ���isbn��");
		BookInfo bookInfo = new BookInfo(input.next());
		if(this.role.delete(bookInfo)){
			System.out.println("ɾ���ɹ���");
		}else{
			System.out.println("ɾ��ʧ�ܣ�");
		}
	}
	
	/**
	 * ��ʾ����Ա���˵�
	 */
	public void showMAinView(){
		System.out.println("\nSEͼ�����ϵͳ�������˵�");
		System.out.println("1������ͼ��");
		System.out.println("2��ɾ��ͼ��");
		System.out.println("3���鿴����ͼ��");
		System.out.println("4��������Ų�ѯ");
		System.out.println("5���޸�ͼ����Ϣ");
		System.out.println("6�����");
		System.out.println("7������");
		System.out.println("8������");
		System.out.println("9������");
		System.out.println("0���½�������Ϣ");
		System.out.println(".�����ص�¼����..");
		System.out.print("��ѡ��");
		String choice = input.next();
		doMainView(choice);
	}
	/**
	 * ע�����û�
	 * @return
	 */
	private boolean registeredUser() {
		//ע�����û�
		System.out.println("����������˺�����key����");
		String key = input.next();
		System.out.println("���������룺");
		String password = input.next();
		System.out.println("��ѡ�������ݣ�");
		System.out.println("1����������Ա 2������Ա");
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
			//�������˵�
			showWelcome();
		}
		return false;
	}
	
//	/**
//	 * ע���û�ʱ���û���Ϣ����
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






