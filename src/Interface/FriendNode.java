package Interface;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
 
public class FriendNode extends DefaultMutableTreeNode{
	
	protected ImageIcon icon;
	protected long userAccount;
	protected String NickName;
	protected long isOnline;
	public FriendNode()
	{
		
	}
	/**
	 * ֻ���������ǳƺ��Ƿ����ߵĽڵ㹹�캯��
	 * @param NickName
	 * @param isOnline
	 */
	public FriendNode(String NickName,long isOnline)
	{
		super();
		this.NickName=NickName;
		this.isOnline=isOnline;
	}
	/**
	 * ��������ͷ���ǳơ��Ƿ����ߵĽڵ㹹�캯��
	 * @param icon
	 * @param NickName
	 * @param isOnline
	 */
	public FriendNode(ImageIcon icon,String NickName,long isOnline,long userAccount)
	{
		super();
		this.icon=icon;
		this.NickName=NickName;
		this.isOnline=isOnline;
		this.userAccount=userAccount;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public long getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(long isOnline) {
		this.isOnline = isOnline;
	}
	public long getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(long userAccount) {
		this.userAccount = userAccount;
	}
	
}
