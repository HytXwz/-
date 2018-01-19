package com.hytXwz.booksys.entity;

import java.io.Serializable;
import java.sql.Date;

import com.hytXwz.booksys.auth.Role;

/**
 * �û����󣬰�����ɫ
 * @author �ӈ�ͷС����
 * @version 1.0
 * @date 2018��1��4��
 * @remarks
 */
public class User implements Serializable{

	private static final long serialVersionUID = -507996627144171577L;
	
	private String userName;
	private String passWord;
	private Role role;
	private Date lastLoginDate;
	
	public User(){
		role = new Role();	//Ĭ�Ͻ�ɫdefault
	}
	
	public User(Role role){
		if(null == role) role = new Role();
		setRole(role);		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)return false;
		if(!(obj instanceof User)) return false;
		User user = (User)obj;
		return userName.equals(user.getUserName()) && passWord.equals(user.getPassWord());
	}
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	
	

}








