package Interface;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import bean.Friends;
import client.Client;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendRequest {

	public JFrame frame;
	private Client client;
	private Friends msg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//FriendRequest window = new FriendRequest();
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
	public FriendRequest(Client client,Friends msg) {
		this.client = client;
		this.msg = msg;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel(msg.getMyName()+"("+msg.getMyAccount()+")"+"请求加你为好友");
		label.setBounds(70, 79, 302, 43);
		label.setFont(new Font("宋体",0,17));
		frame.getContentPane().add(label);
		
		JButton button = new JButton("\u540C\u610F");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				msg.setType(2);
				client.getMessage(msg);
			}
		});
		button.setBounds(70, 189, 113, 27);
		button.setFont(new Font("宋体",0,17));
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("\u62D2\u7EDD");
		button_1.setBounds(259, 189, 113, 27);
		button_1.setFont(new Font("宋体",0,17));
		frame.getContentPane().add(button_1);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
}
