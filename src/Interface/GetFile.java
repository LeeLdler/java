package Interface;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JLabel;

import bean.FileMessage;
import client.Client;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GetFile {

	public JFrame frame;
	Client client;
	FileMessage msg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	GetFile window = new GetFile();
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
	public GetFile(Client client,FileMessage msg) {
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
		
		JLabel lblNewLabel = new JLabel("对方向你发送了一个文件，是否接收");
		if(msg.getType()==3) {
			lblNewLabel.setText("是否接收该文件");
		}
		lblNewLabel.setBounds(35, 29, 310, 38);
		frame.getContentPane().add(lblNewLabel);
		
		JButton button = new JButton("\u662F");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byteFile(msg.getB(),"E:\\Chat room\\getfile","\\"+msg.getName());
				client.closeGetFile();
				if(msg.getType() == 2) {
				client.chatFriend.addFile(msg.getFriendName(),msg.getDate(), "E:\\Chat room\\getfile"+"\\"+msg.getName(), false);
				try {
					writeRecord(msg.getMyId(),"E:\\Chat room\\getfile"+"\\"+msg.getName(),msg.getDate(),3,msg.getFriendId(),msg.getMyId(),false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});
		button.setBounds(35, 167, 113, 27);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("\u5426");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.closeGetFile();
			}
		});
		button_1.setBounds(247, 167, 113, 27);
		frame.getContentPane().add(button_1);
		
		JLabel lblNewLabel_1 = new JLabel(msg.getName());
		lblNewLabel_1.setBounds(84, 90, 275, 27);
		frame.getContentPane().add(lblNewLabel_1);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	private void writeRecord(int id,String msg,String date,int type,int myId,int friendId,boolean isGroup) throws IOException {
		OutputStream os = new FileOutputStream("E:\\Chat room\\record\\"+myId+"to"+friendId+"isGroup"+isGroup+".txt",true);
		PrintWriter pw=new PrintWriter(os);
		pw.println(id);
		pw.println(msg);//每输入一个数据，自动换行，便于我们每一行每一行地进行读取
		pw.println(date);
		pw.println(type);
		pw.println(0);
		pw.close();
		os.close();
 }
	private void byteFile(byte[] b,String filePath,String fileName) {
		BufferedOutputStream bos=null;
	    FileOutputStream fos=null;
	    File file=null;
	    try{
	        File dir=new File(filePath);
	        if(!dir.exists() && !dir.isDirectory()){//判断文件目录是否存在
	            dir.mkdirs();
	        }
	        file=new File(filePath+fileName);
	        fos=new FileOutputStream(file);
	        bos=new BufferedOutputStream(fos);
	        bos.write(b);
	    }
	    catch(Exception e){
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    }
	    finally{
	        try{
	            if(bos != null){
	                bos.close();
	            }
	            if(fos != null){
	                fos.close();
	            }
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	        }
	    }
}
}
