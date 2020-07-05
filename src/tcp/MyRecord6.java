package tcp;

import java.awt.*;


import javax.swing.*;

import bean.FileMessage;
import client.Client;

import java.awt.event.*;
import java.io.*;
 
import javax.sound.sampled.*;
 
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class MyRecord6 extends JFrame implements MouseListener{
 
	//����¼����ʽ
	AudioFormat af = null;
	//����Ŀ��������,���Դ��ж�ȡ��Ƶ����,�� TargetDataLine �ӿ��ṩ��Ŀ�������еĻ�������ȡ���������ݵķ�����
	TargetDataLine td = null;
	//����Դ������,Դ�������ǿ���д�����ݵ������С����䵱���Ƶ����Դ��Ӧ�ó�����Ƶ�ֽ�д��Դ�����У������ɴ����ֽڻ��岢�����Ǵ��ݸ���Ƶ����
	SourceDataLine sd = null;
	//�����ֽ��������������
	ByteArrayInputStream bais = null;
	ByteArrayOutputStream baos = null;
	//������Ƶ������
	AudioInputStream ais = null;
	//����ֹͣ¼���ı�־��������¼���̵߳�����
	Boolean stopflag = false;
	//��¼��ʼ¼����ʱ��
	long startPlay;
	//����һ�����ŵı�־
	Boolean playflag;
	//ÿ�α���������ļ���
	File tarFile = null;
	//������Ƶ����ÿ����ʾ���ֽ���
	int intBytes = 0;
	//����ÿ��¼����ʱ��ÿ����ȡ�ֽ�������Ƶ��
	byte audioDataBuffer[] = null;
	//��������Ҫ�����
	JPanel jp1,jp2,jp3;
	JLabel jl1=null;
	JButton captureBtn;
	//���û������̵߳���ֹ�ı�־
	boolean flag = true;
	//���岥��¼��ʱ��һ������ֵ
	int cnt;
	//���岥��¼��ʱһ����������
	byte btsPlay[] = null;
 
	int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
	double weightx, weighty;
	Insets inset;
	GridBagConstraints c;
	Client client;
	public static void main(String[] args) {
		
	//����һ��ʵ��
	//MyRecord6 mr = new MyRecord6();
		
	}
	//���캯��
	public MyRecord6(Client client)
	{
		this.client = client;
		//�����ʼ��
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
	
		//����jp1������
		Font jpFont = new Font("������κ",Font.BOLD,40);
		jl1 = new JLabel("����������˵�Ļ�");
		jl1.setFont(jpFont);
		jl1.setForeground(Color.red);
		jp1.add(jl1);
		//���尴ť���������
		Font btFont = new Font("������κ",Font.BOLD,40);
		captureBtn = new JButton("��ס ˵��");
		//setForeground�������ð�ť�����������ɫ
		captureBtn.setForeground(Color.RED);
		captureBtn.setFont(btFont);
		//�Կ�ʼ¼����ť����������
		captureBtn.addMouseListener(this);
		
		
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		GridBagLayout gridbag = null;
		jp3.setLayout(gridbag = new GridBagLayout());
		gridx=1;
		gridy=2;
		gridwidth=1;
		gridheight=1;
		weightx=1;
		weighty=1;
		anchor=GridBagConstraints.CENTER;
		fill=GridBagConstraints.HORIZONTAL;
		inset=new Insets(1,1,1,1);
		ipadx=0;
		ipady=30;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(captureBtn, c);
		jp3.add(captureBtn);
 
		//���ô��ڵ�����
		this.setSize(500,300);
		this.setTitle("¼��");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ô��ھ���
		this.setLocation(client.chatFriend.getX()+100 ,client.chatFriend.getY()+100 );
		//�����ڵı߿�ȥ��
		this.setUndecorated(true);
		this.setVisible(true);
		//���ô����ϵ�ͼ��
		//Image img = this.getToolkit().getImage(getClass().getResource("/image/Recorder.jpg"));
		//this.setIconImage(img);
		//���ô�������ǰ����ʾ
		this.setAlwaysOnTop(true);
	}
	public void mouseClicked(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		//����ʼ¼����ť������ʱ�Ϳ�ʼ¼��
		if(e.getSource().equals(captureBtn))
		{
	        //�ı䰴ť������ֵ�����
	        captureBtn.setText("�ɿ� ����");
			
	        //����¼���ķ���
	        capture();
	        
	        //��¼��ʼ¼����ʱ��
	        startPlay = System.currentTimeMillis();
		}
		
	}
 
	public void mouseReleased(MouseEvent e) {
		//���ɿ�¼����ťʱֹͣ¼��������¼�����ļ�
		if(e.getSource().equals(captureBtn))
		{
			//����ֹͣ¼���ķ���
			stop();
			//���ɿ���ť�����ʾ���ε����������
			jp2.repaint();
			//�ı䰴ť������ֵ�����
			captureBtn.setText("��ס ˵��");
			
			long now = System.currentTimeMillis();
			long x= now-startPlay;
			if(x>=2000) {
			System.out.println("����ʱ��:"+x);
			//���ñ���¼���ķ���
			save();
			//����ŵ�ָ����·����
			//��������Ҫ��ŵ��ļ�·��
			String destPath = "D:/Program Files/apache-tomcat-6.0.35/webapps/XWZ/tempFile/";
			System.out.println(tarFile.getName());
			copyFile("D:/"+tarFile.getName(), destPath);
			 FileMessage msg = new FileMessage();
			 String name =  tarFile.getName().replace("mp3", "wav");
	            msg.setMyId(client.getUser().getId());
	            msg.setMyName(client.getUser().getNickName());
	            msg.setType(5);
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	    		String date = df.format(new Date());
	    		msg.setDate(date);
	    		msg.setName(name);
	    		File openFile = new File("E:/Chat room/sendVoice/"+msg.getName());
	    		byte audioData[] = null;
	    		FileInputStream fis;
	    		try {
	    			fis = new FileInputStream(openFile);
	    			audioData = new byte[(int) openFile.length()];
	    			fis.read(audioData);
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}
	    		msg.setB(audioData);
	    		msg.setFriendId(client.chatFriend.getChatFriend().getFriendId());
	    		client.chatFriend.addVideo(msg.getMyName(), msg.getDate(), "E:/Chat room/sendVoice/"+msg.getName(), true);
	    		try {
					client.chatFriend.writeRecord(msg.getMyId(),"E:/Chat room/sendVoice/"+msg.getName(),msg.getDate(),5,1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		client.getMessage(msg);
			this.setVisible(false);
			//System.exit(0);
			}
			else
			{
				this.setVisible(false);
				JOptionPane.showMessageDialog(client.chatFriend, "¼��ʱ��̫�̣�����ʧ��");
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		
		
	}
	public void mouseExited(MouseEvent e) {
		
		
	}
	//��ʼ¼��
	public void capture()
	{
		try {
			//afΪAudioFormatҲ������Ƶ��ʽ
			af = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class,af);
			td = (TargetDataLine)(AudioSystem.getLine(info));
			
			//�򿪾���ָ����ʽ���У�������ʹ�л�����������ϵͳ��Դ����ÿɲ�����
			td.open(af);
			//����ĳһ������ִ������ I/O
			td.start();
			
			//������ʾ���εĽ���
	//		RecordWave aw = new RecordWave();
		//	Thread t2 = new Thread(aw);
		//	t2.start();
			//����ʾ���εĽ��̱�־��Ϊtrue
		//	flag = true;
			
			Record record = new Record();
			Thread t1 = new Thread(record);
			t1.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}
	//ֹͣ¼��
	public void stop()
	{
		stopflag = true;
		//�������εĽ�����ֹ
		flag = false;
	}
	//����¼��
	public void save()
	{
		af = getAudioFormat();
        byte audioData[] = baos.toByteArray();
        bais = new ByteArrayInputStream(audioData);
        ais = new AudioInputStream(bais,af, audioData.length / af.getFrameSize());
        //�������ձ�����ļ���
        File file = null;
        //д���ļ�
        try {	
        	//�Ե�ǰ��ʱ������¼��������
        	//��¼�����ļ���ŵ�F���������ļ�����
        	File filePath = new File("E:/Chat room/sendVoice");
        	String tarPath = "E:/Chat room/";
        	if(!filePath.exists())
        	{//����ļ������ڣ��򴴽���Ŀ¼
        		filePath.mkdirs();
        	}
        	long time = System.currentTimeMillis();
        	file = new File(filePath+"/"+time+".wav");      
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
            //��¼��������wav�ļ�ת��Ϊ������С��mp3��ʽ
            //����������ļ���
            tarFile = new File(tarPath+time+".mp3"); 
            Runtime run = null;
            //���Ե�ǰ��·��
            
            try {
				run = Runtime.getRuntime();
				//���ñ���������wav�ļ�ת��Ϊmp3�ļ�
                //�ѱ���õ���mp3�ļ��ȴ�ŵ�D���£�Ȼ�������ļ��������������ŵ�ָ�����ļ�����ͬʱ��D���µ��ļ�ɾ��
				Process p=run.exec(filePath+"/"+"lame -b 16 "+filePath+"/"+file.getName()+" "+tarPath+tarFile.getName()); //16Ϊ���ʣ��������޸�
				//�ͷŽ���
				p.getOutputStream().close();
				p.getInputStream().close();
				p.getErrorStream().close();
				//�ȴ�
				p.waitFor();
 
//				//ɾ��֮ǰ����ĵ�wav�ļ�
//				if(file.exists())
//				{
//					file.delete();
//				}
				
//				//��������Ҫ��ŵ��ļ�·��
//				String destPath = "D:/Program Files/apache-tomcat-6.0.35/webapps/XWZ/tempFile/";
//				copyFile(tarPath+tarFile.getName(), destPath);
			} catch (Exception e) {
				//e.printStackTrace();
			}finally{
				//���Ҫִ�е����
				//run����lame����������ͷ��ڴ�
				run.freeMemory();
			}
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	//�ر���
        	try {
        		
        		if(bais != null)
        		{
        			bais.close();
        		} 
        		if(ais != null)
        		{
        			ais.close();		
        		}
			} catch (Exception e) {
				e.printStackTrace();
			}   	
        }
	}
	//�ļ���������
	public void copyFile(String srcPath , String destPath)
	{
		File srcFile = new File(srcPath);
		//���Ŀ���ļ���û���򴴽�Ŀ���ļ���
		(new File(destPath)).mkdirs();
		//��Ŀ���ļ����´���Ҫ���Ƶ��ļ�
		File destFile = new File(destPath+"/"+srcFile.getName());
		if(srcFile.isFile() && srcFile.exists())
		{
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(srcFile);
				out = new FileOutputStream(destFile);
				//���û�������
				byte[] buff = new byte[1024*5];   
		        int len = 0;   
		        while ((len = in.read(buff)) != -1) 
		        {   
		            out.write(buff, 0, len);   
		        }
//		        //���Ըú����Ƿ�ִ��
//		        System.out.println("ok1");
		         
			} catch(Exception e) {
				e.printStackTrace();
			}finally{
				//�ر������ȿ��ĺ�ر�
				try {
					if(out != null)
					{
						out.close(); 
					}
					if(in != null)
					{
						in.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//���ƹ���ɾ��Դ�ļ����еĵ��ļ�
		if(srcFile.exists())
		{
			srcFile.delete();
		}
	}
	//����AudioFormat�Ĳ���
	public AudioFormat getAudioFormat() 
	{
		//����ע�Ͳ���������һ����Ƶ��ʽ�����߶�����
		AudioFormat.Encoding encoding = AudioFormat.Encoding.
        PCM_SIGNED ;
		float rate = 8000f;
		int sampleSize = 16;
		String signedString = "signed";
		boolean bigEndian = true;
		int channels = 1;
		return new AudioFormat(encoding, rate, sampleSize, channels,
				(sampleSize / 8) * channels, rate, bigEndian);
	}
	//¼���࣬��ΪҪ�õ�MyRecord���еı��������Խ��������ڲ���
	class Record implements Runnable
	{
		//������¼�����ֽ�����,��Ϊ������
		byte bts[] = new byte[10000];
		//���ֽ������װ��������մ��뵽baos��
		//��дrun����
		public void run() {	
			baos = new ByteArrayOutputStream();		
			try {
				stopflag = false;
				while(stopflag != true)
				{
					//��ֹͣ¼��û����ʱ�����߳�һֱִ��	
					//�������е����뻺������ȡ��Ƶ���ݡ�
					//Ҫ��ȡbts.length���ȵ��ֽ�,cnt ��ʵ�ʶ�ȡ���ֽ���
					int cnt = td.read(bts, 0, bts.length);
					if(cnt > 0)
					{
						baos.write(bts, 0, cnt);
					}
					
					//��ʼ����Ƶ���ж�ȡ�ֽ���
					byte copyBts[] = bts;
					bais = new ByteArrayInputStream(copyBts);
					ais = new AudioInputStream(bais, af, copyBts.length/af.getFrameSize());
					try{
						DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, af);
			            sd = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			            sd.open(af);
			            sd.start();
			            
			            //����Ƶ���ж�ȡ
			            int Buffer_Size = 10000;
			            audioDataBuffer = new byte[Buffer_Size];
			            int outBytes;
			       
						intBytes = ais.read(audioDataBuffer, 0,audioDataBuffer.length);
						
//						��д����Ƶ���������Ͳ��Ქ��
//						if (intBytes >= 0) {
//							outBytes = sd.write(audioDataBuffer, 0,audioDataBuffer.length);
//						}   
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					//intBytes = -1;
					//�رմ򿪵��ֽ�������
					if(baos != null)
					{
						baos.close();
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					//�������td.drain()����Ҫ������������������ݾ������ٴ�¼�����������������ʴ���
					//td.drain();
					td.close();
					//ˢ����ʾ���ε����
					jp2.repaint();
				}
			}
		}
		
	}
	

		
}

