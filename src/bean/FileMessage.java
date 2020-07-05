package bean;

import java.io.Serializable;
import java.util.List;

public class FileMessage implements Serializable{
	private byte[] b;
	private int type;
	private String name;
	private int friendId;
	private String friendName;
	private String date;
	private int myId;
	private String myName;
	private List<User> groupUser; 
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public byte[] getB() {
		return b;
	}
	public void setB(byte[] b) {
		this.b = b;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getMyId() {
		return myId;
	}
	public void setMyId(int myId) {
		this.myId = myId;
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public List<User> getGroupUser() {
		return groupUser;
	}
	public void setGroupUser(List<User> groupUser) {
		this.groupUser = groupUser;
	}
}
