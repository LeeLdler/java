package Interface;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class GroupNodeRenderer extends DefaultTreeCellRenderer{

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
			boolean expanded,boolean leaf, int row, boolean hasFocus) 
	{
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		//��valueת��Ϊ�ڵ����
		GroupNode groupNode=(GroupNode)value;
		//�ӽڵ��ж�ȡͼƬ���ҽ�ͼƬ����Ӧ��С������
		ImageIcon icon = new ImageIcon("");
		//System.out.println(friendNode.getIcon());
		icon.setImage(icon.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		//�ӽڵ��ж�ȡ�ǳƺ��Ƿ�����
		String isOnline="����";
		String text="";
        
        //��Ϊ��Label���ı����ֲ��ܹ�ͨ��������Ӧ�ķ������л��У�
        //����ͨ��ʹ��html���﷨�����ֽ��л���
		if(groupNode.getType()==2){
			isOnline=groupNode.getAccount()+"��";
			text="<html>" + groupNode.getNickName() + "<br/>" + isOnline + " <html/>";
			icon=new ImageIcon("img//group.png");
			icon.setImage(icon.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		}
		else if(groupNode.getType()==1){
			text=groupNode.getNickName();
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
