package bean;

import java.io.Serializable;

public class Friends implements Serializable{
	private int myId;
	private int FriendId;
	private String myName;
	private int myAccount;
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	private int type;
	/*
	 * 1.发送请求
	 * 2.接收请求
	 * 3.删除好友
	 */
	public Friends(int myId,int FriendId,int type) {
		this.myId = myId;
		this.FriendId = FriendId;
		this.type = type;
		this.myAccount = myId + 3384;
	}
	public int getMyAccount() {
		return myAccount;
	}
	public void setMyAccount(int myAccount) {
		this.myAccount = myAccount;
	}
	public int getMyId() {
		return myId;
	}
	public void setMyId(int myId) {
		this.myId = myId;
	}
	public int getFriendId() {
		return FriendId;
	}
	public void setFriendId(int friendId) {
		FriendId = friendId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	

}
