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
 
	//定义录音格式
	AudioFormat af = null;
	//定义目标数据行,可以从中读取音频数据,该 TargetDataLine 接口提供从目标数据行的缓冲区读取所捕获数据的方法。
	TargetDataLine td = null;
	//定义源数据行,源数据行是可以写入数据的数据行。它充当其混频器的源。应用程序将音频字节写入源数据行，这样可处理字节缓冲并将它们传递给混频器。
	SourceDataLine sd = null;
	//定义字节数组输入输出流
	ByteArrayInputStream bais = null;
	ByteArrayOutputStream baos = null;
	//定义音频输入流
	AudioInputStream ais = null;
	//定义停止录音的标志，来控制录音线程的运行
	Boolean stopflag = false;
	//记录开始录音的时间
	long startPlay;
	//设置一个播放的标志
	Boolean playflag;
	//每次保存的最后的文件名
	File tarFile = null;
	//定义音频波形每次显示的字节数
	int intBytes = 0;
	//定义每次录音的时候每次提取字节来画音频波
	byte audioDataBuffer[] = null;
	//定义所需要的组件
	JPanel jp1,jp2,jp3;
	JLabel jl1=null;
	JButton captureBtn;
	//设置画波形线程的终止的标志
	boolean flag = true;
	//定义播放录音时的一个计数值
	int cnt;
	//定义播放录音时一个缓冲数组
	byte btsPlay[] = null;
 
	int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
	double weightx, weighty;
	Insets inset;
	GridBagConstraints c;
	Client client;
	public static void main(String[] args) {
		
	//创造一个实例
	//MyRecord6 mr = new MyRecord6();
		
	}
	//构造函数
	public MyRecord6(Client client)
	{
		this.client = client;
		//组件初始化
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
	
		//定义jp1的字体
		Font jpFont = new Font("华文新魏",Font.BOLD,40);
		jl1 = new JLabel("请留下您想说的话");
		jl1.setFont(jpFont);
		jl1.setForeground(Color.red);
		jp1.add(jl1);
		//定义按钮上面的字体
		Font btFont = new Font("华文新魏",Font.BOLD,40);
		captureBtn = new JButton("按住 说话");
		//setForeground可以设置按钮上面字体的颜色
		captureBtn.setForeground(Color.RED);
		captureBtn.setFont(btFont);
		//对开始录音按钮进行鼠标监听
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
 
		//设置窗口的属性
		this.setSize(500,300);
		this.setTitle("录音");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口居中
		this.setLocation(client.chatFriend.getX()+100 ,client.chatFriend.getY()+100 );
		//将窗口的边框去掉
		this.setUndecorated(true);
		this.setVisible(true);
		//设置窗口上的图标
		//Image img = this.getToolkit().getImage(getClass().getResource("/image/Recorder.jpg"));
		//this.setIconImage(img);
		//设置窗口在最前端显示
		this.setAlwaysOnTop(true);
	}
	public void mouseClicked(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		//当开始录音按钮被按下时就开始录音
		if(e.getSource().equals(captureBtn))
		{
	        //改变按钮上面的字的内容
	        captureBtn.setText("松开 结束");
			
	        //调用录音的方法
	        capture();
	        
	        //记录开始录音的时间
	        startPlay = System.currentTimeMillis();
		}
		
	}
 
	public void mouseReleased(MouseEvent e) {
		//当松开录音按钮时停止录音并保存录音的文件
		if(e.getSource().equals(captureBtn))
		{
			//调用停止录音的方法
			stop();
			//当松开按钮后对显示波形的面板进行清空
			jp2.repaint();
			//改变按钮上面的字的内容
			captureBtn.setText("按住 说话");
			
			long now = System.currentTimeMillis();
			long x= now-startPlay;
			if(x>=2000) {
			System.out.println("持续时间:"+x);
			//调用保存录音的方法
			save();
			//将其放到指定的路径下
			//定义最终要存放的文件路径
			String destPath = "D:/Program Files/apache-tomcat-6.0.35/webapps/XWZ/tempFile/";
			System.out.println(tarFile.getName());
			copyFile("D:/"+tarFile.getName(), destPath);
			 FileMessage msg = new FileMessage();
			 String name =  tarFile.getName().replace("mp3", "wav");
	            msg.setMyId(client.getUser().getId());
	            msg.setMyName(client.getUser().getNickName());
	            msg.setType(5);
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
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
				JOptionPane.showMessageDialog(client.chatFriend, "录音时间太短，发送失败");
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		
		
	}
	public void mouseExited(MouseEvent e) {
		
		
	}
	//开始录音
	public void capture()
	{
		try {
			//af为AudioFormat也就是音频格式
			af = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class,af);
			td = (TargetDataLine)(AudioSystem.getLine(info));
			
			//打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。
			td.open(af);
			//允许某一数据行执行数据 I/O
			td.start();
			
			//启动显示波形的进程
	//		RecordWave aw = new RecordWave();
		//	Thread t2 = new Thread(aw);
		//	t2.start();
			//把显示波形的进程标志设为true
		//	flag = true;
			
			Record record = new Record();
			Thread t1 = new Thread(record);
			t1.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}
	//停止录音
	public void stop()
	{
		stopflag = true;
		//将画波形的进程终止
		flag = false;
	}
	//保存录音
	public void save()
	{
		af = getAudioFormat();
        byte audioData[] = baos.toByteArray();
        bais = new ByteArrayInputStream(audioData);
        ais = new AudioInputStream(bais,af, audioData.length / af.getFrameSize());
        //定义最终保存的文件名
        File file = null;
        //写入文件
        try {	
        	//以当前的时间命名录音的名字
        	//将录音的文件存放到F盘下语音文件夹下
        	File filePath = new File("E:/Chat room/sendVoice");
        	String tarPath = "E:/Chat room/";
        	if(!filePath.exists())
        	{//如果文件不存在，则创建该目录
        		filePath.mkdirs();
        	}
        	long time = System.currentTimeMillis();
        	file = new File(filePath+"/"+time+".wav");      
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
            //将录音产生的wav文件转换为容量较小的mp3格式
            //定义产生后文件名
            tarFile = new File(tarPath+time+".mp3"); 
            Runtime run = null;
            //测试当前的路径
            
            try {
				run = Runtime.getRuntime();
				//调用编码器来将wav文件转换为mp3文件
                //把编码得到的mp3文件先存放到D盘下，然后利用文件拷贝函数将它放到指定的文件夹下同时将D盘下的文件删除
				Process p=run.exec(filePath+"/"+"lame -b 16 "+filePath+"/"+file.getName()+" "+tarPath+tarFile.getName()); //16为码率，可自行修改
				//释放进程
				p.getOutputStream().close();
				p.getInputStream().close();
				p.getErrorStream().close();
				//等待
				p.waitFor();
 
//				//删除之前保存的的wav文件
//				if(file.exists())
//				{
//					file.delete();
//				}
				
//				//定义最终要存放的文件路径
//				String destPath = "D:/Program Files/apache-tomcat-6.0.35/webapps/XWZ/tempFile/";
//				copyFile(tarPath+tarFile.getName(), destPath);
			} catch (Exception e) {
				//e.printStackTrace();
			}finally{
				//最后都要执行的语句
				//run调用lame解码器最后释放内存
				run.freeMemory();
			}
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	//关闭流
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
	//文件拷贝方法
	public void copyFile(String srcPath , String destPath)
	{
		File srcFile = new File(srcPath);
		//如果目的文件夹没有则创建目的文件夹
		(new File(destPath)).mkdirs();
		//在目的文件夹下创建要复制的文件
		File destFile = new File(destPath+"/"+srcFile.getName());
		if(srcFile.isFile() && srcFile.exists())
		{
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(srcFile);
				out = new FileOutputStream(destFile);
				//设置缓冲数组
				byte[] buff = new byte[1024*5];   
		        int len = 0;   
		        while ((len = in.read(buff)) != -1) 
		        {   
		            out.write(buff, 0, len);   
		        }
//		        //测试该函数是否执行
//		        System.out.println("ok1");
		         
			} catch(Exception e) {
				e.printStackTrace();
			}finally{
				//关闭流，先开的后关闭
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
		//复制过后删除源文件夹中的的文件
		if(srcFile.exists())
		{
			srcFile.delete();
		}
	}
	//设置AudioFormat的参数
	public AudioFormat getAudioFormat() 
	{
		//下面注释部分是另外一种音频格式，两者都可以
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
	//录音类，因为要用到MyRecord类中的变量，所以将其做成内部类
	class Record implements Runnable
	{
		//定义存放录音的字节数组,作为缓冲区
		byte bts[] = new byte[10000];
		//将字节数组包装到流里，最终存入到baos中
		//重写run函数
		public void run() {	
			baos = new ByteArrayOutputStream();		
			try {
				stopflag = false;
				while(stopflag != true)
				{
					//当停止录音没按下时，该线程一直执行	
					//从数据行的输入缓冲区读取音频数据。
					//要读取bts.length长度的字节,cnt 是实际读取的字节数
					int cnt = td.read(bts, 0, bts.length);
					if(cnt > 0)
					{
						baos.write(bts, 0, cnt);
					}
					
					//开始从音频流中读取字节数
					byte copyBts[] = bts;
					bais = new ByteArrayInputStream(copyBts);
					ais = new AudioInputStream(bais, af, copyBts.length/af.getFrameSize());
					try{
						DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, af);
			            sd = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			            sd.open(af);
			            sd.start();
			            
			            //从音频流中读取
			            int Buffer_Size = 10000;
			            audioDataBuffer = new byte[Buffer_Size];
			            int outBytes;
			       
						intBytes = ais.read(audioDataBuffer, 0,audioDataBuffer.length);
						
//						不写到混频器中这样就不会播放
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
					//关闭打开的字节数组流
					if(baos != null)
					{
						baos.close();
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					//下面这句td.drain()不能要，这样如果不播放数据就阻塞再次录音会出现其他程序访问错误
					//td.drain();
					td.close();
					//刷新显示波形的面板
					jp2.repaint();
				}
			}
		}
		
	}
	

		
}

