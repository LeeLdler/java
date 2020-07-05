package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class User implements Serializable {
	
	/** �û���� **/
	private int id;
	/** �û��˺� **/
	private int userAccount;
	/** �û��ǳ� **/
	private String nickName;
	/** �û�ǩ�� **/
	private String signature;
	/** �û�ͷ�� **/
	private String head;
	/** �û����� **/
	private String bir;
	/** �û��Ա� **/
	private String sex;
	/** ��� **/
	private int type;
	/** �Ƿ����� **/
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
	 * 1.��¼
	 * 2.�༭��Ϣ
	 * 3.����֪ͨ
	 * 4.����Ӻ����û�
	 * 7.xx���߻�����
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
