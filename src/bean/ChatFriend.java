package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class ChatFriend implements Serializable{
		private int friendId;
		private int friendAccount;
		private int myId;
		private int myAccount;
		private String friendHead;
		private String friendSignature;
		private Vector<Message> message;
		private String friendNickName;
		private String myNickName;
		private Message currentMessage;
		private int type;
		private Vector<Message> offOnline;
		private List<User> groupUser;
		private List<FileMessage> file;
		public List<FileMessage> getFile() {
			return file;
		}
		public void setFile(List<FileMessage> file) {
			this.file = file;
		}
		private boolean isGroup;
		public boolean isGroup() {
			return isGroup;
		}
		public void setGroup(boolean isGroup) {
			this.isGroup = isGroup;
		}
		public int getFriendId() {
			return friendId;
		}
		public void setFriendId(int friendId) {
			this.friendId = friendId;
		}
		public int getFriendAccount() {
			return friendAccount;
		}
		public void setFriendAccount(int friendAccount) {
			this.friendAccount = friendAccount;
		}
		public int getMyId() {
			return myId;
		}
		public void setMyId(int myId) {
			this.myId = myId;
		}
		public int getMyAccount() {
			return myAccount;
		}
		public void setMyAccount(int myAccount) {
			this.myAccount = myAccount;
		}
		public String getFriendHead() {
			return friendHead;
		}
		public void setFriendHead(String friendHead) {
			this.friendHead = friendHead;
		}
		public String getFriendSignature() {
			return friendSignature;
		}
		public void setFriendSignature(String friendSignature) {
			this.friendSignature = friendSignature;
		}
		public Vector<Message> getMessage() {
			return message;
		}
		public void setMessage(Vector<Message> message) {
			this.message = message;
		}
		public String getFriendNickName() {
			return friendNickName;
		}
		public void setFriendNickName(String friendNickName) {
			this.friendNickName = friendNickName;
		}
		public String getMyNickName() {
			return myNickName;
		}
		public void setMyNickName(String myNickName) {
			this.myNickName = myNickName;
		}
		public Message getCurrentMessage() {
			return currentMessage;
		}
		public void setCurrentMessage(Message currentMessage) {
			this.currentMessage = currentMessage;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public Vector<Message> getOffOnline() {
			return offOnline;
		}
		public void setOffOnline(Vector<Message> offOnline) {
			this.offOnline = offOnline;
		}
		public List<User> getGroupUser() {
			return groupUser;
		}
		public void setGroupUser(List<User> groupUser) {
			this.groupUser = groupUser;
		}
		
}
