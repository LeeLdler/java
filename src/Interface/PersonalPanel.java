package Interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bean.Friends;
import bean.User;
import client.Client;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PersonalPanel {

	public JFrame frame;
	private Client client;
	private User user;
	private int type;
	/*
	 * 1.用户面板
	 * 2.加好友面板
	 * 3.好友面板
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//PersonalPanel window = new PersonalPanel();
				//	window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public PersonalPanel(Client client,User user,int type) {
		this.client = client;
		this.user = user;
		this.type = type;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("个人信息");
		frame.setBounds(100, 100, 412, 486);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // 设置标题栏的图标 
        frame.setIconImage(imageIcon.getImage());
        
		
		JLabel label = new JLabel("\u6635\u79F0");
		label.setBounds(62, 161, 72, 18);
		label.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel(user.getNickName());
		label_1.setBounds(148, 161, 72, 18);
		label_1.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label_1);
		
		JLabel lblNewLabel = new JLabel("\u751F\u65E5");
		lblNewLabel.setBounds(62, 219, 72, 18);
		lblNewLabel.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label_2 = new JLabel(user.getBir());
		label_2.setBounds(148, 219, 72, 18);
		label_2.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u5E74\u9F84");
		label_3.setFont(new Font("宋体",0,14));
		label_3.setBounds(62, 285, 72, 18);
		frame.getContentPane().add(label_3);
		
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		int yearInt = Integer.parseInt(year);
		String userBir = user.getBir().substring(0, 4);
		int birInt = Integer.parseInt(userBir);
		int old = yearInt - birInt;
		JLabel label_4 = new JLabel(old + "\u5C81");
		label_4.setBounds(148, 285, 72, 18);
		label_4.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label_4);
		
		//头像
		ImageIcon img = new ImageIcon("src//interface//"+user.getHead());
		img.setImage(img.getImage().getScaledInstance(99,100,Image.SCALE_DEFAULT ));
		JLabel imagLabel = new JLabel(img); 
		imagLabel.setBounds(121, 31, 99, 100);
		frame.getContentPane().add(imagLabel);
		
		JLabel label_5 = new JLabel("\u6027\u522B");
		label_5.setBounds(62, 350, 72, 18);
		label_5.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel(user.getSex());
		label_6.setBounds(148, 350, 72, 18);
		label_6.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label_6);
		
		if(type==1) {
		JLabel label_7 = new JLabel("\u7F16\u8F91\u8D44\u6599");
		label_7.setBounds(308, 13, 72, 18);
		label_7.setFont(new Font("宋体",0,15));
		frame.getContentPane().add(label_7);
		label_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				client.closePersonal();
				client.setEdite(user);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				label_7.setForeground(new Color(100,149,238));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_7.setForeground(new Color(0,0,0));
			}
		});
		}
		JLabel label_8 = new JLabel(""+user.getUserAccount());
		label_8.setBounds(308, 405, 72, 18);
		label_8.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label_8);
		
		JLabel label_9 = new JLabel("\u8D26\u53F7");
		label_9.setBounds(249, 405, 45, 18);
		label_9.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label_9);
		
		String online;
		if(user.getOnline() == 1)
			online = "在线";
		else
			online = "离线";
		JLabel label_12 = new JLabel(online);
		label_12.setFont(new Font("宋体",0,14));
		label_12.setBounds(249, 161, 72, 18);
		frame.getContentPane().add(label_12);
		
		JLabel lblNewLabel_1 = new JLabel(user.getSignature());
		lblNewLabel_1.setBounds(14, 402, 206, 24);
		lblNewLabel_1.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(lblNewLabel_1);
		
		if(type==2) {
		JButton button = new JButton("\u786E\u8BA4\u6DFB\u52A0");
		button.setFont(new Font("宋体",0,14));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(user.getOnline() == 1) {
				Friends msg = new Friends(client.getUser().getId(),user.getId(),1);
				msg.setMyName(client.getUser().getNickName());
				client.getMessage(msg);
				}
				else {
					JOptionPane.showMessageDialog(frame, "对方离线不能发送请求，太麻烦");
				}
			}
		});
		button.setBounds(249, 281, 113, 27);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.closePersonal();
			}
		});
		button_1.setFont(new Font("宋体",0,14));
		button_1.setBounds(249, 334, 113, 27);
		frame.getContentPane().add(button_1);
		}
		
		
		//设置背景板
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 394, 439);
		ImageIcon img1 = new ImageIcon("img/Person.jpg");
		img1.setImage(img1.getImage().getScaledInstance(394,439,Image.SCALE_DEFAULT ));
		
		JLabel imagLabel1 = new JLabel(img1); 
		imagLabel1.setBounds(0,0,img1.getIconWidth(),img1.getIconHeight());
		panel1.add(imagLabel1);
		frame.getContentPane().add(panel1);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
}
