package Interface;

import java.awt.Component;
import java.awt.Image;
 
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
 
public class FriendNodeRenderer extends DefaultTreeCellRenderer{
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
			boolean expanded,boolean leaf, int row, boolean hasFocus) 
	{
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		//将value转化为节点对象
		FriendNode friendNode=(FriendNode)value;
		//从节点中读取图片并且将图片自适应大小、居中
		ImageIcon icon=new ImageIcon(friendNode.getIcon()+"");
		//System.out.println(friendNode.getIcon());
		icon.setImage(icon.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		//从节点中读取昵称和是否在线
		String isOnline="离线";
		String text="";
        
        //因为在Label中文本文字不能够通过调用相应的方法进行换行，
        //所以通过使用html的语法对文字进行换行
		if(friendNode.getIsOnline()==0)
		{
			isOnline="[离线]";
			text="<html>" + friendNode.getNickName() + "<br/>" + isOnline + " <html/>";
		} 
		else if(friendNode.getIsOnline()==1){
			isOnline="[在线]";
			text="<html>" + friendNode.getNickName() + "<br/>" + isOnline + " <html/>";
		}
		else if(friendNode.getIsOnline()==3){
			text=friendNode.getNickName();
		}
		
		//设置图片
		setIcon(icon);
		//设置文本
		setText(text);
		//设置图片和文本之间的距离
		setIconTextGap(15);
		return this;	
	}
}

