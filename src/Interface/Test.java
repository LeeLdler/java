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

	private File openFile;                        //文件类
	private FileInputStream fileInputStream;       //字节文件输入流 
	private FileOutputStream fileOutputStream;     //字节文件输出流
	private OutputStreamWriter outputStreamWriter; //字符文件输出流


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
				JFileChooser chooser = new JFileChooser(); //文件选择
				chooser.showOpenDialog(chooser);        //打开文件选择窗
				openFile = chooser.getSelectedFile();  	//获取选择的文件
				textField.setText(openFile.getPath());	//获取选择文件的路径
	
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
