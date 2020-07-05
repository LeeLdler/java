package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import bean.ChatFriend;
import client.Client;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class CommonPhrases {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	Client client;
	ChatFriend chat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//CommonPhrases window = new CommonPhrases();
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
	public CommonPhrases(Client client,ChatFriend chat) {
		this.client = client;
		this.chat = chat;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 421);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		String[] msg = {"","","","",""}; 
		for(int i=0;i<client.getPh().getPhrase().size();i++) {
			msg[i]=client.getPh().getPhrase().get(i);
		}
		textField = new JTextField(msg[0]);
		textField.setBounds(102, 73, 205, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(msg[1]);
		textField_1.setBounds(102, 121, 205, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField(msg[2]);
		textField_2.setBounds(102, 170, 205, 24);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField(msg[3]);
		textField_3.setBounds(102, 220, 205, 24);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		textField_4 = new JTextField(msg[4]);
		textField_4.setBounds(102, 268, 205, 24);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JButton button = new JButton("\u4FDD\u5B58");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					writePhrase(textField.getText(),textField_1.getText(),textField_2.getText(),textField_3.getText(),textField_4.getText());
					frame.setVisible(false);
					client.closeChatWithFriend();
					client.setChatWithFriend(chat, false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		});
		button.setBounds(149, 334, 113, 27);
		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	private void writePhrase(String msg1,String msg2,String msg3,String msg4,String msg5) throws FileNotFoundException{
		OutputStream os = new FileOutputStream("E:\\Chat room\\record\\"+client.getUser().getId()+"phrase.txt");
		PrintWriter pw=new PrintWriter(os);
		if(msg1 != null && !msg1.equals(""))
		pw.println(msg1);
		if(msg2 != null && !msg2.equals(""))
		pw.println(msg2);
		if(msg3 != null && !msg3.equals(""))
		pw.println(msg3);
		if(msg4 != null && !msg4.equals(""))
		pw.println(msg4);
		if(msg5 != null && !msg5.equals(""))
		pw.println(msg5);
		pw.close();
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }
}
