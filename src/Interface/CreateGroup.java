package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import bean.Group;
import client.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateGroup {

	JFrame frame;
	private JTextField textField;
	Client client;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//CreateGroup window = new CreateGroup();
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
	public CreateGroup(Client client) {
		this.client = client;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 392, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(129, 96, 145, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u7FA4\u540D\u79F0");
		label.setBounds(63, 99, 52, 18);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("\u521B\u5EFA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				if(name==null || name.equals(""))
				{
					JOptionPane.showMessageDialog(frame, "群名不能为空");
				}
				else {
					Group msg = new Group();
					msg.setName(name);
					msg.setCreateId(client.getUser().getId());
					msg.setCreateAccount(client.getUser().getUserAccount());
					msg.setCreateHead(client.getUser().getHead());
					msg.setCreateName(client.getUser().getNickName());
					client.getMessage(msg);
					frame.setVisible(false);
				}
			}
		});
		button.setBounds(129, 173, 113, 27);
		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
}
