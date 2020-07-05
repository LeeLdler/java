package Interface;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;

import bean.ChatFriend;
import bean.FileMessage;
import bean.Message;
import bean.Phrase;
import bean.User;
import client.Client;
import tcp.MyRecord6;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;

public class ChatWithFriend extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton minimize, close, maximize, closeButton, sendButton;
	JPopupMenu popupMenu; //弹出菜单
	JMenuItem[] Items;
	private JPanel upPanel, inputPanel;
	private JLabel friendAvatar, friendName, friendTrades;
	private ChatFriend chatFriend;
	private Box showBox, groupPeopleBox,fileBox;
	private JScrollPane showPanel, inputScroll, groupPeopleScrollPanel,fileScrollPanel;
	JTextArea input;
	private Image headPic;
	private int messageNum = 0;
	private boolean isGroup = false;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private Client client;
	private Phrase ph;
	ChatWithFriend x;
	private File openFile;
	private AudioClip audioClip;
	public ChatFriend getChatFriend() {
		return this.chatFriend;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Client client = new Client();
				//	ChatFriend chatFriend = new ChatFriend();
					//ChatWithFriend window = new ChatWithFriend(client,chatFriend,false);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	
	public ChatWithFriend(Client client,ChatFriend chatFriend, boolean isGroup,Phrase ph) {
		this.client = client;
		this.chatFriend = chatFriend;
		this.isGroup = isGroup;
		this.ph = ph;
		x=this;
		headPic = new ImageIcon(chatFriend.getFriendHead()).getImage().getScaledInstance(41, 37, Image.SCALE_DEFAULT);
		setTitle(chatFriend.getFriendNickName());

		getContentPane().setLayout(null);
		// 更改显示的小图标
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // 设置标题栏的图标 
        setIconImage(imageIcon.getImage());
		
		setSize(665, 582);// 526 + 139
		init();
		CommonAction action = new CommonAction(); //菜单项事件处理
		String[] Str = {"设置常用语"}; //菜单项名称
		popupMenu=new JPopupMenu(); //实例化弹出菜单
		Items=new JMenuItem[6]; //初始化数组
		for (int i=0;i<1;i++) {
			Items[i]=new JMenuItem(Str[i]); //实例化菜单项
		popupMenu.add(Items[i]); //增加菜单项到菜单上
		Items[i].addActionListener(action); //菜单项事件处理
		}
		System.out.println(ph.getPhrase().size());
		for(int i=1;i<ph.getPhrase().size()+1;i++) {
			Items[i]=new JMenuItem(ph.getPhrase().get(i-1));
			popupMenu.add(Items[i]); //增加菜单项到菜单上
			Items[i].addActionListener(action); //菜单项事件处理
		}
		getContentPane().add(upPanel);
		getContentPane().add(showPanel);
		getContentPane().add(inputPanel);
		
		button = new JButton("\u53D1\u9001\u8868\u60C5");
		button.addMouseListener(new MouseAdapter() {
			int flag=0;
			FaceFrame face;
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(flag==0) {
				face = new FaceFrame(x);
				//showPanel.add(face);
				flag=1;
				}
				else {
					face.setVisible(false);
					flag=0;
				}
				
			}
		});
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setBackground(new Color(225,225, 225));
		button.setBounds(10, 107, 101, 27);
		inputPanel.add(button);
		if(!isGroup){
		button_1 = new JButton("\u53D1\u9001\u6587\u4EF6");
		button_1.setBorderPainted(false);
		button_1.setFocusPainted(false);
		button_1.setMargin(new Insets(0, 0, 0, 0));
		button_1.setBackground(new Color(225,225, 225));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(); 
				chooser.showOpenDialog(chooser);        
				openFile = chooser.getSelectedFile();  	
				String path = openFile.getPath();
				try {
					FileMessage msg = new FileMessage();
					FileInputStream fis = new FileInputStream(openFile);
					byte b[] = new byte[(int) openFile.length()];
					fis.read(b);
					String name = path.substring(path.lastIndexOf("\\")+1);
					System.out.println(name);
					String type = path.substring(path.lastIndexOf(".")+1);
					if(type.equals("jpg")||type.equals("png")) {
						msg.setType(1);
					}
					else {
						msg.setType(2);
					}
					msg.setB(b);
					msg.setFriendName(chatFriend.getMyNickName());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String date = df.format(new Date());
					msg.setDate(date);
					msg.setName(name);
					msg.setFriendId(chatFriend.getFriendId());
					msg.setMyId(chatFriend.getMyId());
					if(msg.getType() == 1) {
						addPic(chatFriend.getMyNickName(),msg.getDate(), path, true);
						writeRecord(chatFriend.getMyId(),path,msg.getDate(),2,1);
					}
					if(msg.getType() == 2) {
						addFile(chatFriend.getMyNickName(),msg.getDate(), path, true);
						writeRecord(chatFriend.getMyId(),path,msg.getDate(),3,1);
					}
					client.getMessage(msg);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		button_1.setBounds(116, 107, 97, 27);
		inputPanel.add(button_1);
		}
		if(isGroup){
			button_1 = new JButton("\u53D1\u9001\u6587\u4EF6");
			button_1.setBorderPainted(false);
			button_1.setFocusPainted(false);
			button_1.setMargin(new Insets(0, 0, 0, 0));
			button_1.setBackground(new Color(225,225, 225));
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser(); 
					chooser.showOpenDialog(chooser);        
					openFile = chooser.getSelectedFile();  	
					String path = openFile.getPath();
					try {
						FileMessage msg = new FileMessage();
						FileInputStream fis = new FileInputStream(openFile);
						byte b[] = new byte[(int) openFile.length()];
						fis.read(b);
						String name = path.substring(path.lastIndexOf("\\")+1);
						System.out.println(name);
						msg.setType(3);
						msg.setName(name);
						msg.setB(b);
						msg.setFriendId(chatFriend.getFriendId());
						msg.setMyId(client.getUser().getId());
						msg.setGroupUser(chatFriend.getGroupUser());
						client.getMessage(msg);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			button_1.setBounds(116, 107, 97, 27);
			inputPanel.add(button_1);
			}
		
		
		button_2 = new JButton("\u5E38\u7528\u8BED");
		button_2.setBorderPainted(false);
		button_2.setFocusPainted(false);
		button_2.setMargin(new Insets(0, 0, 0, 0));
		button_2.setBackground(new Color(225,225, 225));
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				popupMenu.show(event.getComponent(),event.getX(),event.getY()); //显示菜单
			}
		});
		button_2.setBounds(217, 107, 97, 27);
		inputPanel.add(button_2);
		
		JButton button_3 = new JButton("\u53D1\u9001\u8BED\u97F3");
		button_3.setBounds(540, 495, 99, 27);
		button_3.setBorderPainted(false);
		button_3.setFocusPainted(false);
		button_3.setMargin(new Insets(0, 0, 0, 0));
		button_3.setBackground(new Color(225,225, 225));
		button_3.addMouseListener(new MouseAdapter() {
			
			int flag=0;
			MyRecord6 mr;
			@Override
			public void mouseClicked(MouseEvent event) {
				//client.setMyRecord();
				//client.video.setVisible(true);
				
				if(flag==0) {
					mr = new MyRecord6(client);
					//showPanel.add(face);
					flag=1;
					}
					else {
						mr.setVisible(false);
						flag=0;
					}
				
			}
		});
			getContentPane().add(button_3);

		if (isGroup) {
			getContentPane().add(groupPeopleScrollPanel);
			getContentPane().add(fileScrollPanel);
		}
		//setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		
		upPanel = new JPanel();
		upPanel.setLayout(null);
		upPanel.setBounds(0, 0, 665, 85);
		upPanel.setBackground(new Color(224, 223, 241));
		
		//头像
        ImageIcon img = new ImageIcon("src//interface//"+chatFriend.getFriendHead());
		img.setImage(img.getImage().getScaledInstance(70,66,Image.SCALE_DEFAULT ));
		friendAvatar = new JLabel(img); 
		friendAvatar.setBounds(10, 6, 70, 66);
		
		upPanel.add(friendAvatar);
		/**
		 * Name
		 */

		friendName = new JLabel(chatFriend.getFriendNickName());
		friendName.setBounds(94, 23, 107, 22);
		upPanel.add(friendName);
		/**
		 * Trades
		 */
		friendTrades = new JLabel(chatFriend.getFriendSignature());
		friendTrades.setBounds(96, 57, 400, 15);
		upPanel.add(friendTrades);
		/**
		 * show
		 */
		showBox = Box.createVerticalBox();
		showBox.setBackground(new Color(0, 0, 0));
		showPanel = new JScrollPane(showBox);
		showPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// 设置滚动速率
		showPanel.getVerticalScrollBar().setUnitIncrement(20);
		showPanel.setBounds(0, 85, 526, 311);

		/*
		 * 获取聊天记录
		 */
		if(!isGroup) {
		for(int i=0;i<chatFriend.getMessage().size();i++) {
			if(chatFriend.getMessage().get(i).getType()==1) {
				//System.out.println("chatFriend:"+chatFriend.getMessage().get(i).getMessage());
				if(chatFriend.getMessage().get(i).getIsMy() == 1) {
				addMessage(chatFriend.getMyNickName(),chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),true,true);
				}
				else {
					addMessage(chatFriend.getFriendNickName(),chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),true,false);
				}
					
			}
			if(chatFriend.getMessage().get(i).getType()==2) {
				if(chatFriend.getMessage().get(i).getIsMy() == 1) {
					addPic(chatFriend.getMyNickName(), chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),true);
					}
					else {
						addPic(chatFriend.getFriendNickName(), chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),false);
					}
			}
			if(chatFriend.getMessage().get(i).getType()==3) {
				if(chatFriend.getMessage().get(i).getIsMy() == 1) {
					addFile(chatFriend.getMyNickName(), chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),true);
					}
					else {
						addFile(chatFriend.getFriendNickName(), chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),false);
					}
			}
			if(chatFriend.getMessage().get(i).getType()==5) {
				if(chatFriend.getMessage().get(i).getIsMy() == 1) {
					
					addVideo(chatFriend.getMyNickName(), chatFriend.getMessage().get(i).getDate(), chatFriend.getMessage().get(i).getMessage(),true);
					}
					else {
						addVideo(chatFriend.getFriendNickName(), chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),false);
					}
			}
			
		}
		}
		else {
			for(int i=0;i<chatFriend.getMessage().size();i++) {
				if(chatFriend.getMessage().get(i).getType()==1) {
					for(int j=0;j<chatFriend.getGroupUser().size();j++) {
						//System.out.println("chatFriend:"+chatFriend.getMessage().get(i).getMessage());
						if(chatFriend.getMessage().get(i).getIsMy() == 1) {
						addMessage(chatFriend.getMyNickName(),chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),true,true);
						break;
						}
						else {
							if(chatFriend.getMessage().get(i).getId() == chatFriend.getGroupUser().get(j).getId()) {
							addMessage(chatFriend.getGroupUser().get(j).getNickName(),chatFriend.getMessage().get(i).getDate(),chatFriend.getMessage().get(i).getMessage(),true,false);
							break;
							}
						}
					}	
				}
			}
		}
		/*
		 * 获取离线聊天记录
		 */
		if(!isGroup) {
		for(int i=0;i<chatFriend.getOffOnline().size();i++) {
			if(chatFriend.getOffOnline().get(i).getType() == 1) {
				
				addMessage(chatFriend.getFriendNickName(),chatFriend.getOffOnline().get(i).getDate(),chatFriend.getOffOnline().get(i).getMessage(),false,false);
				try {
					writeRecord(chatFriend.getFriendId(),chatFriend.getOffOnline().get(i).getMessage(),chatFriend.getOffOnline().get(i).getDate(),1,0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		}
		else
		{
			for(int i=0;i<chatFriend.getOffOnline().size();i++) {
				if(chatFriend.getOffOnline().get(i).getType() == 1) {
					for(int j=0;j<chatFriend.getGroupUser().size();j++) {
						if(chatFriend.getGroupUser().get(j).getId() == chatFriend.getOffOnline().get(i).getId()) {
					addMessage(chatFriend.getGroupUser().get(j).getNickName(),chatFriend.getOffOnline().get(i).getDate(),chatFriend.getOffOnline().get(i).getMessage(),false,false);
					try {
						writeRecord(chatFriend.getOffOnline().get(i).getId(),chatFriend.getOffOnline().get(i).getMessage(),chatFriend.getOffOnline().get(i).getDate(),1,0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
						}
					}
				}
			}
		}

		// showPanel.getVerticalScrollBar().setValue(showPanel.getVerticalScrollBar().getMaximum());
		showPanel.getViewport().setViewPosition(new Point(0, showPanel.getHeight() + 100));
		// System.out.println(showPanel.getVerticalScrollBar().getMaximum());

		/**
		 * input
		 */
		inputPanel = new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBounds(0, 396, 526, 138);
		input = new JTextArea();
		input.setBounds(0, 0, 526, 106);
		
		input.setLineWrap(true);
		inputScroll = new JScrollPane(input);
		inputScroll.setBounds(0, 0, 526, 106);
	
		inputScroll.getVerticalScrollBar().setUnitIncrement(15);
		inputPanel.add(inputScroll);

		closeButton = new JButton("<html>关闭(<u>C</u>)</html>");
		closeButton.setBorderPainted(false);
		closeButton.setFocusPainted(false);
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.setBackground(new Color(58, 77, 195));
	
		closeButton.setBounds(361, 106, 70, 24);
		inputPanel.add(closeButton);

		sendButton = new JButton("<html>发送(<u>S</u>)</html>");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(input.getText().equals("")){
					JOptionPane.showMessageDialog(input, "发送消息不能为空，请重新输入");
				}
				else {
				    ChatFriend chat = chatFriend;
					String msg = input.getText();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String date = df.format(new Date());
					Message current = new Message();
					current.setDate(date);
					current.setMessage(msg);
					current.setType(1);
					chat.setCurrentMessage(current);
					chat.setType(1);
					client.getMessage(chat);
					addMessage(chatFriend.getMyNickName(),date,msg,false,true);
					try {
						writeRecord(chat.getMyId(),msg,date,1,1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					input.setText("");
				}
			}
		});
		sendButton.setBorderPainted(false);
		sendButton.setFocusPainted(false);
		sendButton.setMargin(new Insets(0, 0, 0, 0));
		sendButton.setBackground(new Color(58, 77, 195));
		sendButton.setBounds(435, 106, 70, 24);
	

		inputPanel.add(sendButton);
		// 获取群成员部分
				if (isGroup) {
					groupPeopleBox = Box.createVerticalBox();
					List<User> members = chatFriend.getGroupUser();
					Box myBox = Box.createHorizontalBox();
					ImageIcon img1 = new ImageIcon("src//interface//"+client.getUser().getHead());
					img1.setImage(img1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT ));
					myBox.add(new JLabel(img1));
					myBox.add(new JLabel("☆我☆(" + client.getUser().getUserAccount() + ")"));
					groupPeopleBox.add(myBox);
					for (User i : members) {
						if (i.getId() == client.getUser().getId())
							continue;
						Box peopleBox = Box.createHorizontalBox();
						ImageIcon icon = new ImageIcon("src//interface//"+i.getHead());
						String content = i.getNickName()+"(" + i.getUserAccount() + ")";
						icon = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
						peopleBox.add(new JLabel(icon));
						peopleBox.add(new JLabel(content));
						groupPeopleBox.add(peopleBox);
					}
					groupPeopleScrollPanel = new JScrollPane(groupPeopleBox);

					//groupPeopleScrollPanel.getVerticalScrollBar().setUI(new com.oicq.frame.ScrollBarUI());
					groupPeopleScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					// 设置滚动速率
					groupPeopleScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
					groupPeopleScrollPanel.setBounds(524, 85, 141, 100);
					fileBox = Box.createVerticalBox();
					JLabel label = new JLabel();
					label.setText("群文件");
					fileBox.add(label);
					fileScrollPanel = new JScrollPane(fileBox);
					label.addMouseListener(new MouseAdapter() {
			 			@Override
			 			public void mouseClicked(MouseEvent e) {
			 				if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
			 					String filePath = "E:\\Chat room\\getfile";
			 			        	try {
										Desktop.getDesktop().open(new File(filePath));
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
			 				}
			 			}
			 		});
					for(int i=0;i<chatFriend.getFile().size();i++) {
					FileMessage file = chatFriend.getFile().get(i);
					addFiles(file);
					}
					fileScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					// 设置滚动速率
					fileScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
					fileScrollPanel.setBounds(524, 185, 141, 200);
					
				}

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	}

	public int getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(int messagenum) {
		this.messageNum = messagenum;
	}
	public void addFiles(FileMessage file) {
		String rm = "<html><p style =\"font-size:10px;" +  "color:#00008b"+ "\">" + file.getName();
		JLabel ll = new JLabel(rm);
		
		file.setType(3);
		
		ll.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
 					String filePath = "E:\\Chat room\\getfile"+"\\"+file.getName();
 					if(new File(filePath).exists()) {
 			        	try {
							Desktop.getDesktop().open(new File(filePath));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
 			        
 			        }
 					else {
					client.setGetFile(file);
 					}
 				}
 			}
 		});
		ll.setSize(0,10);
		fileBox.add(ll);
		fileScrollPanel.getViewport().setViewPosition(new Point(0, fileScrollPanel.getHeight() + 100000));
	}
	public void addFile(String userName, String sendTime, String path,boolean isMy) {
		if(!isMy) {
			userName = "<html><p style =\"font-size:10px;color:#0000ff\">" + userName;
			}
			else {
				userName = "<html><p style =\"font-size:10px;color:#00FF00\">" + userName;
			}
		sendTime = "<span style=\"color:#cc9966\"> " + sendTime + "</span></p></html>";
		//System.out.println("msssage:"+message);
		showBox.add(new JLabel(userName + sendTime));
		String name = path.substring(path.lastIndexOf("\\")+1);
		String rm = "<html><p style =\"font-size:14px;" +  "color:#00008b"+ "\">" + name;
		JLabel label = new JLabel();
		label.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
					try {
						if(new File(path).exists()) {
						Desktop.getDesktop().open(new File(path));
						}
						else {
							JOptionPane.showMessageDialog(showPanel, "文件已丢失");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
 				}
 			}
 		});
		label.setText(rm);
		showBox.add(label);
		// showPanel.getVerticalScrollBar().setValue(showPanel.getVerticalScrollBar().getMaximum());
		showPanel.getViewport().setViewPosition(new Point(0, showPanel.getHeight() + 100000));
	}
	public void addPic(String userName, String sendTime, String path,boolean isMy) {
		if(!isMy) {
			userName = "<html><p style =\"font-size:10px;color:#0000ff\">" + userName;
			}
			else {
				userName = "<html><p style =\"font-size:10px;color:#00FF00\">" + userName;
			}
			sendTime = "<span style=\"color:#cc9966\"> " + sendTime + "</span></p></html>";
			//System.out.println("msssage:"+message);
			showBox.add(new JLabel(userName + sendTime));
			
			
			ImageIcon img = new ImageIcon(path);
	 		img.setImage(img.getImage().getScaledInstance(99,100,Image.SCALE_DEFAULT ));
	 		JLabel label = new JLabel(img);
	 		label.addMouseListener(new MouseAdapter() {
	 			@Override
	 			public void mouseClicked(MouseEvent e) {
	 				if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
						try {
							if(new File(path).exists()) {
								Desktop.getDesktop().open(new File(path));
								}
								else {
									JOptionPane.showMessageDialog(showPanel, "图片已丢失");
								}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	 				}
	 			}
	 		});
	 		showBox.add(label);
			// showPanel.getVerticalScrollBar().setValue(showPanel.getVerticalScrollBar().getMaximum());
			showPanel.getViewport().setViewPosition(new Point(0, showPanel.getHeight() + 100000));
	}
	public void addVideo(String userName, String sendTime, String path,boolean isMy) {
		if(!isMy) {
			userName = "<html><p style =\"font-size:10px;color:#0000ff\">" + userName;
			}
			else {
				userName = "<html><p style =\"font-size:10px;color:#00FF00\">" + userName;
			}
			sendTime = "<span style=\"color:#cc9966\"> " + sendTime + "</span></p></html>";
			//System.out.println("msssage:"+message);
			showBox.add(new JLabel(userName + sendTime));
			String name;
			if(isMy) {
				name = "发送的一条语音";
			}
			else {
				name = "对方发来的一条语音";
			}
			String rm = "<html><p style =\"font-size:14px;" +  "color:#00008b"+ "\">" + name;
			JLabel label = new JLabel();
			label.addMouseListener(new MouseAdapter() {
	 			@Override
	 			public void mouseClicked(MouseEvent e) {
	 				if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
						if(new File(path).exists()) {
							 File selectedFile = new File(path);
								if (selectedFile != null) {
								
								try {
									if (audioClip != null) {//如果有音频在播放
										audioClip.stop();//停止当前音频的播放
									}
									audioClip = Applet.newAudioClip(selectedFile.toURI().toURL());//获取选择的音频
									audioClip.play();//播放音频
								} catch (MalformedURLException e1) {
									e1.printStackTrace();
								}
							}
						}
						else {
							JOptionPane.showMessageDialog(showPanel, "文件已丢失");
						}
	 				}
	 			}
	 		});
			label.setText(rm);
			showBox.add(label);
			showPanel.getViewport().setViewPosition(new Point(0, showPanel.getHeight() + 100000));
	}
	public void addMessage(String userName, String sendTime, String message, boolean isOld,boolean isMy) {
		if(!isMy) {
		userName = "<html><p style =\"font-size:10px;color:#0000ff\">" + userName;
		}
		else {
			userName = "<html><p style =\"font-size:10px;color:#00FF00\">" + userName;
		}
		sendTime = "<span style=\"color:#cc9966\"> " + sendTime + "</span></p></html>";
		//System.out.println("msssage:"+message);
		showBox.add(new JLabel(userName + sendTime));
		JLabel label = new JLabel();
		label.setSize(200, 0);
		String a;
		try {
			a = JlabelSetText(label,message);
			String m = null;
			for(int i=0;i<90;i++) {
				if(i<10) {
			      m = a.replace("f00"+i, "<img src=\"file:/E:/Chat room/img/face/f00"+i+".png\" alt=\"f00"+i+"\" width=\"20\" height=\"20\">");
				}
				else
				{
					m = a.replace("f0"+i, "<img src=\"file:/E:/Chat room/img/face/f0"+i+".png\" alt=\"f0"+i+"\" width=\"20\" height=\"20\">");
				}
			a=m;
			}
			String rm = "<html><p style =\"font-size:14px;" + (isOld ? "color:#969696" : "") + "\">" + m;
			label.setText(rm);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		showBox.add(label);
		// showPanel.getVerticalScrollBar().setValue(showPanel.getVerticalScrollBar().getMaximum());
		showPanel.getViewport().setViewPosition(new Point(0, showPanel.getHeight() + 100000));
	}
	 String JlabelSetText(JLabel jLabel, String longString) 
	            throws InterruptedException {
	        StringBuilder builder = new StringBuilder("");
	        char[] chars = longString.toCharArray();
	        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
	        int start = 0;
	        int len = 0;
	        while (start + len < longString.length()) {
	            while (true) {
	                len++;
	                if (start + len > longString.length())break;
	                if (fontMetrics.charsWidth(chars, start, len) 
	                        > jLabel.getWidth()) {
	                    break;
	                }
	            }
	            builder.append(chars, start, len-1).append("<br/>");
	            start = start + len - 1;
	            len = 0;
	        }
	        builder.append(chars, start, longString.length()-start);
	        builder.append("</p><br/></html>");
	        //jLabel.setText(builder.toString());
	       // System.out.println("build:::::"+builder.toString());
	        return builder.toString();
	    }
	 public void writeRecord(int id,String msg,String date,int type,int isMy) throws IOException {
			OutputStream os = new FileOutputStream("E:\\Chat room\\record\\"+chatFriend.getMyId()+"to"+chatFriend.getFriendId()+"isGroup"+isGroup+".txt",true);
			PrintWriter pw=new PrintWriter(os);
			pw.println(id);
			pw.println(msg);//每输入一个数据，自动换行，便于我们每一行每一行地进行读取
			pw.println(date);
			pw.println(type);
			pw.println(isMy);
			pw.close();
			os.close();
	 }
	 
	class CommonAction implements ActionListener { //菜单项事件处理

		 	public void actionPerformed(ActionEvent event) {

		 		for (int i=0;i<ph.getPhrase().size()+1;i++) {
					if (event.getSource()==Items[i]) { //判断事件来自于哪个菜单项
						if(i==0) {
							new CommonPhrases(client,chatFriend).frame.setVisible(true);
						}
						else
						{
							input.append(ph.getPhrase().get(i-1));
						}
						
		 				
			}
	}

	}
	 }
}

