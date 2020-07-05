package Interface;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class GroupNode extends DefaultMutableTreeNode{
	protected long groupAccount;
	protected long account;
	protected String NickName;
	protected int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public GroupNode(String NickName,int type)
	{
		super();
		this.NickName=NickName;
		this.type = type;
	}
	public GroupNode(String NickName,long groupAccount,long account,int type)
	{
		super();
		this.NickName=NickName;
		this.groupAccount=groupAccount;
		this.account=account;
		this.type = type;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public long getGroupAccount() {
		return groupAccount;
	}
	public void setGroupAccount(long groupAccount) {
		this.groupAccount = groupAccount;
	}
	public long getAccount() {
		return account;
	}
	public void setAccount(long account) {
		this.account = account;
	}

}
