package Interface;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client.Client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class RetrievePassword {

	public JFrame frame;
	private JTextField textField_2;
	private JTextField textField_3;
	private Client client;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private String aut;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	RetrievePassword window = new RetrievePassword();
					//window.frame.setVisible(true);
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
	public RetrievePassword(Client client) {
		this.client = client;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 508, 391);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // 设置标题栏的图标 
        frame.setIconImage(imageIcon.getImage());
		
		JLabel label = new JLabel("\u9A8C\u8BC1\u7801");
		label.setBounds(55, 238, 72, 18);
		label.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(label);
		
		JButton button = new JButton("\u83B7\u53D6\u9A8C\u8BC1\u7801");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String account = textField_3.getText();
				String pwd = String.valueOf(passwordField.getPassword());
				String pwd1 = String.valueOf(passwordField_1.getPassword());
				if(account.equals("")) {
					JOptionPane.showMessageDialog(frame, "账号不能为空");
				}
				else if(pwd.length()<6) {
					JOptionPane.showMessageDialog(frame, "密码至少六位数");
				}
				else if(!pwd.equals(pwd1)) {
					JOptionPane.showMessageDialog(frame, "两次密码不一致");
				}
				else {
					String msg = "4&"+account;
					client.getMessage(msg);
				}
			}
		});
		button.setBounds(298, 234, 113, 27);
		button.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(button);
		
		JLabel label_1 = new JLabel("\u65B0\u5BC6\u7801");
		label_1.setFont(new Font("宋体",0,14));
		label_1.setBounds(55, 176, 72, 18);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_2.setFont(new Font("宋体",0,14));
		label_2.setBounds(55, 207, 72, 18);
		frame.getContentPane().add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(141, 235, 143, 24);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton button_1 = new JButton("\u786E\u8BA4");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String account = textField_3.getText();
				String pwd = String.valueOf(passwordField.getPassword());
				String pwd1 = String.valueOf(passwordField_1.getPassword());
				String a = textField_2.getText();
				System.out.println(aut);
				if(a.equals(aut)) {
					String msg = "5&account="+account+"&pwd="+pwd;
					client.getMessage(msg);
					JOptionPane.showMessageDialog(frame, "修改成功");
					client.closeRetrievePassword();
				}
				else {
					JOptionPane.showMessageDialog(frame, "验证码错误");
				}
			}
		});
		button_1.setBounds(171, 287, 113, 27);
		button_1.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(button_1);
		
		JLabel label_3 = new JLabel("\u8D26\u53F7");
		label_3.setFont(new Font("宋体",0,14));
		label_3.setBounds(55, 141, 72, 18);
		frame.getContentPane().add(label_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(141, 136, 143, 24);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(141, 173, 143, 24);
		frame.getContentPane().add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(141, 204, 143, 24);
		frame.getContentPane().add(passwordField_1);
		
		//背景图片
//		JPanel panel = new JPanel();
//		panel.setBounds(0, 129, 490, 215);
//		frame.getContentPane().add(panel);
//		ImageIcon img1 = new ImageIcon("img/wjmm.jpg");
//	       img1.setImage(img1.getImage().getScaledInstance(490,224,Image.SCALE_DEFAULT ));
//			JLabel imagLabel1 = new JLabel(img1); 
//			imagLabel1.setBounds(0,0,img1.getIconWidth(),img1.getIconHeight());
//			panel.add(imagLabel1);
//			
//			JPanel panel_1 = new JPanel();
//			panel_1.setBounds(-11, -36, 511, 174);
//			frame.getContentPane().add(panel_1);
//			ImageIcon img = new ImageIcon("img/q.jpg");
//			img.setImage(img.getImage().getScaledInstance(511,174,Image.SCALE_DEFAULT ));
//			JLabel imagLabel = new JLabel(img); 
//			panel_1.add(imagLabel);
//			imagLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}

	public String getAut() {
		return aut;
	}

	public void setAut(String aut) {
		this.aut = aut;
	}
}
