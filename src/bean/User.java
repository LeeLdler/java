package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class User implements Serializable {
	
	/** 用户编号 **/
	private int id;
	/** 用户账号 **/
	private int userAccount;
	/** 用户昵称 **/
	private String nickName;
	/** 用户签名 **/
	private String signature;
	/** 用户头像 **/
	private String head;
	/** 用户生日 **/
	private String bir;
	/** 用户性别 **/
	private String sex;
	/** 类别 **/
	private int type;
	/** 是否在线 **/
	private int online;
	private String pwd; 
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	/*
	 * 1.登录
	 * 2.编辑信息
	 * 3.下线通知
	 * 4.申请加好友用户
	 * 7.xx上线或下线
	 */
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public User()
	{
		
	}
	public String getBir() {
		return bir;
	}
	public void setBir(String bir) {
		this.bir = bir;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(int userAccount) {
		this.userAccount = userAccount;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	

}
