package Interface;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import Interface.JPopMenuDemo.ChangeColorAction;
import bean.ChatFriend;
import bean.Friends;
import bean.GroupRelationship;
import bean.Message;
import bean.User;
import bean.UserCustom;
import client.Client;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
public class MainInterface {

	public JFrame frame;
	JMenuItem[] friendItems;//好友菜单项
	JMenuItem[] groupItems;//群聊菜单项
	private Client client;
	private UserCustom user;
	private JTree friendTree;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTree groupTree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//MainInterface window = new MainInterface();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainInterface(Client client,UserCustom user) {
		this.client = client;
		this.user = user;
		initialize();
	}
	
	public void setUser(UserCustom user) {
		this.user = user;
	}
    public void setFriendTree() {
    	scrollPane.repaint();
		JPopupMenu popupMenu; //弹出菜单
		FriendAction action = new FriendAction(); //菜单项事件处理
		String[] friendStr = {"发送消息","查看资料","删除好友","发送文件"}; //菜单项名称
		popupMenu=new JPopupMenu(); //实例化弹出菜单
		friendItems=new JMenuItem[4]; //初始化数组
		for (int i=0;i<4;i++) {
			friendItems[i]=new JMenuItem(friendStr[i]); //实例化菜单项
		popupMenu.add(friendItems[i]); //增加菜单项到菜单上
		
		friendItems[i].addActionListener(action); //菜单项事件处理
		}
		FriendNode friendRoot=new FriendNode("我的好友",3);
		
		for(int i=0;i<user.getFriends().size();i++) {
			FriendNode node=new FriendNode(new ImageIcon("src//interface//"+user.getFriends().get(i).getHead()),user.getFriends().get(i).getNickName(),user.getFriends().get(i).getOnline(),user.getFriends().get(i).getUserAccount());
			friendRoot.add(node);
			System.out.println(user.getFriends().get(i).getNickName()+"在线吗"+user.getFriends().get(i).getOnline());
		}
		
		 
		
		scrollPane.setBounds(0, 199, 367, 229);
		//将scrollPane添加到JPanel中用于显示
       frame.getContentPane().add(scrollPane);
       //添加树的双击触发事件
       //tree.addMouseListener(new MouseHandle());
        
      
       //将刚才的根节点添加到JTree中
       friendTree=new JTree(friendRoot);
     
       friendTree.addMouseListener(new MouseAdapter() { 
    	
    	   
       	@Override
       	public void mouseClicked(MouseEvent e) {
       	    

       		if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
       			
                System.out.println("两下");
                System.out.println(getIdex(friendTree));

            }
       	}
       	public void mousePressed(MouseEvent event) { //点击鼠标
			
			triggerEvent(event); //调用triggerEvent方法处理事件
			
			}

			public void mouseReleased(MouseEvent event) { //释放鼠标
			
			triggerEvent(event);
			
			}

			private void triggerEvent(MouseEvent event) { //处理事件
			
			if (event.isPopupTrigger()&& getIdex(friendTree)>=0) //如果是弹出菜单事件(根据平台不同可能不同)
			
			popupMenu.show(event.getComponent(),event.getX(),event.getY()); //显示菜单
			
			}
       });
       //将tree添加到JScrollPane 中
       scrollPane.setViewportView(friendTree);
       
		//将树的前面连接去掉
		friendTree.putClientProperty("JTree.lineStyle", "Horizontal");
		//设置树的字体大小，样式
		friendTree.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//设置树节点的高度
		friendTree.setRowHeight(80);
		//设置单元描述
		friendTree.setCellRenderer(new FriendNodeRenderer());
    	
    }
    public void setGroupTree() {
    	scrollPane_1.repaint();
    	JPopupMenu popupMenu_1; //弹出菜单
		GroupAction gaction = new GroupAction(); //菜单项事件处理
		String[] groupStr =  {"发送消息","查看资料","退出群聊","邀请好友"};
		popupMenu_1=new JPopupMenu(); //实例化弹出菜单
		groupItems=new JMenuItem[4]; //初始化数组
		for (int i=0;i<4;i++) {
			groupItems[i]=new JMenuItem(groupStr[i]); //实例化菜单项
		popupMenu_1.add(groupItems[i]); //增加菜单项到菜单上
		
		groupItems[i].addActionListener(gaction); //菜单项事件处理
		}
		GroupNode groupRoot=new GroupNode("我的群组",1);
		for(int i=0;i<user.getGroup().size();i++) {
			GroupNode node = new GroupNode(user.getGroup().get(i).getName(),user.getGroup().get(i).getAccount(),user.getGroup().get(i).getGroupUser().size(),2);
			System.out.println("群里面的人："+user.getGroup().get(i).getGroupUser().size());
			groupRoot.add(node);
			//System.out.println(user.getFriends().get(i).getNickName()+"在线吗"+user.getFriends().get(i).getOnline());
		}
		scrollPane_1.setBounds(0, 428, 367, 231);
		frame.getContentPane().add(scrollPane_1);
		//将刚才的根节点添加到JTree中
	     groupTree=new JTree(groupRoot);
		//将tree添加到JScrollPane 中
		scrollPane_1.setViewportView(groupTree);
	       
			//将树的前面连接去掉
		groupTree.putClientProperty("JTree.lineStyle", "Horizontal");
			//设置树的字体大小，样式
		groupTree.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			//设置树节点的高度
		groupTree.setRowHeight(80);
			//设置单元描述
		groupTree.setCellRenderer(new GroupNodeRenderer());
		groupTree.addMouseListener(new MouseAdapter() { 
	    	   
	       	public void mousePressed(MouseEvent event) { //点击鼠标
				
				triggerEvent(event); //调用triggerEvent方法处理事件
				
				}

				public void mouseReleased(MouseEvent event) { //释放鼠标
				
				triggerEvent(event);
				
				}

				private void triggerEvent(MouseEvent event) { //处理事件
				
				if (event.isPopupTrigger()) //如果是弹出菜单事件(根据平台不同可能不同)
				
				popupMenu_1.show(event.getComponent(),event.getX(),event.getY()); //显示菜单
				
				}
	       });
    }
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				System.out.println("我走了");
				user.setType(3);
				client.getMessage(user);
				System.exit(0);
			}
		});
		frame.setTitle("\u5B9D\u53EF\u68A6\u804A\u5929\u5BA4");
		frame.setBounds(100, 100, 385, 706);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // 设置标题栏的图标 
        frame.setIconImage(imageIcon.getImage());
        //头像
         ImageIcon img = new ImageIcon("src//interface//"+user.getHead());
 		img.setImage(img.getImage().getScaledInstance(99,100,Image.SCALE_DEFAULT ));
 		JLabel imagLabel = new JLabel(img); 
 		imagLabel.setBounds(37, 27, 91, 88);
 		imagLabel.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2)
 				client.setPersonal(user,1);
 			}
 		});
 		frame.getContentPane().setLayout(null);
 		frame.getContentPane().add(imagLabel);
		
		
		JLabel label = new JLabel(user.getNickName());
		label.setBounds(167, 55, 91, 27);
		label.setFont(new Font("宋体",0,17));
		label.setForeground(new Color(255,255,255));
		frame.getContentPane().add(label);
		
		/*
		 * 好友JTree
		 */
		JPopupMenu popupMenu; //弹出菜单
		FriendAction action = new FriendAction(); //菜单项事件处理
		String[] friendStr = {"发送消息","查看资料","删除好友","发送文件"}; //菜单项名称
		String[] groupStr =  {"发送消息","查看资料","退出群聊","邀请好友"};
		popupMenu=new JPopupMenu(); //实例化弹出菜单
		friendItems=new JMenuItem[4]; //初始化数组
		for (int i=0;i<4;i++) {
			friendItems[i]=new JMenuItem(friendStr[i]); //实例化菜单项
		popupMenu.add(friendItems[i]); //增加菜单项到菜单上
		
		friendItems[i].addActionListener(action); //菜单项事件处理
		}
		
		FriendNode friendRoot=new FriendNode("我的好友",3);
		
		for(int i=0;i<user.getFriends().size();i++) {
			FriendNode node=new FriendNode(new ImageIcon("src//interface//"+user.getFriends().get(i).getHead()),user.getFriends().get(i).getNickName(),user.getFriends().get(i).getOnline(),user.getFriends().get(i).getUserAccount());
			friendRoot.add(node);
			System.out.println(user.getFriends().get(i).getNickName()+"在线吗"+user.getFriends().get(i).getOnline());
		}
		
		 
		//将tree添加到JScrollPane 中
		scrollPane=new JScrollPane();
		scrollPane.setBounds(0, 199, 367, 229);
		//将scrollPane添加到JPanel中用于显示
       frame.getContentPane().add(scrollPane);
       //添加树的双击触发事件
       //tree.addMouseListener(new MouseHandle());
        
      
       //将刚才的根节点添加到JTree中
       friendTree=new JTree(friendRoot);
     
       friendTree.addMouseListener(new MouseAdapter() { 
    	
    	   
       	@Override
       	public void mouseClicked(MouseEvent e) {
       	    

       		if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
       			
                System.out.println("两下");
                System.out.println(getIdex(friendTree));

            }
       	}
       	public void mousePressed(MouseEvent event) { //点击鼠标
			
			triggerEvent(event); //调用triggerEvent方法处理事件
			
			}

			public void mouseReleased(MouseEvent event) { //释放鼠标
			
			triggerEvent(event);
			
			}

			private void triggerEvent(MouseEvent event) { //处理事件
			
			if (event.isPopupTrigger()&& getIdex(friendTree)>=0) //如果是弹出菜单事件(根据平台不同可能不同)
			
			popupMenu.show(event.getComponent(),event.getX(),event.getY()); //显示菜单
			
			}
       });
       //将tree添加到JScrollPane 中
       scrollPane.setViewportView(friendTree);
       
		//将树的前面连接去掉
		friendTree.putClientProperty("JTree.lineStyle", "Horizontal");
		//设置树的字体大小，样式
		friendTree.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//设置树节点的高度
		friendTree.setRowHeight(80);
		//设置单元描述
		friendTree.setCellRenderer(new FriendNodeRenderer());
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 428, 367, 231);
		frame.getContentPane().add(scrollPane_1);
		
		
		
		GroupNode groupRoot=new GroupNode("我的群组",1);
		for(int i=0;i<user.getGroup().size();i++) {
			GroupNode node = new GroupNode(user.getGroup().get(i).getName(),user.getGroup().get(i).getAccount(),user.getGroup().get(i).getGroupUser().size(),2);
			groupRoot.add(node);
			//System.out.println(user.getFriends().get(i).getNickName()+"在线吗"+user.getFriends().get(i).getOnline());
		}
		//将刚才的根节点添加到JTree中
	    groupTree=new JTree(groupRoot);
		//将tree添加到JScrollPane 中
		scrollPane_1.setViewportView(groupTree);
	       
			//将树的前面连接去掉
		groupTree.putClientProperty("JTree.lineStyle", "Horizontal");
			//设置树的字体大小，样式
		groupTree.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			//设置树节点的高度
		groupTree.setRowHeight(80);
			//设置单元描述
		groupTree.setCellRenderer(new GroupNodeRenderer());
		
		//添加好友
		JButton button = new JButton("\u6DFB\u52A0\u597D\u53CB");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.setFriendsQuery();
			}
		});
		button.setBounds(0, 173, 113, 27);
		button.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(button);
		
		//创建群聊
		JButton button_1 = new JButton("\u521B\u5EFA\u7FA4\u804A");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGroup m = new CreateGroup(client);
				m.frame.setVisible(true);
			}
		});
		button_1.setBounds(126, 173, 113, 27);
		button_1.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(button_1);
		
		//加入群聊
		JButton button_2 = new JButton("\u52A0\u5165\u7FA4\u804A");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.setAddGroup();
			}
		});
		button_2.setBounds(253, 173, 113, 27);
		button_2.setFont(new Font("宋体",0,14));
		frame.getContentPane().add(button_2);
		System.out.println(user.getSignature());
		String signature = user.getSignature();
		if(null == signature||signature.equals(""))
		{
			signature = "\u5FEB\u6765\u7F16\u5199\u4F60\u7684\u4E2A\u6027\u7B7E\u540D\u5427";
		}
		JLabel label_1 = new JLabel(signature);
		label_1.setBounds(37, 133, 316, 27);
		label_1.setFont(new Font("宋体",0,17));
		label_1.setForeground(new Color(255,255,255));
		frame.getContentPane().add(label_1);
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 367, 200);
		ImageIcon img1 = new ImageIcon("img/luofei.jpeg");
		img1.setImage(img1.getImage().getScaledInstance(367,200,Image.SCALE_DEFAULT ));
		
		JLabel imagLabel1 = new JLabel(img1); 
		imagLabel1.setBounds(0,0,img1.getIconWidth(),img1.getIconHeight());
		panel1.add(imagLabel1);
		frame.getContentPane().add(panel1);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		JPopupMenu popupMenu_1; //弹出菜单
		GroupAction gaction = new GroupAction(); //菜单项事件处理
		popupMenu_1=new JPopupMenu(); //实例化弹出菜单
		groupItems=new JMenuItem[4]; //初始化数组
		for (int i=0;i<4;i++) {
			groupItems[i]=new JMenuItem(groupStr[i]); //实例化菜单项
		popupMenu_1.add(groupItems[i]); //增加菜单项到菜单上
		
		groupItems[i].addActionListener(gaction); //菜单项事件处理
		}
		groupTree.addMouseListener(new MouseAdapter() { 
	    	   
	       	public void mousePressed(MouseEvent event) { //点击鼠标
				
				triggerEvent(event); //调用triggerEvent方法处理事件
				
				}

				public void mouseReleased(MouseEvent event) { //释放鼠标
				
				triggerEvent(event);
				
				}

				private void triggerEvent(MouseEvent event) { //处理事件
				
				if (event.isPopupTrigger()) //如果是弹出菜单事件(根据平台不同可能不同)
				
				popupMenu_1.show(event.getComponent(),event.getX(),event.getY()); //显示菜单
				
				}
	       });
	       
		
	}
	private Vector<Message> readRecord(int myId,int friendId,boolean isGroup) {
		String path = "E:\\Chat room\\record\\"+myId+"to"+friendId+"isGroup"+isGroup+".txt";
		File f = new File(path);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		        Vector<Message> msg = new Vector<Message>();
		        //打开带读取的文件
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("E:\\Chat room\\record\\"+myId+"to"+friendId+"isGroup"+isGroup+".txt"));
			String line=null;
		
		while((line=br.readLine())!=null) {
			Message s = new Message();
			s.setId(Integer.parseInt(line));
			line = br.readLine();
			
			s.setMessage(line);
			line = br.readLine();
			
			s.setDate(line);
			line = br.readLine();
			
			s.setType(Integer.parseInt(line));
			line = br.readLine();
			s.setIsMy(Integer.parseInt(line));
			msg.add(s);
			}
//		for(int i=0;i<msg.size();i++) {
//			System.out.println(msg.get(i).getMessage());
//			System.out.println(msg.get(i).getDate());
//			System.out.println(msg.get(i).getIsMy());
//			System.out.println(msg.get(i).getType());
//		}
		br.close();//关闭文件
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return msg;

	}
	public int getIdex(JTree tree) {
		tree.getSelectionModel().setSelectionMode(
 	    	   TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
 		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree
 	    	   .getLastSelectedPathComponent();
 	    	   if (treeNode == null) {
 	    	   return -1;
 	    	   }
 	    	   
 	    	   // 获取选中节点的父节点
 	    	   DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treeNode
 	    	   .getParent();
 	    	   if (parent == null) {
 	    	   return -1;
 	    	   };

 	    	   // 获取选中节点的节点索引
 	    	   int selectedIndex = parent.getIndex(treeNode);
 	    	   return selectedIndex;
	}
	class FriendAction implements ActionListener { //菜单项事件处理

	 	public void actionPerformed(ActionEvent event) {

	 		for (int i=0;i<4;i++) {
				if (event.getSource()==friendItems[i]) { //判断事件来自于哪个菜单项
					
					int select = getIdex(friendTree);
	 				if(i == 1) {
	 					client.setPersonal(user.getFriends().get(select), 3);
	 				}
	 				if(i == 2) {
	 					Friends msg = new Friends(user.getId(),user.getFriends().get(select).getId(),3);
	 					client.getMessage(msg);
	 				}
	 				if(i == 0) {
	 					ChatFriend chat = new ChatFriend();
	 					chat.setFriendAccount(user.getFriends().get(select).getUserAccount());
	 					chat.setFriendHead(user.getFriends().get(select).getHead());
	 					chat.setFriendId(user.getFriends().get(select).getId());
	 					chat.setFriendNickName(user.getFriends().get(select).getNickName());
	 					chat.setFriendSignature(user.getFriends().get(select).getSignature());
	 					chat.setMyAccount(user.getUserAccount());
	 					chat.setMyId(user.getId());
	 					chat.setMyNickName(user.getNickName());
	 					chat.setGroup(false);
	 					try {
							chat.setMessage(readRecord(chat.getMyId(),chat.getFriendId(),false));
						} catch (Exception e) {
							e.printStackTrace();
						}
	 					chat.setType(2);
	 					client.getMessage(chat);
	 				}
	 				return;
		}
}

}
 }
	class GroupAction implements ActionListener { //菜单项事件处理

	 	public void actionPerformed(ActionEvent event) {

	 		for (int i=0;i<4;i++) {
				if (event.getSource()==groupItems[i]) { //判断事件来自于哪个菜单项
					int select = getIdex(groupTree);
	 				if(i == 0) {
	 					ChatFriend chat = new ChatFriend();
	 					chat.setFriendAccount(user.getGroup().get(select).getAccount());
	 					chat.setFriendNickName(user.getGroup().get(select).getName());
	 					chat.setFriendHead("group.png");
	 					chat.setFriendSignature(user.getGroup().get(select).getGroupUser().size()+("人"));
	 					chat.setFriendId(user.getGroup().get(select).getId());
	 					chat.setMyAccount(user.getUserAccount());
	 					chat.setMyId(user.getId());
	 					chat.setMyNickName(user.getNickName());
	 					chat.setGroup(true);
	 					chat.setGroupUser(user.getGroup().get(select).getGroupUser());
	 					chat.setMessage(readRecord(chat.getMyId(),chat.getFriendId(),true));
	 					chat.setType(2);
	 					client.getMessage(chat);
	 				}
	 				int select2 = getIdex(friendTree);
	 				if(i == 3) {
	 					if(select2 == -1) {
	 						JOptionPane.showMessageDialog(frame, "未获取好友");
	 					}
	 					else {
	 						int flag=0;
	 						for(int j=0;j<client.getUser().getGroup().get(select).getGroupUser().size();j++) {
	 							if(client.getUser().getGroup().get(select).getGroupUser().get(j).getId()==client.getUser().getFriends().get(select2).getId()) {
	 								JOptionPane.showMessageDialog(frame, "该好友已在群内");
	 								flag=1;
	 								break;
	 							}
	 						}
	 						if(flag==0) {
	 							GroupRelationship msg = new GroupRelationship();
	 							msg.setGroupId(user.getGroup().get(select).getId());
	 							msg.setUserId(user.getFriends().get(select2).getId());
	 							msg.setUserAccount(user.getFriends().get(select2).getUserAccount());
	 							msg.setUserHead(user.getFriends().get(select2).getHead());
	 							msg.setUserName(user.getFriends().get(select2).getNickName());
	 							msg.setFriend(user.getNickName());
	 							msg.setType(3);
	 							client.getMessage(msg);
	 						}
	 					}
	 				}
	 				return;
		}
}

}
 }
	
}

