package Interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import client.Client;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class tip {

	private static final int DO_NOTHING_ON_CLOSE = 0;
	public JFrame frame;
    private Client client;

	/**
	 * Create the application.
	 */
	public tip(String tip1,String tip2,Client client) 
	{
		this.client = client;
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		//设置图标
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
		frame.setIconImage(imageIcon.getImage());
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel(tip1);
		lblNewLabel.setBounds(76, 68, 343, 61);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("宋体",0,30));
		frame.getContentPane().add(lblNewLabel);
		
		
		JButton btnNewButton = new JButton(tip2);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.setVisible(false);
				if(tip2.equals("注册成功"))
				{
					client.setSign();
					client.closeRegist();
				}
				if(tip2.equals("创建成功")) {
					client.main.setGroupTree();
				}
			}
		});
		btnNewButton.setBounds(131, 177, 153, 34);
		btnNewButton.setFont(new Font("宋体",0,20));
		btnNewButton.setForeground(Color.RED);
		frame.getContentPane().add(btnNewButton);
		
		//背景板
		JPanel panel = new JPanel();
		ImageIcon img = new ImageIcon("img/111.jpg");
		img.setImage(img.getImage().getScaledInstance(450,300,Image.SCALE_DEFAULT ));
		JLabel imagLabel = new JLabel(img);
		panel.add(imagLabel);
		imagLabel.setBounds(176,5,-1,-1);
		panel.setBounds(0, 0, 450, 300);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	private void setDefaultCloseOperation(int hideOnClose) {
		// TODO Auto-generated method stub
		
	}
}
