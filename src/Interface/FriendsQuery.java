package Interface;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;

import client.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendsQuery {

	public JFrame frame;
	private JTextField textField;
	Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	FriendsQuery window = new FriendsQuery();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FriendsQuery(Client client) {
		this.client = client;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 284);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(112, 109, 195, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u8BF7\u8F93\u5165\u8D26\u53F7\uFF1A");
		label.setFont(new Font("宋体",0,17));
		label.setBounds(46, 50, 119, 34);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("\u641C\u7D22");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account = textField.getText();
				if(account.equals(""))
				{
					JOptionPane.showMessageDialog(frame, "账号不能为空");
				}
				else
				{
					String msg = "3"+"查询"+account;
					client.getMessage(msg);
				}
			}
		});
		button.setFont(new Font("宋体",0,17));
		button.setBounds(153, 180, 113, 27);
		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
}
