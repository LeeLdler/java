package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Test {

	private JFrame frame;
	private JTextField textField;

	private File openFile;                        //�ļ���
	private FileInputStream fileInputStream;       //�ֽ��ļ������� 
	private FileOutputStream fileOutputStream;     //�ֽ��ļ������
	private OutputStreamWriter outputStreamWriter; //�ַ��ļ������


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
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
		
		JButton button = new JButton("\u6DFB\u52A0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(); //�ļ�ѡ��
				chooser.showOpenDialog(chooser);        //���ļ�ѡ��
				openFile = chooser.getSelectedFile();  	//��ȡѡ����ļ�
				textField.setText(openFile.getPath());	//��ȡѡ���ļ���·��
	
			}
		});
		
		button.setBounds(128, 138, 113, 27);
		frame.getContentPane().add(button);
		
		textField = new JTextField();
		textField.setBounds(128, 88, 213, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
