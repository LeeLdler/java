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
		//��valueת��Ϊ�ڵ����
		FriendNode friendNode=(FriendNode)value;
		//�ӽڵ��ж�ȡͼƬ���ҽ�ͼƬ����Ӧ��С������
		ImageIcon icon=new ImageIcon(friendNode.getIcon()+"");
		//System.out.println(friendNode.getIcon());
		icon.setImage(icon.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		//�ӽڵ��ж�ȡ�ǳƺ��Ƿ�����
		String isOnline="����";
		String text="";
        
        //��Ϊ��Label���ı����ֲ��ܹ�ͨ��������Ӧ�ķ������л��У�
        //����ͨ��ʹ��html���﷨�����ֽ��л���
		if(friendNode.getIsOnline()==0)
		{
			isOnline="[����]";
			text="<html>" + friendNode.getNickName() + "<br/>" + isOnline + " <html/>";
		} 
		else if(friendNode.getIsOnline()==1){
			isOnline="[����]";
			text="<html>" + friendNode.getNickName() + "<br/>" + isOnline + " <html/>";
		}
		else if(friendNode.getIsOnline()==3){
			text=friendNode.getNickName();
		}
		
		//����ͼƬ
		setIcon(icon);
		//�����ı�
		setText(text);
		//����ͼƬ���ı�֮��ľ���
		setIconTextGap(15);
		return this;	
	}
}

