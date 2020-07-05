package Interface;

import java.awt.EventQueue;


import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import bean.Message;
import client.Client;
import client.Send;
import server.Server;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;

public class SignInterface {

	public JFrame frmQq;
	private Client client;
	private JPasswordField passwordField;
	private JTextField textField;
	public JRadioButton radioButton;

	

	/**
	 * Create the application.
	 */
	public SignInterface(Client client) {
		initialize();
		this.client = client;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//����frame
		Vector<String> v = readRecord();
		frmQq = new JFrame();
		frmQq.setTitle("������������");
		frmQq.setBounds(100, 100, 456, 422);
		frmQq.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmQq.setResizable(false);
		frmQq.getContentPane().setLayout(null);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // ���ñ�������ͼ�� 
        frmQq.setIconImage(imageIcon.getImage());
		String pwd;
		if(v.size()!=2) {
			pwd = "";
		}
		else
		{
			pwd = v.get(1);
		}
        //���������ı���
		passwordField = new JPasswordField(pwd);
		passwordField.setEchoChar((char)0);
		passwordField.setBounds(173, 224, 218, 33);
            	passwordField.setEchoChar('��');
            
		
		frmQq.getContentPane().add(passwordField);
		String acc;
		//�˺��ı���
		if(v.size()!=2) {
			acc = "";
		}
		else
		{
			acc = v.get(0);
		}
		textField = new JTextField(acc);
		
		textField.setBounds(173, 178, 218, 33);
		frmQq.getContentPane().add(textField);
		textField.setColumns(10);
		
		//���ð�ť��ס����
		if(v.size() == 2) {
			radioButton = new JRadioButton("\u8BB0\u4F4F\u5BC6\u7801",true);
		}
		else {
		radioButton = new JRadioButton("\u8BB0\u4F4F\u5BC6\u7801");
		}
		radioButton.setBounds(173, 266, 122, 27);
		radioButton.setFont(new Font("����",0,14));
		frmQq.getContentPane().add(radioButton);
		if(v.size() == 2) {
			radioButton.isSelected();
		}
		//���ð�ť�Զ���¼
		JRadioButton radioButton_1 = new JRadioButton("\u81EA\u52A8\u767B\u5F55");
		radioButton_1.setBounds(293, 266, 157, 27);
		radioButton_1.setFont(new Font("����",0,14));
		frmQq.getContentPane().add(radioButton_1);
		//���õ�¼��ť 
		JButton btnNewButton = new JButton("\u767B     \u5F55");
		btnNewButton.setBounds(173, 300, 218, 41);
		btnNewButton.setFont(new Font("����",0,15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String upwd = String.valueOf(passwordField.getPassword());
			    String uname = textField.getText();
			    if(uname.equals("")||upwd.equals("")){
			    	String tip1 = "  �˺Ż����벻��Ϊ�գ�";
        			String tip2 = "��¼ʧ��";
        			tip t = new tip(tip1,tip2,client);
			    }
			    else {
			    String msg = "1"+"uname="+uname+"&"+"upwd="+upwd;
			   
			    if(!msg.equals("")) {
			    client.getMessage(msg);
			    
			    }
			    }
			}
		});
		frmQq.getContentPane().add(btnNewButton);
		
		
		
		//����ע���˺�
		JLabel lblNewLabel = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		lblNewLabel.setFont(new Font("����",0,15));
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmQq.setVisible(false);
				client.setRegist();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblNewLabel.setForeground(new Color(100,149,238)); 
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setForeground(new Color(0,0,0));
			}
		});
		lblNewLabel.setBounds(25, 354, 72, 18);
		frmQq.getContentPane().add(lblNewLabel);
		
		
		//����qͼƬ
		JPanel panel = new JPanel();
		panel.setBounds(0, -29, 450, 179);
		
		frmQq.getContentPane().add(panel);
		ImageIcon img = new ImageIcon("img/1234.jpg");
		img.setImage(img.getImage().getScaledInstance(450,179,Image.SCALE_DEFAULT ));
		JLabel imagLabel = new JLabel(img); 
		panel.add(imagLabel);
		imagLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
		
		//������������
		JLabel label_1 = new JLabel("\u5FD8\u8BB0\u5BC6\u7801");
		label_1.setFont(new Font("����",0,15));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				client.setRetrievePassword();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				label_1.setForeground(new Color(100,149,238));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_1.setForeground(new Color(0,0,0));
			}
		});
		
		
		label_1.setBounds(364, 354, 72, 18);
		frmQq.getContentPane().add(label_1);
		
		//����qeͼƬ
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(14, 178, 122, 115);
		frmQq.getContentPane().add(panel_1);
       ImageIcon img1 = new ImageIcon("img/qiu.jpg");
       img1.setImage(img1.getImage().getScaledInstance(122,115,Image.SCALE_DEFAULT ));
		JLabel imagLabel1 = new JLabel(img1); 
		imagLabel1.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
		panel_1.add(imagLabel1);
		
	}
	private Vector<String> readRecord() {
		String path = "E:\\Chat room\\record\\"+"pwd"+".txt";
		File f = new File(path);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		        Vector<String> msg = new Vector<String>();
		        //�򿪴���ȡ���ļ�
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("E:\\Chat room\\record\\"+"pwd"+".txt"));
			String line=null;
		
		while((line=br.readLine())!=null) {
			msg.add(line);
			}
		br.close();//�ر��ļ�
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}
}
