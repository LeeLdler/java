package bean;

import java.util.List;

public class UserCustom extends User{

	private List<User> friends;
	private List<Group> group;

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}
}
