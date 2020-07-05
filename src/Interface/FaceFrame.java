package Interface;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;


public class FaceFrame extends JWindow
{

	private JPanel contentPane;

	GridLayout gridLayout1 = new GridLayout();
	public static JLabel[] ico = new JLabel[90];
	int width=720;
	int height=288;
	ChatWithFriend chat;
	public FaceFrame(ChatWithFriend chat)
	{
		super(chat);
		this.chat = chat;
		try
		{
			jbInit();
			int left=chat.getLocation().x-10+20;
			int top=chat.getLocation().y+250;
			this.setBounds(left, top, 450, 180);
			this.setAlwaysOnTop(true);
			this.setVisible(true);
			//chat.getContentPane().add(this);
		} catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	private void jbInit() throws Exception
	{
		getContentPane().setLayout(gridLayout1);
		gridLayout1.setColumns(15);
		gridLayout1.setHgap(1);
		gridLayout1.setRows(6);
		gridLayout1.setVgap(1);
		String fileName = " ";
		for (int i = 0; i < ico.length; i++)
		{
			String str;
			if(i<10)
        	{
				str = "f00" + i;
        		fileName= "img/face/"+str+".png";//修改图片路径 
        	}
        	else
        	{
        		str = "f0" + i;
        		fileName= "img/face/"+str+".png";
			}
			ImageIcon imge = new   ImageIcon(fileName);
			imge.setImage(imge.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT ));
			ico[i]=new   JLabel(imge);
			ico[i].setToolTipText(str+"点此添加QQ表情哟^_^");
			final Icon img = ico[i].getIcon();
			final int count=i;
			ico[i].addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					chat.input.append(str);
					//System.out.println(str);
					getObj().dispose();// 这里最好用单例模式
				}
			});
			this.getContentPane().add(ico[i]);
		}
		this.getContentPane().setBackground(SystemColor.text);
	}

	private JWindow getObj()
	{
		return this;
	}

}