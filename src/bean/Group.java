package bean;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable{
	private int id;
	private List<User> groupUser;
	private int account;
	private String name;
	private int createId;
	private int createAccount;
	private String createHead;
	private String createName;
	private List<FileMessage> file;
	public List<FileMessage> getFile() {
		return file;
	}
	public void setFile(List<FileMessage> file) {
		this.file = file;
	}
	public int getCreateAccount() {
		return createAccount;
	}
	public void setCreateAccount(int createAccount) {
		this.createAccount = createAccount;
	}
	public String getCreateHead() {
		return createHead;
	}
	public void setCreateHead(String createHead) {
		this.createHead = createHead;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<User> getGroupUser() {
		return groupUser;
	}
	public void setGroupUser(List<User> groupUser) {
		this.groupUser = groupUser;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCreateId() {
		return createId;
	}
	public void setCreateId(int createId) {
		this.createId = createId;
	}
}
