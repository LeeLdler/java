package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import bean.GroupRelationship;
import client.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddGroup {

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
					//addGroup window = new addGroup();
				//	window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddGroup(Client client) {
		this.client = client;
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
		
		textField = new JTextField();
		textField.setBounds(142, 113, 153, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u7FA4\u8D26\u53F7");
		label.setBounds(83, 116, 45, 18);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("\u52A0\u5165");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account = textField.getText();
				if(account == null||account.equals("")) {
					JOptionPane.showMessageDialog(frame, "群号不能为空");
				}
				else {
					int flag=0;
					for(int i=0;i<client.getUser().getGroup().size();i++) {
						if(Integer.parseInt(account) == client.getUser().getGroup().get(i).getAccount()){
							JOptionPane.showMessageDialog(frame, "你已经加过该群");
							flag=1;
							break;
						}
					}
					if(flag==0) {
						GroupRelationship msg = new GroupRelationship();
						msg.setGroupId(Integer.parseInt(account)-5050);
						msg.setUserId(client.getUser().getId());
						msg.setUserAccount(client.getUser().getUserAccount());
						msg.setUserHead(client.getUser().getHead());
						msg.setUserName(client.getUser().getNickName());
						msg.setType(1);
						client.getMessage(msg);
					}
				}
			}
		});
		button.setBounds(142, 187, 113, 27);
		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}

}
